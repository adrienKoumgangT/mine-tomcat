package com.github.adrien.koumgang.minetomcat.apps.account.view;

import com.github.adrien.koumgang.minetomcat.apps.account.model.Account;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;

public class AccountView extends BaseView {

    private String idAccount;

    private String name;

    private AccountTypeView accountType;

    private String currency;

    private Double startingBalance;

    public AccountView() {
        super();
    }

    public AccountView(Account account) {
        super(account);

        this.idAccount = StringIdConverter.getInstance().fromObjectId(account.getAccountId());

        this.name = account.getName();
        this.currency = account.getCurrency();
        this.startingBalance = account.getStartingBalance();

        if(account.getAccountType() != null) {
            this.accountType = new AccountTypeView(account.getAccountType());
        }
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getName() {
        return name;
    }

    public AccountTypeView getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeView accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getStartingBalance() {
        return startingBalance;
    }
}
