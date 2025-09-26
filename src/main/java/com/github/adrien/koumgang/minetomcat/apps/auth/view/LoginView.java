package com.github.adrien.koumgang.minetomcat.apps.auth.view;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class LoginView extends BaseView {

    @Required
    private String email;

    @Required
    private String password;

    public LoginView() {}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
