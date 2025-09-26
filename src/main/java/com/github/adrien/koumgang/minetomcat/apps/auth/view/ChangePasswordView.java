package com.github.adrien.koumgang.minetomcat.apps.auth.view;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class ChangePasswordView extends BaseView {

    @Required
    private String oldPassword;

    @Required
    private String newPassword;

    public ChangePasswordView() {}

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
