package com.github.adrien.koumgang.minetomcat.apps.metadata.service;

import com.github.adrien.koumgang.minetomcat.apps.metadata.model.Metadata;
import com.github.adrien.koumgang.minetomcat.lib.model.Pagination;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.Condition;
import com.github.adrien.koumgang.minetomcat.lib.service.request.delete.DeleteItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.get.GetItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListFilterRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.post.PostItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.put.PutItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.response.delete.DeleteItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.get.GetItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.get.GetResponseMetadata;
import com.github.adrien.koumgang.minetomcat.lib.service.response.list.ListItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.post.PostItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.put.PutItemResponse;
import com.google.gson.reflect.TypeToken;
import com.github.adrien.koumgang.minetomcat.apps.metadata.dao.MetadataDao;
import com.github.adrien.koumgang.minetomcat.apps.metadata.repository.MetadataRepository;
import com.github.adrien.koumgang.minetomcat.apps.metadata.view.MetadataView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis.RedisInstance;
import com.github.adrien.koumgang.minetomcat.lib.log.MineLog;
import com.github.adrien.koumgang.minetomcat.lib.service.BaseService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.Type;
import java.util.*;

public class MetaDataService extends BaseService<MetadataView> {

    @Contract(" -> new")
    public static @NotNull MetaDataService getInstance() {
        return new MetaDataService(MetadataRepository.getInstance());
    }


    private final MetadataDao metaDataDao;

    private MetaDataService(MetadataDao metaDataDao) {
        this.metaDataDao = metaDataDao;
    }



    private static final Long TTL_CACHE_METADATA = 60 * 60L;



    private static @NotNull @Unmodifiable List<MetadataView> getMetaDataViews(@NotNull List<Metadata> metadataList) {
        return metadataList.stream().map(MetadataView::new).toList();
    }


    @Contract(pure = true)
    private static @NotNull String formMetadataKey(String id) {
        return "metadata:"  + id;
    }

    @Contract(pure = true)
    private static @NotNull String formMetadataKeyByType(UserToken userToken, String metaType) {
        return "metadata:type:"  + metaType + ":user:" + userToken.getIdUser() ;
    }


    @Contract(pure = true)
    private static @NotNull String formMetadataKeyByName(UserToken userToken, String metaType, String name) {
        return "metadata:type:"  + metaType + ":name:" + name + ":user:" + userToken.getIdUser();
    }

    @Nullable
    private MetadataView getMetaDataFromCache(UserToken userToken, String id) {
        if (!MongoAnnotationProcessor.isValidObjectId(id)) {
            return null;
        }

        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [CACHE] [GET] id: " + id);

        String key = formMetadataKey(id);

        try {
            MetadataView metaDataView = RedisInstance.getInstance().get(key, MetadataView.class);
            if (metaDataView != null) {
                try {
                    RedisInstance.getInstance().expire(key, TTL_CACHE_METADATA);
                } catch (Exception ignored) {}

                timePrinter.log();
                return metaDataView;
            }
            timePrinter.missing();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }

        return null;
    }

    private void cacheMetaData(UserToken userToken, MetadataView metaDataView) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [METADATA] [CACHE] [SET] data: " + gson.toJson(metaDataView)
        );

        String key = formMetadataKey(metaDataView.getIdMetaData());

        try {
            RedisInstance.getInstance().set(key, metaDataView, TTL_CACHE_METADATA);
            timePrinter.log();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }
    }

    private void scacheMetaData(UserToken userToken, String id) {
        if (!MongoAnnotationProcessor.isValidObjectId(id)) {
            return;
        }

        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [CACHE] [DELETE] id: " + id);

        String key = formMetadataKey(id);

        try {
            RedisInstance.getInstance().delete(key);
            timePrinter.log();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }
    }

    private @Nullable List<MetadataView> getMetaDataByTypeFromCache(UserToken userToken, String metaType) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [METADATA] [CACHE] [GET] meta Type: " + metaType
        );

        String key = formMetadataKeyByType(userToken, metaType);

        try {
            String value = RedisInstance.getInstance().get(key, String.class);
            if(value != null) {
                Type type = new TypeToken<List<MetadataView>>(){}.getType();
                List<MetadataView> metaDataViews = gson.fromJson(value, type);

                try {
                    RedisInstance.getInstance().expire(key, TTL_CACHE_METADATA);
                } catch (Exception ignored) {}

                timePrinter.log();
                return metaDataViews;
            }
            timePrinter.missing();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }

        return null;
    }

    private void cacheMetaDataByType(UserToken userToken, String metaType, List<MetadataView> metaDataViews) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [METADATA] [CACHE] [SET] meta type:" + metaType + ", data: " + gson.toJson(metaDataViews)
        );

        String key = formMetadataKeyByType(userToken, metaType);

        try {
            String value = gson.toJson(metaDataViews);
            RedisInstance.getInstance().set(key, value, TTL_CACHE_METADATA);
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }
    }

    private void scacheMetaDataByType(UserToken userToken, String metaType) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [METADATA] [CACHE] [DELETE] meta type: " + metaType
        );

        String key = formMetadataKeyByType(userToken, metaType);

        try {
            RedisInstance.getInstance().delete(key);
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }
    }

    private void scacheMetaData(UserToken userToken, String id,  String metaType) {
        scacheMetaData(userToken, id);
        scacheMetaDataByType(userToken, metaType);
    }


    public GetItemResponse<MetadataView> getItem(GetItemRequest request) {
        if(request.userToken().isEmpty()) throw new IllegalArgumentException("userToken is empty");
        UserToken userToken = request.userToken().get();

        MetadataView cachedData = getMetaDataFromCache(userToken, request.id());
        if(cachedData != null){
            return GetItemResponse.<MetadataView>builder()
                    .item(cachedData)
                    .responseMetadata(
                            GetResponseMetadata.of(
                                    Map.of("cache", "true")
                            )
                    )
                    .build();
        }


        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [GET] request: " + request);

        try {
            Optional<Metadata> optMetaData = metaDataDao.findById(request.id());

            if(optMetaData.isEmpty()) {
                timePrinter.missing("Metadata not found");
                return GetItemResponse.<MetadataView>builder().build();
            }

            MetadataView metaDataView = new MetadataView(optMetaData.get());

            cacheMetaData(userToken, metaDataView);

            timePrinter.log();

            return GetItemResponse.<MetadataView>builder().item(metaDataView).build();
        } catch (IllegalArgumentException e) {
            timePrinter.error(e.getMessage());
            return GetItemResponse.<MetadataView>builder().build();
        }
    }


    public List<MetadataView> listMetaDataByType(UserToken userToken, String metaType) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [METADATA] [LIST] meta type: " + metaType
        );

        List<Metadata> metadataList = metaDataDao.findByType(userToken.getIdUser(), metaType);

        List<MetadataView> metaDataViews = getMetaDataViews(metadataList);

        timePrinter.log();

        return metaDataViews;
    }


    public PostItemResponse<MetadataView> postItem(PostItemRequest<MetadataView> request) {
        if(request.hasItem() && request.hasUserToken() && request.userToken().isPresent()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                    "[SERVICE] [METADATA] [SAVE] request:" + request
            );

            UserToken userToken = request.userToken().get();

            try {
                Metadata metaData = new Metadata(request.item());
                String metaDataId = metaDataDao.save(metaData);

                if(metaDataId == null) {
                    timePrinter.error("Error saving metadata");
                    return PostItemResponse.<MetadataView>builder().build();
                }

                Optional<Metadata> optMetaData = metaDataDao.findById(metaDataId);

                if(optMetaData.isEmpty()) {
                    timePrinter.missing("Error saving metadata");
                    return PostItemResponse.<MetadataView>builder().build();
                }

                MetadataView metaDataView = new MetadataView(optMetaData.get());

                cacheMetaData(userToken, metaDataView);

                timePrinter.log();

                return PostItemResponse.<MetadataView>builder().item(metaDataView).build();
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }


        return PostItemResponse.<MetadataView>builder().build();
    }


    public PutItemResponse<MetadataView> updateItem(PutItemRequest<MetadataView> request) {
        if(request.hasItem() && request.id() != null && request.hasUserToken() && request.userToken().isPresent()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                    "[SERVICE] [METADATA] [UPDATE] request: " + request
            );

            try {
                Optional<Metadata> existingMetaData = metaDataDao.findById(request.id());

                if(existingMetaData.isPresent()) {
                    Metadata metaData = existingMetaData.get();
                    metaData.update(request.item());
                    boolean updated =  metaDataDao.update(metaData);

                    if(updated){
                        scacheMetaData(request.userToken().get(), request.id(), metaData.getMetadataType());

                        Optional<Metadata> optMetadata = metaDataDao.findById(request.id());

                        if(optMetadata.isPresent()) {
                            MetadataView metaDataView = new MetadataView(optMetadata.get());

                            timePrinter.log();

                            return PutItemResponse.<MetadataView>builder().item(metaDataView).updated(true).build();
                        }
                    }

                    timePrinter.error("Error during update metadata");
                } else {
                    timePrinter.missing("Metadata not found");
                }
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return PutItemResponse.<MetadataView>builder().item(request.item()).updated(false).build();
    }


    public DeleteItemResponse<MetadataView> deleteItem(DeleteItemRequest request) {
        if(request.hasUserToken() && request.userToken().isPresent() && request.id() != null) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [DELETE] request: " + request);

            try {
                Optional<Metadata> optMetaData = metaDataDao.findById(request.id());

                if(optMetaData.isPresent()) {
                    boolean deleted = metaDataDao.delete(request.id());

                    if(deleted){
                        scacheMetaData(request.userToken().get(), request.id(), optMetaData.get().getMetadataType());

                        MetadataView metadataView = new MetadataView(optMetaData.get());

                        timePrinter.log();

                        return DeleteItemResponse.<MetadataView>builder().item(metadataView).deleted(true).build();
                    }

                    timePrinter.error("Error during delete metadata");
                } else {
                    timePrinter.missing();
                }
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return DeleteItemResponse.<MetadataView>builder().deleted(false).build();
    }

    public ListItemResponse<MetadataView> list(ListItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [LIST] request: " + request);


        List<Metadata> metadataList = request.pagination()
                ? metaDataDao.findByType()
                : metaDataDao.findByType(request.page(), request.pageSize());

        List<MetadataView> metaDataViews = getMetaDataViews(metadataList);

        long total = metaDataDao.count();

        Pagination pagination = Pagination.builder()
                .page(request.page())
                .pageSize(request.pageSize())
                .total(total)
                .build();

        timePrinter.log();

        return ListItemResponse.<MetadataView>builder()
                .items(metaDataViews)
                .pagination(pagination)
                .paginated(request.pagination())
                .build();
    }

    private static Map<String, String> extractNameAndTypeFromCondition(Condition condition) {
        String name = null;
        String metadataType = null;

        switch (condition.comparisonOperator()) {
            case EQ: {
                if(condition.fieldValue() != null && condition.fieldValue().name() != null && condition.fieldValue().value() != null) {
                    MetadataFilter filter = MetadataFilter.getByValue(condition.fieldValue().name());
                    switch (filter) {
                        case NAME ->  name = condition.fieldValue().value().s();
                        case METADATA_TYPE -> metadataType = condition.fieldValue().value().s();
                        case null -> {}
                    }
                }
            } break;
            case AND: {
                if(condition.hasCondition() && condition.conditions() != null && !condition.conditions().isEmpty()) {
                    for(Condition innerCondition : condition.conditions()) {
                        Map<String, String> neFromCondition = extractNameAndTypeFromCondition(innerCondition);
                        if(neFromCondition.containsKey(MetadataFilter.NAME.getValue()) && neFromCondition.get(MetadataFilter.NAME.getValue()) != null) {
                            name = neFromCondition.get(MetadataFilter.NAME.getValue());
                        }
                        if(neFromCondition.containsKey(MetadataFilter.METADATA_TYPE.getValue()) && neFromCondition.get(MetadataFilter.METADATA_TYPE.getValue()) != null) {
                            metadataType = neFromCondition.get(MetadataFilter.METADATA_TYPE.getValue());
                        }
                    }
                }
            } break;
            case null, default: {}
        }

        Map<String, String> map = new HashMap<>();
        map.put(MetadataFilter.NAME.getValue(), name);
        map.put(MetadataFilter.METADATA_TYPE.getValue(), metadataType);
        return map;
    }

    @Override
    public ListItemResponse<MetadataView> filter(ListFilterRequest request) {
        if(request.hasUserToken() && request.userToken().isPresent()) {
            boolean callList = !request.hasCondition() || request.conditions() == null || request.conditions().isEmpty();

            if(callList) {
                return this.list(request.toListRequest());
            }

            String name = "";
            String metadataType = "";

            for(Condition condition : request.conditions()) {
                Map<String, String> map = extractNameAndTypeFromCondition(condition);
                if(map.containsKey(MetadataFilter.NAME.getValue()) && map.get(MetadataFilter.NAME.getValue()) != null) {
                    name = map.get(MetadataFilter.NAME.getValue());
                }
                if(map.containsKey(MetadataFilter.METADATA_TYPE.getValue()) && map.get(MetadataFilter.METADATA_TYPE.getValue()) != null) {
                    metadataType = map.get(MetadataFilter.METADATA_TYPE.getValue());
                }
            }

            if(name.isBlank() && metadataType.isBlank()) {
                return this.list(request.toListRequest());
            }


            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [METADATA] [LIST CONDITION] request: " + request);

            UserToken userToken = request.userToken().get();

            List<Metadata> metadataList;
            long total;
            if(name.isBlank()) {
                total = metaDataDao.count(userToken.getIdUser(), metadataType);
                metadataList = request.pagination()
                        ? metaDataDao.findByType(userToken.getIdUser(), metadataType, request.page(), request.pageSize())
                        : metaDataDao.findByType(userToken.getIdUser(), metadataType);
            } else {
                total = metaDataDao.count(userToken.getIdUser(), metadataType, name);
                metadataList = request.pagination()
                        ? metaDataDao.findByName(userToken.getIdUser(), metadataType, name, request.page(), request.pageSize())
                        : metaDataDao.findByName(userToken.getIdUser(), metadataType, name);
            }

            List<MetadataView> metadataViews = metadataList.stream().map(MetadataView::new).toList();

            Pagination pagination = Pagination.builder()
                    .page(request.page())
                    .pageSize(request.pageSize())
                    .total(total)
                    .build();

            timePrinter.log();

            return ListItemResponse.<MetadataView>builder()
                    .items(metadataViews)
                    .pagination(pagination)
                    .paginated(request.pagination())
                    .build();
        }

        return ListItemResponse.<MetadataView>builder().build();
    }

}
