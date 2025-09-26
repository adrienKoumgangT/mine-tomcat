package com.github.adrien.koumgang.minetomcat.apps.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.github.adrien.koumgang.minetomcat.apps.auth.service.AuthEventLogService;
import com.github.adrien.koumgang.minetomcat.apps.auth.view.AuthEventLogView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.controller.ApiResponseController;
import com.github.adrien.koumgang.minetomcat.lib.controller.BaseController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/auth/event/log")
@Tag(name = "Auth Event Log", description = "API operation related to auth event log")
public class AuthEventLogController extends BaseController {

    @GET
    @Operation(summary = "Get list of a Server Event Log instance", description = "Get a Server Event Log instance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthEventLogs(
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken user = getUserToken(token);

        if(!user.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        List<AuthEventLogView> serverEventLogViews = AuthEventLogService.getInstance().listAuthEventLogs();

        return ApiResponseController.ok(serverEventLogViews);
    }

}
