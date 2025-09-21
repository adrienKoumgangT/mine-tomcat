package com.github.adrien.koumgang.minetomcat.apps.test.controller;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.github.adrien.koumgang.minetomcat.apps.test.service.TestService;
import com.github.adrien.koumgang.minetomcat.apps.test.view.TestView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.controller.ApiResponseController;
import com.github.adrien.koumgang.minetomcat.lib.controller.BaseController;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.view.PaginationView;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test")
@Tag(name = "Tests", description = "API operation related to test")
public class TestController extends BaseController {


    @GET
    @Operation(summary = "Get list of a Test instance", description = "Return a list of Test instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TestView.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTests() throws Exception {
        ListItemRequest request = ListItemRequest.builder().build();
        ListItemResponse<TestView> response = TestService.getInstance().list(request);

        return ApiResponseController.ok(response.items());
    }

    @GET
    @Path("ids")
    @Operation(summary = "Get list of id of a Test instance", description = "Return a list of id of Test instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllIdTests() throws Exception {
        ListItemRequest request = ListItemRequest.builder().ids(true).build();
        ListItemResponse<TestView> response = TestService.getInstance().list(request);

        return ApiResponseController.ok(response.ids());
    }

    @GET
    @Path("list")
    @Operation(summary = "Get list of a Test instance", description = "Return a list of Test instance")
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
    public Response getAllTestsPaginated(
            @Parameter(description = "Page number (1-based), starting from 1", example = "1", required = true)
            @QueryParam("page") @DefaultValue("1") Integer page,
            @Parameter(description = "Page size (1-based), starting from 1", example = "10", required = false)
            @QueryParam("pageSize") @DefaultValue("1") Integer pageSize
    ) throws Exception {
        ListItemRequest request = ListItemRequest.builder().pagination(true).page(page).pageSize(pageSize).build();
        ListItemResponse<TestView> response = TestService.getInstance().list(request);

        ListItemView<TestView> itemViews = new ListItemView<>(response.items(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @GET
    @Path("ids/list")
    @Operation(summary = "Get list of id of a Test instance", description = "Return a list of id of Test instance")
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
    public Response getAllIdsTestsPaginated(
            @Parameter(description = "Page number (1-based), starting from 1", example = "1", required = true)
            @QueryParam("page") @DefaultValue("1") Integer page,
            @Parameter(description = "Page size (1-based), starting from 1", example = "10", required = false)
            @QueryParam("pageSize") @DefaultValue("1") Integer pageSize
    ) throws Exception {
        ListItemRequest request = ListItemRequest.builder().ids(true).pagination(true).page(page).pageSize(pageSize).build();
        ListItemResponse<TestView> response = TestService.getInstance().list(request);

        ListItemView<String> itemViews = new ListItemView<>(response.ids(), PaginationView.of(response.pagination()));

        return ApiResponseController.ok(itemViews);
    }

    @GET
    @Path("secure")
    @Operation(summary = "Get list of a Test instance", description = "Return a list of Test instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TestView.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTestsWithToken(@HeaderParam("Authorization") String token) throws Exception {
        UserToken userToken = getUserToken(token);

        ListItemRequest request = ListItemRequest.builder().userToken(userToken).build();
        ListItemResponse<TestView> response = TestService.getInstance().list(request);

        return ApiResponseController.ok(response.items());
    }

    @GET
    @Path("{idTest}")
    @Operation(summary = "Get a Test by id", description = "Return a Test as per id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Test found",
                    content = { @Content(schema = @Schema(implementation = TestView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Test Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest(
            @Parameter(name = "idTest", description = "Test id", example = "68b28e50c8c86a733de632d8")
            @PathParam("idTest") String idTest
    ) throws Exception {
        if (!MongoAnnotationProcessor.isValidObjectId(idTest)) {
            return ApiResponseController.error("idTest is not valid");
        }

        GetItemRequest request = GetItemRequest.builder().id(idTest).build();

        GetItemResponse<TestView> response = TestService.getInstance().getItem(request);

        if(!response.hasItem()) {
            return ApiResponseController.notFound("Test not found");
        }

        return ApiResponseController.ok(response.item());
    }

    @PUT
    @Path("{idTest}")
    @Operation(summary = "Update a Test instance", description = "Return a Test instance updated")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = { @Content(schema = @Schema(implementation = TestView.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTest(
            TestView testView,
            @Parameter(name = "idTest", description = "Test id", example = "68b28e50c8c86a733de632d8")
            @PathParam("idTest") String idTest
    ) throws Exception {
        testView.checkIfValid();

        PutItemRequest<TestView> request = PutItemRequest.<TestView>builder().id(idTest).build();
        PutItemResponse<TestView> response = TestService.getInstance().updateItem(request);

        if(!response.updated() || !response.hasItem()) {
            return ApiResponseController.error("Error during save test");
        }

        return ApiResponseController.ok(response.item());
    }


    @POST
    @Operation(summary = "Create new Test instance", description = "Return a new Test instance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = { @Content(schema = @Schema(implementation = TestView.class)) }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTest(
            TestView testView
    ) throws Exception {
        testView.checkIfValid();

        PostItemRequest<TestView> request = PostItemRequest.<TestView>builder().item(testView).build();
        PostItemResponse<TestView> response = TestService.getInstance().postItem(request);

        if(!response.created() || !response.hasItem()) {
            return ApiResponseController.error("Error during save test");
        }

        return ApiResponseController.ok(response.item());
    }


    @DELETE
    @Path("{idTest}")
    @Operation(summary = "Remove a Test instance identifier by parameter", description = "Delete a Test instance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTest(
            @Parameter(name = "idTest", description = "Test id", example = "68b28e50c8c86a733de632d8")
            @PathParam("idTest") String idTest
    ) throws Exception {
        if (!MongoAnnotationProcessor.isValidObjectId(idTest)) {
            return ApiResponseController.error("idTest is not valid");
        }

        DeleteItemRequest request = DeleteItemRequest.builder().id(idTest).build();
        DeleteItemResponse<TestView> response = TestService.getInstance().deleteItem(request);


        if(response.deleted()) {
            return ApiResponseController.ok("Test deleted successfully");
        } else {
            return ApiResponseController.error("Error during delete test");
        }
    }

}
