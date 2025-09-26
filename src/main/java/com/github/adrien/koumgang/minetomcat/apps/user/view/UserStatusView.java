package com.github.adrien.koumgang.minetomcat.apps.user.view;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class UserStatusView extends BaseView {

    private int idUserStatus;

    @Required
    private String code;

    @Required
    private String label;

    private String color;

    public UserStatusView() {}

    public UserStatusView(int idUserStatus, String code, String label, String color) {
        this.idUserStatus = idUserStatus;
        this.code = code;
        this.label = label;
        this.color = color;
    }

    public int getIdUserStatus() {
        return idUserStatus;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getColor() {
        return color;
    }
}
