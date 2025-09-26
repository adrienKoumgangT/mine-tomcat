package com.github.adrien.koumgang.minetomcat.apps.user.view;


import com.github.adrien.koumgang.minetomcat.apps.auth.view.RegisterView;
import com.github.adrien.koumgang.minetomcat.apps.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserView extends UserMeView {

    private Boolean isAdmin;

    private String status;

    private List<LoginHistoryView> loginHistory;

    public UserView() {
        super();
    }

    public UserView(User user) {
        super(user);

        this.isAdmin = user.getAdmin();
        this.status = user.getStatus();

        this.loginHistory = user.getLoginHistory() != null ? user.getLoginHistory().stream().map(LoginHistoryView::new).toList() : new ArrayList<>();
    }

    public UserView(RegisterView registerView) {
        super(registerView);
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getStatus() {
        return status;
    }

    public List<LoginHistoryView> getLoginHistory() {
        return loginHistory;
    }
}
