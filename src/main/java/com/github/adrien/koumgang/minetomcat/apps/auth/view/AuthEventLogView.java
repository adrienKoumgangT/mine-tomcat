package com.github.adrien.koumgang.minetomcat.apps.auth.view;

import com.github.adrien.koumgang.minetomcat.apps.auth.model.AuthEventLog;
import com.github.adrien.koumgang.minetomcat.shared.view.RequestDataView;

import java.util.List;
import java.util.Map;

public class AuthEventLogView extends AuthEventLogSimpleView {

    private RequestDataView requestData;

    private String logIp;

    private Map<String, List<String>> serverData;

    public AuthEventLogView() {
        super();
    }

    public AuthEventLogView(AuthEventLog authEventLog) {
        super(authEventLog);

        if(authEventLog.getRequestData() != null) {
            this.requestData = new RequestDataView(authEventLog.getRequestData());
        }

        this.logIp      = authEventLog.getLogIp();
        this.serverData = authEventLog.getServerData();
    }

    public RequestDataView getRequestData() {
        return requestData;
    }

    public String getLogIp() {
        return logIp;
    }

    public Map<String, List<String>> getServerData() {
        return serverData;
    }
}
