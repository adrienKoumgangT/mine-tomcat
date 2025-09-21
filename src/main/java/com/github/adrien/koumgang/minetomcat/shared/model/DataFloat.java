package com.github.adrien.koumgang.minetomcat.shared.model;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class DataFloat extends BaseView {

    @Required
    private Float data;

    public DataFloat() {}

    public DataFloat(Float data) {
        this.data = data;
    }

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }
}
