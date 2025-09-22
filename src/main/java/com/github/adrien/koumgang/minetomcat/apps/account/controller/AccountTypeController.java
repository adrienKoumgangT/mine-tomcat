package com.github.adrien.koumgang.minetomcat.apps.account.controller;

import com.github.adrien.koumgang.minetomcat.apps.account.service.AccountTypeService;
import com.github.adrien.koumgang.minetomcat.apps.account.view.AccountTypeView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.controller.ApiResponseController;
import com.github.adrien.koumgang.minetomcat.lib.controller.BaseController;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.model.ListItemView;
import com.github.adrien.koumgang.minetomcat.lib.service.request.delete.DeleteItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.get.GetItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListItemRequest;
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

import java.util.List;

@Path("/account/type")
@Tag(name = "Account Types", description = "API operation related to account type")
public class AccountTypeController extends BaseController {


    @GET
    @Operation(summary = "Get list of a Account Type instance", description = "Return a list of Account Type instance")
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
    public Response getAllAccountTypes(
            @Parameter(description = "Page number (1-based), starting from 1", example = "1", required = false)
            @QueryParam("page") @DefaultValue("null") Integer page,
            @Parameter(description = "Page size (1-based), starting from 1", example = "10", required = false)
            @QueryParam("pageSize") @DefaultValue("100") Integer pageSize,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        ListItemRequest request = ListItemRequest.builder()
                .userToken(userToken)
                .page(page)
                .pageSize(pageSize)
                .pagination(page != null)
                .build();
        ListItemResponse<AccountTypeView> response = AccountTypeService.getInstance().list(request);

        ListItemView<AccountTypeView> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @POST
    @Operation(summary = "Create new account type", description = "Create new account type in the system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = { @Content(schema = @Schema(implementation = AccountTypeView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccountType(
            AccountTypeView accountTypeView,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        accountTypeView.checkIfValid();

        PostItemRequest<AccountTypeView> request = PostItemRequest.<AccountTypeView>builder()
                .userToken(userToken)
                .item(accountTypeView)
                .build();
        PostItemResponse<AccountTypeView> response = AccountTypeService.getInstance().postItem(request);

        if(!response.hasItem()){
            return ApiResponseController.error("Error during creation of account type");
        }

        return ApiResponseController.ok(response.item());
    }

    @PUT
    @Operation(summary = "Update a account type data instance", description = "Return a updated account type instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = { @Content(schema = @Schema(implementation = AccountTypeView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountType(
            AccountTypeView accountTypeView,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if(accountTypeView.getIdAccountType() == null) {
            return ApiResponseController.error("id account type required");
        }

        accountTypeView.checkIfValid();

        PutItemRequest<AccountTypeView> request = PutItemRequest.<AccountTypeView>builder()
                .userToken(userToken)
                .id(accountTypeView.getIdAccountType())
                .item(accountTypeView)
                .build();
        PutItemResponse<AccountTypeView> response = AccountTypeService.getInstance().updateItem(request);

        if(!response.updated()){
            return ApiResponseController.error("error while updating account type");
        }

        return ApiResponseController.ok(response.item());
    }

    @GET
    @Path("{idAccountType}")
    @Operation(summary = "Get a Account Type by id", description = "Return a Account Type as per id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Account Type found",
                    content = { @Content(schema = @Schema(implementation = AccountTypeView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Test Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountType(
            @Parameter(name = "idAccountType", description = "Account Type id", example = "68b28e50c8c86a733de632d8")
            @PathParam("idAccountType") String idAccountType,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if (!MongoAnnotationProcessor.isValidObjectId(idAccountType)) {
            return ApiResponseController.error("idAccountType is not valid");
        }

        GetItemRequest request = GetItemRequest.builder().userToken(userToken).id(idAccountType).build();
        GetItemResponse<AccountTypeView> response = AccountTypeService.getInstance().getItem(request);

        if(!response.hasItem()) {
            return ApiResponseController.notFound("Account Type not found");
        }

        return ApiResponseController.ok(response.item());
    }

    @DELETE
    @Path("{idAccountType}")
    @Operation(summary = "Remove a Account Type instance identifier by parameter", description = "Delete a Account Type instance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAccountType(
            @Parameter(name = "idAccountType", description = "Account Type id", example = "68b28e50c8c86a733de632d8")
            @PathParam("idAccountType") String idAccountType,
            @HeaderParam("Authorization") String token
    ) throws Exception {
        UserToken userToken = getUserToken(token);

        if (!MongoAnnotationProcessor.isValidObjectId(idAccountType)) {
            return ApiResponseController.error("idAccountType is not valid");
        }

        DeleteItemRequest request = DeleteItemRequest.builder()
                .userToken(userToken)
                .id(idAccountType)
                .build();
        DeleteItemResponse<AccountTypeView> response = AccountTypeService.getInstance().deleteItem(request);

        if(response.deleted()) {
            return ApiResponseController.ok("Account Type deleted successfully");
        } else {
            return ApiResponseController.error("Error during delete account type");
        }
    }

}
