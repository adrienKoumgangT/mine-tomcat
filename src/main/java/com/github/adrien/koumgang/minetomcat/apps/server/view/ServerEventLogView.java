package com.github.adrien.koumgang.minetomcat.apps.server.view;

import com.github.adrien.koumgang.minetomcat.apps.server.model.ServerEventLog;
import com.github.adrien.koumgang.minetomcat.shared.view.RequestDataView;

import java.util.Date;

public class ServerEventLogView extends ServerEventLogSimpleView {

    private RequestDataView requestData;

    public ServerEventLogView() {
        super();
    }

    public ServerEventLogView(ServerEventLog serverEventLog) {
        super(serverEventLog);

        if(serverEventLog.getRequestData() != null) {
            this.requestData = new RequestDataView(serverEventLog.getRequestData());
        }
    }

    public ServerEventLogView(
            String idServerEventLog,
            String event,
            String curl,
            String name,
            String message,
            String file,
            Date createdAt,
            RequestDataView requestData
    ) {
        super(idServerEventLog, event, curl, name, message, file, createdAt);
        this.requestData = requestData;
    }

    public RequestDataView getRequestData() {
        return requestData;
    }
}
