package com.github.adrien.koumgang.minetomcat.apps.account.model;

import com.github.adrien.koumgang.minetomcat.apps.account.view.AccountView;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoCollectionName;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoEmbedded;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoId;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.model.BaseModel;
import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;
import org.bson.types.ObjectId;

@MongoCollectionName("accounts")
public class Account extends BaseModel {

    @MongoId
    private ObjectId accountId;

    @ModelField("user_id")
    private ObjectId userId;

    @ModelField("name")
    private String name;

    @MongoEmbedded("account_type")
    private AccountType accountType;

    @ModelField("currency")
    private String currency;

    @ModelField("starting_balance")
    private Double startingBalance;

    @ModelField("is_archived")
    private Boolean isArchived;


    public Account() {}


    public Account(AccountView account) {
        this.accountId = StringIdConverter.getInstance().toObjectId(account.getIdAccount());

        this.name       = account.getName();
        this.currency   = account.getCurrency();

        if(account.getAccountType()!=null){
            this.accountType = new AccountType(account.getAccountType());
        }

        update(account);
    }

    public void update(AccountView account) {
        this.startingBalance = account.getStartingBalance();
    }


    public ObjectId getAccountId() {
        return accountId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getStartingBalance() {
        return startingBalance;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean archived) {
        isArchived = archived;
    }
}
