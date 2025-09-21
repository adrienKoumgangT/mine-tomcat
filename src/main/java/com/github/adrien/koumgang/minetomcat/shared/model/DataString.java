package com.github.adrien.koumgang.minetomcat.shared.model;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class DataString extends BaseView {

    @Required
    private String data;

    public DataString() {}

    public DataString(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
