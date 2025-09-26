package com.github.adrien.koumgang.minetomcat.apps.user.view;

import com.github.adrien.koumgang.minetomcat.apps.auth.view.RegisterView;
import com.github.adrien.koumgang.minetomcat.apps.user.model.User;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class UserExternView extends BaseView {

    private String idUser;

    @Required
    private String firstName;

    @Required
    private String lastName;

    private String username;

    private String image;

    private String wallImage;

    public UserExternView() {}

    public UserExternView(User user) {
        super(user);

        this.idUser = StringIdConverter.getInstance().fromObjectId(user.getUserId());
        this.firstName  = user.getFirstName();
        this.lastName   = user.getLastName();
        this.username   = user.getUsername();
        this.image      = user.getImage();
        this.wallImage  = user.getWallImage();
    }

    public UserExternView(RegisterView registerView) {
        this.username = registerView.getUsername();
        this.firstName = registerView.getFirstName();
        this.lastName = registerView.getLastName();
    }


    public String getIdUser() {
        return idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public String getWallImage() {
        return wallImage;
    }
}
