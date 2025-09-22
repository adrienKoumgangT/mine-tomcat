package com.github.adrien.koumgang.minetomcat.apps.account.model;

import com.github.adrien.koumgang.minetomcat.apps.account.view.AccountTypeView;
import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;

public class AccountType {

    @ModelField("account_type_id")
    private String idAccountType;

    @ModelField("name")
    private String name;

    @ModelField("description")
    private String description;

    public AccountType() {}

    public AccountType(AccountTypeView accountType) {
        this.idAccountType  = accountType.getIdAccountType();
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
}
