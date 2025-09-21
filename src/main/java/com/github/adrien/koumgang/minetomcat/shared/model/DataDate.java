package com.github.adrien.koumgang.minetomcat.shared.model;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

import java.util.Date;

public class DataDate extends BaseView {

    @Required
    private Date data;

    public DataDate() {}

    public DataDate(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
