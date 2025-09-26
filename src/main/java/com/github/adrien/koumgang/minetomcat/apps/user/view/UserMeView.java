package com.github.adrien.koumgang.minetomcat.apps.user.view;


import com.github.adrien.koumgang.minetomcat.apps.auth.view.RegisterView;
import com.github.adrien.koumgang.minetomcat.apps.user.model.User;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;
import com.github.adrien.koumgang.minetomcat.shared.view.AddressView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserMeView extends UserExternView {

    @Required
    private String email;

    private Date lastLoginAt;

    private List<AddressView> addresses;

    public UserMeView() {
        super();
    }

    public UserMeView(User user) {
        super(user);

        this.email = user.getEmail() != null ? user.getEmail().getEmail() : null;
        this.lastLoginAt = user.getLastLoginAt();
        this.addresses = user.getAddresses() != null ? user.getAddresses().stream().map(AddressView::new).toList() : new ArrayList<>();
    }

    public UserMeView(RegisterView registerView) {
        super(registerView);
        this.email = registerView.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public List<AddressView> getAddresses() {
        return addresses;
    }
}
