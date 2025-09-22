package com.github.adrien.koumgang.minetomcat.apps.account.view;

import com.github.adrien.koumgang.minetomcat.apps.account.model.AccountType;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;

public class AccountTypeView extends BaseView {

    private String idAccountType;

    private String name;

    private String description;

    private Boolean isDefault;


    public AccountTypeView() {}


    public AccountTypeView(String idAccountType, String name, String description, Boolean isDefault) {
        this.idAccountType  = idAccountType;
        this.name           = name;
        this.description    = description;
        this.isDefault      = isDefault;
    }

    public AccountTypeView(AccountType accountType) {
        this.idAccountType = accountType.getIdAccountType();
        this.name           = accountType.getName();
        this.description    = accountType.getDescription();
    }


    public String getIdAccountType() {
        return idAccountType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }


}
