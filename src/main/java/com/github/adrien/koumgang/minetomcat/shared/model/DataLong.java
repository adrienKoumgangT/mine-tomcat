package com.github.adrien.koumgang.minetomcat.shared.model;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

public class DataLong extends BaseView {

    @Required
    private Long data;

    public DataLong() {}

    public DataLong(Long data) {
        this.data = data;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
