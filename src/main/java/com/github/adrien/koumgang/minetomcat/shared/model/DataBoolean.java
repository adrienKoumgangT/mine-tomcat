package com.github.adrien.koumgang.minetomcat.shared.model;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class DataBoolean extends BaseView {

    @Required
    private Boolean data;

    public DataBoolean() {}

    public DataBoolean(Boolean data) {
        this.data = data;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }
}
