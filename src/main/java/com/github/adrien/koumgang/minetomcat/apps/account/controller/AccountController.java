package com.github.adrien.koumgang.minetomcat.apps.account.controller;

import com.github.adrien.koumgang.minetomcat.apps.account.service.AccountFilter;
import com.github.adrien.koumgang.minetomcat.apps.account.service.AccountService;
import com.github.adrien.koumgang.minetomcat.apps.account.view.AccountView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.controller.ApiResponseController;
import com.github.adrien.koumgang.minetomcat.lib.controller.BaseController;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.model.ListItemView;
import com.github.adrien.koumgang.minetomcat.lib.service.FieldValue;
import com.github.adrien.koumgang.minetomcat.lib.service.Value;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.ComparisonOperator;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.Condition;
import com.github.adrien.koumgang.minetomcat.lib.service.request.delete.DeleteItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.get.GetItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListFilterRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.post.PostItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.put.PutItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.response.delete.DeleteItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.get.GetItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.list.ListItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.post.PostItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.put.PutItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.view.PaginationView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/account")
@Tag(name = "Accounts", description = "API operation related to account")
public class AccountController extends BaseController {

    @GET
    @Operation(summary = "Get list of a Account instance", description = "Return a list of Account instance")
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
    public Response getAllAccounts(
            @Parameter(description = "If account is archived or not", example = "true")
            @QueryParam("isArchived") @DefaultValue("false") Boolean isArchived,
            @Parameter(description = "Page number (1-based), starting from 1", example = "1")
            @QueryParam("page") @DefaultValue("null") Integer page,
            @Parameter(description = "Page size (1-based), starting from 1", example = "10")
            @QueryParam("pageSize") @DefaultValue("100") Integer pageSize,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        ListFilterRequest request = ListFilterRequest.builder()
                .userToken(userToken)
                .page(page)
                .pageSize(pageSize)
                .pagination(page != null)
                .conditions(
                        isArchived == null
                                ? null
                                : Condition.builder()
                                        .comparisonOperator(ComparisonOperator.EQ)
                                        .fieldValue(
                                                FieldValue.builder()
                                                        .name(AccountFilter.IS_ARCHIVED.getValue())
                                                        .value(
                                                                Value.builder()
                                                                        .bool(isArchived)
                                                                        .build()
                                                        )
                                                        .build()
                                        ).build()
                ).build();
        ListItemResponse<AccountView> response = AccountService.getInstance().filter(request);

        ListItemView<AccountView> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @POST
    @Operation(summary = "Create new account", description = "Create new account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = { @Content(schema = @Schema(implementation = AccountView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(
            AccountView accountView,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        accountView.checkIfValid();

        PostItemRequest<AccountView> request = PostItemRequest.<AccountView>builder()
                .userToken(userToken)
                .item(accountView)
                .build();
        PostItemResponse<AccountView> response = AccountService.getInstance().postItem(request);

        if(!response.hasItem()){
            return ApiResponseController.error("Error during creation of account");
        }

        return ApiResponseController.ok(response.item());
    }

    @PUT
    @Operation(summary = "Update a account data instance", description = "Return a updated account instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = { @Content(schema = @Schema(implementation = AccountView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(
            AccountView accountView,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(accountView.getIdAccount() == null) {
            return ApiResponseController.error("id account required");
        }

        accountView.checkIfValid();

        PutItemRequest<AccountView> request = PutItemRequest.<AccountView>builder()
                .userToken(userToken)
                .id(accountView.getIdAccount())
                .item(accountView)
                .build();
        PutItemResponse<AccountView> response = AccountService.getInstance().updateItem(request);

        if(!response.updated()){
            return ApiResponseController.error("error while updating account");
        }

        return ApiResponseController.ok(response.item());
    }


    @GET
    @Path("{idAccount}")
    @Operation(summary = "Get a Account by id", description = "Return a Account as per id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Account found",
                    content = { @Content(schema = @Schema(implementation = AccountView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Account Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(
            @Parameter(name = "idAccount", description = "Account id", example = "68b28e50c8c86a733de632d8")
            @PathParam("idAccount") String idAccount,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if (!MongoAnnotationProcessor.isValidObjectId(idAccount)) {
            return ApiResponseController.error("idAccount is not valid");
        }

        GetItemRequest request = GetItemRequest.builder().userToken(userToken).id(idAccount).build();
        GetItemResponse<AccountView> response = AccountService.getInstance().getItem(request);

        if(!response.hasItem()) {
            return ApiResponseController.notFound("Account not found");
        }

        return ApiResponseController.ok(response.item());
    }

    @DELETE
    @Path("{idAccount}")
    @Operation(summary = "Remove a Account instance identifier by parameter", description = "Delete a Account instance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAccount(
            @Parameter(name = "idAccount", description = "Account id", example = "68b28e50c8c86a733de632d8")
            @PathParam("idAccount") String idAccount,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if (!MongoAnnotationProcessor.isValidObjectId(idAccount)) {
            return ApiResponseController.error("idAccount is not valid");
        }

        DeleteItemRequest request = DeleteItemRequest.builder().userToken(userToken).id(idAccount).build();
        DeleteItemResponse<AccountView> response = AccountService.getInstance().deleteItem(request);

        if(response.deleted()) {
            return ApiResponseController.ok("Account deleted successfully");
        } else {
            return ApiResponseController.error("Error during delete account");
        }
    }

}
