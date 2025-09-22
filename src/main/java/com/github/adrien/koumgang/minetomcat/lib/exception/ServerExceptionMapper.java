package com.github.adrien.koumgang.minetomcat.lib.exception;

import com.github.adrien.koumgang.minetomcat.lib.service.request.post.PostItemRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.github.adrien.koumgang.minetomcat.apps.server.service.ServerEventLogService;
import com.github.adrien.koumgang.minetomcat.apps.server.view.ServerEventLogView;
import com.github.adrien.koumgang.minetomcat.helpers.filter.server.ServerUtils;
import com.github.adrien.koumgang.minetomcat.lib.authentication.token.TokenManager;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.configuration.service.ApiConfiguration;
import com.github.adrien.koumgang.minetomcat.lib.controller.ApiResponseController;
import com.github.adrien.koumgang.minetomcat.lib.exception.safe.InvalidAuthentificationException;
import com.github.adrien.koumgang.minetomcat.lib.exception.unsafe.UnauthorizedException;
import com.github.adrien.koumgang.minetomcat.shared.view.RequestDataView;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;


@Provider
public class ServerExceptionMapper implements ExceptionMapper<Throwable> {

    public static Boolean DEBUG;

    static {
        try {
            ApiConfiguration apiConfiguration = new ApiConfiguration();
            DEBUG = apiConfiguration.isDebug();
        } catch (Exception e) {
            DEBUG = false;
        }
    }

    // Inject request information
    @Context
    private ContainerRequestContext request;

    @Context
    private HttpHeaders headers;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable throwable) {
        if(DEBUG) throwable.printStackTrace();


        if(!skipException(throwable) ) {
            if(request != null) {

                // Extract Request Info
                String method = request.getMethod();
                String url = uriInfo.getRequestUri().toString();
                Map<String, List<String>> headerMap = headers.getRequestHeaders();
                MultivaluedMap<String, String> paramsMap = uriInfo.getQueryParameters();

                String requestBody;
                if (ServerUtils.shouldSkipBodyLog(method, request.getMediaType())) {
                    requestBody = null;
                } else {
                    requestBody = (String) request.getProperty(ServerUtils.RAW_BODY_PROP);
                }

                // Extract Status Code
                int statusCode = (throwable instanceof UnauthorizedException) ? 401 : 500;
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);
                String errorMessage = sw.toString();

                // Build cURL Command
                StringJoiner curlHeaders = new StringJoiner(" ");
                headerMap.forEach((key, values) -> values.forEach(value ->
                        curlHeaders.add("-H '" + key + ": " + value + "'")
                ));

                String curlCommand = String.format(
                        "curl -X %s %s %s -d '%s'",
                        method, curlHeaders, url, requestBody
                );

                try {
                    UserToken userToken = null;
                    if(statusCode != 401 && headerMap.containsKey(HttpHeaders.AUTHORIZATION)) {
                        String authorization = headerMap.get(HttpHeaders.AUTHORIZATION).getFirst();
                        try {
                            userToken = TokenManager.readToken(authorization);
                        } catch (Exception ignored) { }
                    }

                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Type mapType = new TypeToken<Map<String, Object>>() {}.getType();

                    Map<String, Object> mapBody;
                    try {
                        if (requestBody == null || requestBody.isBlank() || Objects.equals(requestBody, "N/A")) mapBody = new HashMap<>();
                        else mapBody = gson.fromJson(requestBody, mapType);
                    } catch (Exception ignored) {
                        mapBody = new HashMap<>();
                    }

                    RequestDataView requestDataView = new RequestDataView(
                            url,
                            method,
                            mapBody,
                            new HashMap<>(paramsMap),
                            new HashMap<>(headerMap),
                            new HashMap<>()
                    );

                    PostItemRequest<ServerEventLogView> request = PostItemRequest.<ServerEventLogView>builder()
                            .userToken(userToken)
                            .item(
                                    new ServerEventLogView(
                                            null,
                                            "exception",
                                            curlCommand,
                                            throwable.getClass().getSimpleName(),
                                            errorMessage,
                                            null,
                                            new Date(),
                                            requestDataView
                                    )
                            )
                            .build();
                    ServerEventLogService.getInstance().postItem(request);
                } catch (Exception ignored) { }
            }
        }


        return (throwable instanceof UnauthorizedException || throwable instanceof InvalidAuthentificationException)
                ? ApiResponseController.unauthorized(throwable.getMessage())
                : ApiResponseController.error(throwable.getMessage())
                ;
    }


    private static boolean skipException(Throwable throwable) {
        return (
                (throwable instanceof SafeException)
                        || (throwable instanceof jakarta.ws.rs.NotFoundException)
                        || (throwable instanceof jakarta.ws.rs.NotAllowedException)
        );
    }

}
