package com.github.adrien.koumgang.minetomcat.apps.server.service;

import com.github.adrien.koumgang.minetomcat.apps.server.view.EventNamesView;
import com.github.adrien.koumgang.minetomcat.lib.model.Pagination;
import com.github.adrien.koumgang.minetomcat.lib.service.BaseService;
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
import com.github.adrien.koumgang.minetomcat.apps.server.dao.ServerEventLogDao;
import com.github.adrien.koumgang.minetomcat.apps.server.model.ServerEventLog;
import com.github.adrien.koumgang.minetomcat.apps.server.repository.ServerEventLogRepository;
import com.github.adrien.koumgang.minetomcat.apps.server.view.ServerEventLogView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.log.MineLog;

import java.util.*;

public class ServerEventLogService extends BaseService<ServerEventLogView> {

    public static ServerEventLogService getInstance() {
        return new ServerEventLogService(ServerEventLogRepository.getInstance());
    }


    private final ServerEventLogDao serverEventLogDao;

    private ServerEventLogService(ServerEventLogDao serverEventLogDao) {
        this.serverEventLogDao = serverEventLogDao;
    }


    public GetItemResponse<ServerEventLogView> getItem(GetItemRequest request) {

        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [SERVER EVENT LOG] [GET] request: " + request
        );

        try {
            Optional<ServerEventLog> serverEventLog = serverEventLogDao.findById(request.id());
            if (serverEventLog.isPresent()) {
                ServerEventLogView serverEventLogView = new ServerEventLogView(serverEventLog.get());
                timePrinter.log();
                return GetItemResponse.<ServerEventLogView>builder().item(serverEventLogView).build();
            }
        } catch (IllegalArgumentException ignored) { }

        timePrinter.missing("Server event not found");

        return GetItemResponse.<ServerEventLogView>builder().build();
    }


    public PostItemResponse<ServerEventLogView> postItem(PostItemRequest<ServerEventLogView> request) {
        if(request.hasItem()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                    "[SERVICE] [SERVER EVENT LOG] [SAVE] test: request:" + request
            );


            ServerEventLog serverEventLog = new ServerEventLog(request.item());
            if(request.hasUserToken() && request.userToken().isPresent()) {
                UserToken userToken = request.userToken().get();
                serverEventLog.setCreatedBy(userToken.getIdUser());
                serverEventLog.setUpdatedBy(userToken.getIdUser());
            }

            try {
                String serverEventLogId =  serverEventLogDao.save(serverEventLog);

                if(serverEventLogId == null) {
                    timePrinter.error("Error saving server event log");
                    return null;
                }

                Optional<ServerEventLog> optServerEventLog = serverEventLogDao.findById(serverEventLogId);

                if(optServerEventLog.isEmpty()) {
                    timePrinter.error("Error saving server event log");
                    return null;
                }

                ServerEventLogView serverEventLogView = new ServerEventLogView(optServerEventLog.get());

                timePrinter.log();

                return PostItemResponse.<ServerEventLogView>builder().item(serverEventLogView).created(true).build();
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return PostItemResponse.<ServerEventLogView>builder().created(false).build();
    }

    public PutItemResponse<ServerEventLogView> updateItem(PutItemRequest<ServerEventLogView> request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DeleteItemResponse<ServerEventLogView> deleteItem(DeleteItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [SERVER EVENT LOG] [DELETE] request: " + request
        );

        try {
            Optional<ServerEventLog> serverEventLog = serverEventLogDao.findById(request.id());

            if (serverEventLog.isPresent()) {
                ServerEventLogView serverEventLogView = new ServerEventLogView(serverEventLog.get());

                boolean deleted = serverEventLogDao.delete(request.id());

                timePrinter.log();

                return DeleteItemResponse.<ServerEventLogView>builder().item(serverEventLogView).deleted(deleted).build();
            }
        } catch (IllegalArgumentException e) {
            timePrinter.error(e.getMessage());
        }

        return DeleteItemResponse.<ServerEventLogView>builder().deleted(false).build();
    }

    public ListItemResponse<ServerEventLogView> list(ListItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [SERVER EVENT LOG] [LIST] request: " + request);

        List<ServerEventLog> serverEventLogs = request.pagination()
                ? serverEventLogDao.findAll(request.page(), request.pageSize())
                : serverEventLogDao.findAll();

        List<ServerEventLogView> serverEventLogViews = serverEventLogs.stream().map(ServerEventLogView::new).toList();

        long total = serverEventLogDao.count();

        Pagination pagination = Pagination.builder()
                .page(request.page())
                .pageSize(request.pageSize())
                .total(total)
                .build();

        timePrinter.log();

        return ListItemResponse.<ServerEventLogView>builder()
                .items(serverEventLogViews)
                .pagination(pagination)
                .paginated(request.pagination())
                .build();
    }

    private static Map<String, String> extractNameEventFromCondition(Condition condition) {
        String name = null;
        String event = null;

        switch (condition.comparisonOperator()) {
            case EQ: {
                if(condition.fieldValue() != null && condition.fieldValue().name() != null && condition.fieldValue().value() != null) {
                    ServerEventLogFilter filter = ServerEventLogFilter.getByValue(condition.fieldValue().name());
                    switch (filter) {
                        case NAME ->  name = condition.fieldValue().value().s();
                        case EVENT -> event = condition.fieldValue().value().s();
                        case null -> {}
                    }
                }
            } break;
            case AND: {
                if(condition.hasCondition() && condition.conditions() != null && !condition.conditions().isEmpty()) {
                    for(Condition innerCondition : condition.conditions()) {
                        Map<String, String> neFromCondition = extractNameEventFromCondition(innerCondition);
                        if(neFromCondition.containsKey(ServerEventLogFilter.NAME.getValue()) && neFromCondition.get(ServerEventLogFilter.NAME.getValue()) != null) {
                            name = neFromCondition.get(ServerEventLogFilter.NAME.getValue());
                        }
                        if(neFromCondition.containsKey(ServerEventLogFilter.EVENT.getValue()) && neFromCondition.get(ServerEventLogFilter.EVENT.getValue()) != null) {
                            event = neFromCondition.get(ServerEventLogFilter.EVENT.getValue());
                        }
                    }
                }
            } break;
            case null, default: {}
        }

        Map<String, String> map = new HashMap<>();
        map.put(ServerEventLogFilter.NAME.getValue(), name);
        map.put(ServerEventLogFilter.EVENT.getValue(), event);
        return map;
    }

    public ListItemResponse<ServerEventLogView> filter(ListFilterRequest request) {

        boolean callList = !request.hasCondition() || request.conditions() == null || request.conditions().isEmpty();

        if(callList) {
            return this.list(request.toListRequest());
        }

        String name = "";
        String event = "";

        for(Condition condition : request.conditions()) {
            Map<String, String> map = extractNameEventFromCondition(condition);
            if(map.containsKey(ServerEventLogFilter.NAME.getValue()) && map.get(ServerEventLogFilter.NAME.getValue()) != null) {
                name = map.get(ServerEventLogFilter.NAME.getValue());
            }
            if(map.containsKey(ServerEventLogFilter.EVENT.getValue()) && map.get(ServerEventLogFilter.EVENT.getValue()) != null) {
                event = map.get(ServerEventLogFilter.EVENT.getValue());
            }
        }

        if(name.isBlank() && event.isBlank()) {
            return this.list(request.toListRequest());
        }

        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [SERVER EVENT LOG] [LIST CONDITION] request: " + request);


        List<ServerEventLog> serverEventLogs;
        long total;
        if(event.isBlank()) {
            total = serverEventLogDao.countByName(name);
            serverEventLogs = request.pagination()
                    ? serverEventLogDao.findByName(name, request.page(), request.pageSize())
                    : serverEventLogDao.findByName(name);
        } else {
            total = serverEventLogDao.countByEvent(event);
            serverEventLogs = request.pagination()
                    ? serverEventLogDao.findByEvent(event, request.page(), request.pageSize())
                    : serverEventLogDao.findByEvent(event);
        }

        List<ServerEventLogView> serverEventLogViews = serverEventLogs.stream().map(ServerEventLogView::new).toList();

        Pagination pagination = Pagination.builder()
                .page(request.page())
                .pageSize(request.pageSize())
                .total(total)
                .build();

        timePrinter.log();

        return ListItemResponse.<ServerEventLogView>builder()
                .items(serverEventLogViews)
                .pagination(pagination)
                .paginated(request.pagination())
                .build();
    }

    public ListItemResponse<String> listEvents(ListItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [SERVER EVENT LOG] [EVENTS] request" + request);

        List<String> events = serverEventLogDao.listDistinctEvents();

        Pagination pagination = Pagination.builder()
                .page(1)
                .pageSize(events.size())
                .total((long) events.size())
                .build();

        timePrinter.log();

        return ListItemResponse.<String>builder()
                .items(events)
                .pagination(pagination)
                .paginated(false)
                .build();
    }

    public ListItemResponse<EventNamesView> listEventsNames(ListItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [SERVER EVENT LOG] [DISTINCT EVENTS NAMES] ");

        Map<String, List<String>> eventsNames = ((ServerEventLogRepository) serverEventLogDao).mapDistinctEventsNames();

        List<EventNamesView> result = EventNamesView.fromMap(eventsNames);

        Pagination pagination = Pagination.builder()
                .page(1)
                .pageSize(result.size())
                .total((long) result.size())
                .build();

        timePrinter.log();

        return ListItemResponse.<EventNamesView>builder()
                .items(result)
                .pagination(pagination)
                .paginated(false)
                .build();
    }

}
