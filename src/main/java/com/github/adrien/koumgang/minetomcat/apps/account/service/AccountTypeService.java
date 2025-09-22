package com.github.adrien.koumgang.minetomcat.apps.account.service;

import com.github.adrien.koumgang.minetomcat.apps.account.view.AccountTypeView;
import com.github.adrien.koumgang.minetomcat.apps.metadata.service.MetaDataService;
import com.github.adrien.koumgang.minetomcat.apps.metadata.service.MetadataFilter;
import com.github.adrien.koumgang.minetomcat.apps.metadata.view.MetadataView;
import com.github.adrien.koumgang.minetomcat.lib.log.MineLog;
import com.github.adrien.koumgang.minetomcat.lib.service.BaseService;
import com.github.adrien.koumgang.minetomcat.lib.service.FieldValue;
import com.github.adrien.koumgang.minetomcat.lib.service.Value;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.ComparisonOperator;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.Condition;
import com.github.adrien.koumgang.minetomcat.lib.service.request.delete.DeleteItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.get.GetItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListFilterRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.post.PostItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.put.PutItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.response.delete.DeleteItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.get.GetItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.list.ListItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.post.PostItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.put.PutItemResponse;

import java.util.*;

public class AccountTypeService extends BaseService<AccountTypeView> {

    public static AccountTypeService getInstance() {
        return new AccountTypeService(MetaDataService.getInstance());
    }


    private final MetaDataService metaDataService;

    private AccountTypeService(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }


    private static AccountTypeView formAccountTypeView(MetadataView metaData) {
        return new AccountTypeView(
                metaData.getIdMetaData(),
                metaData.getName(),
                metaData.getDescription(),
                metaData.getIsDefault()
        );
    }


    private static final String METADATA_TYPE = "account-type";


    public GetItemResponse<AccountTypeView> getItem(GetItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [TYPE] [GET] request: " + request);

        GetItemResponse<MetadataView> response = metaDataService.getItem(request);

        if(!response.hasItem()) {
            timePrinter.missing();
            return GetItemResponse.<AccountTypeView>builder().build();
        }

        AccountTypeView accountTypeView = formAccountTypeView(response.item());

        timePrinter.log();

        return GetItemResponse.<AccountTypeView>builder().item(accountTypeView).build();
    }

    public PostItemResponse<AccountTypeView> postItem(PostItemRequest<AccountTypeView> request) {
        if(request.hasItem() && request.hasUserToken() && request.userToken().isPresent()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                    "[SERVICE] [ACCOUNT] [TYPE] [CREATE] request: " + request
            );

            AccountTypeView accountTypeDetail = request.item();

            MetadataView metaDataView = new MetadataView(
                    accountTypeDetail.getIdAccountType(),
                    METADATA_TYPE,
                    accountTypeDetail.getName(),
                    accountTypeDetail.getDescription(),
                    null,
                    null,
                    accountTypeDetail.getIsDefault()
            );

            PostItemRequest<MetadataView> postItemRequest = PostItemRequest.<MetadataView>builder()
                    .item(metaDataView)
                    .userToken(request.userToken().get())
                    .build();
            PostItemResponse<MetadataView> postItemResponse = metaDataService.postItem(postItemRequest);

            if(postItemResponse.hasItem()) {
                AccountTypeView accountTypeView = formAccountTypeView(postItemResponse.item());

                timePrinter.log();

                return PostItemResponse.<AccountTypeView>builder().item(accountTypeView).build();
            }

            timePrinter.error("Error during saving account type");
        }

        return PostItemResponse.<AccountTypeView>builder().build();
    }

    public PutItemResponse<AccountTypeView> updateItem(PutItemRequest<AccountTypeView> request) {
        if(request.hasItem() && request.id() != null && request.hasUserToken() && request.userToken().isPresent()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                    "[SERVICE] [ACCOUNT] [TYPE] [UPDATE] request: " + request
            );

            AccountTypeView accountTypeDetail = request.item();

            MetadataView metaDataView = new MetadataView(
                    accountTypeDetail.getIdAccountType(),
                    METADATA_TYPE,
                    accountTypeDetail.getName(),
                    accountTypeDetail.getDescription(),
                    null,
                    null,
                    accountTypeDetail.getIsDefault()
            );

            PutItemRequest<MetadataView> putItemRequest = PutItemRequest.<MetadataView>builder()
                    .item(metaDataView)
                    .userToken(request.userToken().get())
                    .build();
            PutItemResponse<MetadataView> putItemResponse = metaDataService.updateItem(putItemRequest);

            if(putItemResponse.updated()) {
                AccountTypeView accountTypeView = formAccountTypeView(putItemResponse.item());

                timePrinter.log();

                return PutItemResponse.<AccountTypeView>builder().item(accountTypeView).updated(true).build();
            }
            timePrinter.error("Error during updating account type");
        }

        return PutItemResponse.<AccountTypeView>builder().item(request.item()).updated(false).build();
    }


    public DeleteItemResponse<AccountTypeView> deleteItem(DeleteItemRequest request) {
        if(request.hasUserToken() && request.id() != null) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [TYPE] [DELETE] request: " + request);

            DeleteItemResponse<MetadataView> deleteItemResponse = metaDataService.deleteItem(request);

            if(deleteItemResponse.deleted()) {
                AccountTypeView accountTypeView = formAccountTypeView(deleteItemResponse.item());

                timePrinter.log();

                DeleteItemResponse.<AccountTypeView>builder().item(accountTypeView).deleted(true).build();
            }
            timePrinter.error("Error during deleting account type");
        }

        return DeleteItemResponse.<AccountTypeView>builder().deleted(false).build();
    }


    public ListItemResponse<AccountTypeView> list(ListItemRequest request) {
        if(request.hasUserToken() && request.userToken().isPresent()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [LIST] request: " + request);

            ListFilterRequest listFilterRequest = ListFilterRequest.builder()
                    .userToken(request.userToken().get())
                    .page(request.page())
                    .pageSize(request.pageSize())
                    .pagination(request.pagination())
                    .conditions(
                            Condition.builder()
                                    .comparisonOperator(ComparisonOperator.EQ)
                                    .fieldValue(
                                            FieldValue.builder()
                                                    .name(MetadataFilter.METADATA_TYPE.getValue())
                                                    .value(
                                                            Value.builder()
                                                                    .s(METADATA_TYPE)
                                                                    .build()
                                                    ).build()
                                    ).build()
                    )
                    .build();
            ListItemResponse<MetadataView> listItemResponse = metaDataService.filter(listFilterRequest);

            List<AccountTypeView> accountTypeViews = listItemResponse.items().stream().map(AccountTypeService::formAccountTypeView).toList();

            timePrinter.log();

            return ListItemResponse.<AccountTypeView>builder()
                    .items(accountTypeViews)
                    .pagination(listItemResponse.pagination())
                    .paginated(request.pagination())
                    .build();
        }

        return ListItemResponse.<AccountTypeView>builder().build();
    }

    private static Map<String, String> extractNameFromCondition(Condition condition) {
        String name = null;

        switch (condition.comparisonOperator()) {
            case EQ: {
                if(condition.fieldValue() != null && condition.fieldValue().name() != null && condition.fieldValue().value() != null) {
                    MetadataFilter filter = MetadataFilter.getByValue(condition.fieldValue().name());
                    switch (filter) {
                        case NAME ->  name = condition.fieldValue().value().s();
                        case null, default -> {}
                    }
                }
            } break;
            case null, default: {}
        }

        Map<String, String> map = new HashMap<>();
        map.put(MetadataFilter.NAME.getValue(), name);
        return map;
    }

    public ListItemResponse<AccountTypeView> filter(ListFilterRequest request) {
        if(request.hasUserToken() && request.userToken().isPresent()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [LIST] request: " + request);

            String name = "";
            for(Condition condition: request.conditions()) {
                Map<String, String> map = extractNameFromCondition(condition);
                if(map.containsKey(MetadataFilter.NAME.getValue())) {
                    name = map.get(MetadataFilter.NAME.getValue());
                }
            }

            List<Condition> conditions = new ArrayList<>();
            conditions.add(
                    Condition.builder()
                            .comparisonOperator(ComparisonOperator.EQ)
                            .fieldValue(
                                    FieldValue.builder()
                                            .name(MetadataFilter.METADATA_TYPE.getValue())
                                            .value(
                                                    Value.builder()
                                                            .s(METADATA_TYPE)
                                                            .build()
                                            ).build()
                            ).build()
            );
            if(name != null && !name.isBlank()) {
                conditions.add(
                        Condition.builder()
                                .comparisonOperator(ComparisonOperator.EQ)
                                .fieldValue(
                                        FieldValue.builder()
                                                .name(MetadataFilter.NAME.getValue())
                                                .value(
                                                        Value.builder()
                                                                .s(name)
                                                                .build()
                                                ).build()
                                ).build()
                );
            }

            ListFilterRequest listFilterRequest = ListFilterRequest.builder()
                    .userToken(request.userToken().get())
                    .page(request.page())
                    .pageSize(request.pageSize())
                    .pagination(request.pagination())
                    .conditions(
                            Condition.builder()
                                    .comparisonOperator(ComparisonOperator.AND)
                                    .conditions(conditions)
                                    .build()
                    )
                    .build();
            ListItemResponse<MetadataView> listItemResponse = metaDataService.filter(listFilterRequest);

            List<AccountTypeView> accountTypeViews = listItemResponse.items().stream().map(AccountTypeService::formAccountTypeView).toList();

            timePrinter.log();

            return ListItemResponse.<AccountTypeView>builder()
                    .items(accountTypeViews)
                    .pagination(listItemResponse.pagination())
                    .paginated(request.pagination())
                    .build();
        }

        return ListItemResponse.<AccountTypeView>builder().build();
    }

}
