package com.github.adrien.koumgang.minetomcat.shared.view;

import com.github.adrien.koumgang.minetomcat.apps.user.model.LoginHistory;

import java.util.Date;

public class LoginHistorySimpleView {

    private Date loginTime;

    private String status;

    public LoginHistorySimpleView() {}

    public LoginHistorySimpleView(Date loginTime, String status) {
        this.loginTime = loginTime;
        this.status = status;
    }

    public LoginHistorySimpleView(LoginHistory loginHistory) {
        this.loginTime = loginHistory.getLoginTime();
        this.status = loginHistory.getStatus();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public String getStatus() {
        return status;
    }
}
