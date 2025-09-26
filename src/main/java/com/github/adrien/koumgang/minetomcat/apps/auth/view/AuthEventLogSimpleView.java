package com.github.adrien.koumgang.minetomcat.apps.auth.view;

import com.github.adrien.koumgang.minetomcat.apps.auth.model.AuthEventLog;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;

public class AuthEventLogSimpleView extends BaseView {

    private String authEventLogId;

    private String event;

    private Boolean isSuccess;

    private String message;

    public AuthEventLogSimpleView() {}

    public AuthEventLogSimpleView(AuthEventLog authEventLog) {
        this.authEventLogId = StringIdConverter.getInstance().fromObjectId(authEventLog.getAuthEventLogId());

        this.event      = authEventLog.getEvent();
        this.isSuccess  = authEventLog.getSuccess();
        this.message    = authEventLog.getMessage();
    }

    public String getAuthEventLogId() {
        return authEventLogId;
    }

    public String getEvent() {
        return event;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
