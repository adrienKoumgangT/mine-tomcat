package com.github.adrien.koumgang.minetomcat.apps.server.controller;

import com.github.adrien.koumgang.minetomcat.apps.server.service.ServerEventLogFilter;
import com.github.adrien.koumgang.minetomcat.lib.model.ListItemView;
import com.github.adrien.koumgang.minetomcat.lib.service.FieldValue;
import com.github.adrien.koumgang.minetomcat.lib.service.Value;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.ComparisonOperator;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.Condition;
import com.github.adrien.koumgang.minetomcat.lib.service.request.delete.DeleteItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.get.GetItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListFilterRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.response.delete.DeleteItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.get.GetItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.list.ListItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.view.PaginationView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.github.adrien.koumgang.minetomcat.apps.server.service.ServerEventLogService;
import com.github.adrien.koumgang.minetomcat.apps.server.view.EventNamesView;
import com.github.adrien.koumgang.minetomcat.apps.server.view.ServerEventLogView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.controller.ApiResponseController;
import com.github.adrien.koumgang.minetomcat.lib.controller.BaseController;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/server/event/log")
@Tag(name = "Server Error Log", description = "API operation related to server event log")
public class ServerEventLogController extends BaseController {

    @GET
    @Operation(summary = "Get list of a Server Event Log instance", description = "Get a Server Event Log instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ListItemView.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServerEventLogs(
            @Parameter(description = "Page number (1-based), starting from 1", example = "1", required = false)
            @QueryParam("page") @DefaultValue("null") Integer page,
            @Parameter(description = "Page size (1-based), starting from 1", example = "10", required = false)
            @QueryParam("pageSize") @DefaultValue("100") Integer pageSize,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(!userToken.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        ListItemRequest request = ListItemRequest.builder()
                .userToken(userToken)
                .page(page)
                .pageSize(pageSize)
                .pagination(page != null)
                .build();
        ListItemResponse<ServerEventLogView> response = ServerEventLogService.getInstance().list(request);

        ListItemView<ServerEventLogView> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @GET
    @Path("event")
    @Operation(summary = "Get list of a Server Event Log Event Name", description = "Return a list of Server Event Log Name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ListItemView.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServerEventLogEvents(
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(!userToken.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        ListItemRequest request = ListItemRequest.builder().userToken(userToken).build();
        ListItemResponse<String> response = ServerEventLogService.getInstance().listEvents(request);

        ListItemView<String> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @GET
    @Path("event/name")
    @Operation(summary = "Get list of a Server Event Log Event Name", description = "Return a list of Server Event Log Name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation (Map of event to list of names)",
                    content = @Content(schema = @Schema(implementation = ListItemView.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServerEventLogEventsNames(
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(!userToken.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        ListItemRequest request = ListItemRequest.builder().userToken(userToken).build();
        ListItemResponse<EventNamesView> response = ServerEventLogService.getInstance().listEventsNames(request);

        ListItemView<EventNamesView> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @GET
    @Path("by/event/{event}")
    @Operation(summary = "Get list of a Server Event Log instance by event", description = "Get a Server Event Log instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ListItemView.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServerEventLogsByEvent(
            @PathParam("event") String event,
            @Parameter(description = "Page number (1-based), starting from 1", example = "1", required = false)
            @QueryParam("page") @DefaultValue("null") Integer page,
            @Parameter(description = "Page size (1-based), starting from 1", example = "10", required = false)
            @QueryParam("pageSize") @DefaultValue("100") Integer pageSize,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(!userToken.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        ListFilterRequest request = ListFilterRequest.builder()
                .userToken(userToken)
                .page(page)
                .pageSize(pageSize)
                .pagination(page != null)
                .conditions(
                        Condition.builder()
                                .comparisonOperator(ComparisonOperator.EQ)
                                .fieldValue(
                                        FieldValue.builder()
                                                .name(ServerEventLogFilter.EVENT.getValue())
                                                .value(
                                                        Value.builder()
                                                                .s(event)
                                                                .build()
                                                ).build()
                                ).build()
                )
                .build();
        ListItemResponse<ServerEventLogView> response = ServerEventLogService.getInstance().filter(request);

        ListItemView<ServerEventLogView> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @GET
    @Path("by/name/{name}")
    @Operation(summary = "Get list of a Server Event Log instance by event", description = "Get a Server Event Log instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ListItemView.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServerEventLogsByName(
            @PathParam("name") String name,
            @Parameter(description = "Page number (1-based), starting from 1", example = "1", required = false)
            @QueryParam("page") @DefaultValue("null") Integer page,
            @Parameter(description = "Page size (1-based), starting from 1", example = "10", required = false)
            @QueryParam("pageSize") @DefaultValue("100") Integer pageSize,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(!userToken.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        ListFilterRequest request = ListFilterRequest.builder()
                .userToken(userToken)
                .page(page)
                .pageSize(pageSize)
                .pagination(page != null)
                .conditions(
                        Condition.builder()
                                .comparisonOperator(ComparisonOperator.EQ)
                                .fieldValue(
                                        FieldValue.builder()
                                                .name(ServerEventLogFilter.NAME.getValue())
                                                .value(
                                                        Value.builder()
                                                                .s(name)
                                                                .build()
                                                ).build()
                                ).build()
                )
                .build();
        ListItemResponse<ServerEventLogView> response = ServerEventLogService.getInstance().filter(request);

        ListItemView<ServerEventLogView> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @GET
    @Path("{idServerEventLog}")
    @Operation(summary = "Get a Server Event Log instance by event", description = "Get a Server Event Log instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ServerEventLogView.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServerEventLog(
            @PathParam("idServerEventLog") String idServerEventLog,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(!userToken.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        if (!MongoAnnotationProcessor.isValidObjectId(idServerEventLog)) {
            return ApiResponseController.error("idTest is not valid");
        }

        GetItemRequest request = GetItemRequest.builder().userToken(userToken).id(idServerEventLog).build();
        GetItemResponse<ServerEventLogView> response = ServerEventLogService.getInstance().getItem(request);

        if(!response.hasItem()) {
            return ApiResponseController.notFound("Server event not found");
        }

        return ApiResponseController.ok(response.item());
    }

    @DELETE
    @Path("{idServerEventLog}")
    @Operation(summary = "Remove a Server Event Log instance identifier by parameter", description = "Delete a Server Event Log instance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteServerEventLog(
            @PathParam("idServerEventLog") String idServerEventLog,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(!userToken.isAdmin()) {
            return ApiResponseController.unauthorized("You are not an admin");
        }

        if (!MongoAnnotationProcessor.isValidObjectId(idServerEventLog)) {
            return ApiResponseController.error("idTest is not valid");
        }

        DeleteItemRequest request = DeleteItemRequest.builder().userToken(userToken).id(idServerEventLog).build();
        DeleteItemResponse<ServerEventLogView> response = ServerEventLogService.getInstance().deleteItem(request);

        if(response.deleted()) {
            return ApiResponseController.ok("Server Event Log deleted successfully");
        } else {
            return ApiResponseController.error("Error during delete server event log");
        }
    }

}
