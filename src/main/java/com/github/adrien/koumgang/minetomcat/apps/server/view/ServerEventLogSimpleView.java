package com.github.adrien.koumgang.minetomcat.apps.server.view;


import com.github.adrien.koumgang.minetomcat.apps.server.model.ServerEventLog;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

import java.util.Date;

public class ServerEventLogSimpleView extends BaseView {

    private String idServerEventLog;

    @Required
    private String event;

    private String curl;

    @Required
    private String name;

    private String message;

    private String file;

    private Date createdAt;

    public ServerEventLogSimpleView() {}

    public ServerEventLogSimpleView(ServerEventLog serverEventLog) {
        this.idServerEventLog = StringIdConverter.getInstance().fromObjectId(serverEventLog.getServerEventLogId());

        this.event      = serverEventLog.getEvent();
        this.curl       = serverEventLog.getCurl();
        this.name       = serverEventLog.getName();
        this.message    = serverEventLog.getMessage();
        this.file       = serverEventLog.getFile();

        this.createdAt  = serverEventLog.getCreatedAt();
    }

    public ServerEventLogSimpleView(
            String idServerEventLog,
            String event,
            String curl,
            String name,
            String message,
            String file,
            Date createdAt
    ) {
        this.idServerEventLog = idServerEventLog;
        this.event = event;
        this.curl = curl;
        this.name = name;
        this.message = message;
        this.file = file;
        this.createdAt = createdAt;
    }

    public String getIdServerEventLog() {
        return idServerEventLog;
    }

    public String getEvent() {
        return event;
    }

    public String getCurl() {
        return curl;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getFile() {
        return file;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
