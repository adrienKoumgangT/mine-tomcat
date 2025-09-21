package com.github.adrien.koumgang.minetomcat.apps.test.view;


import com.github.adrien.koumgang.minetomcat.apps.test.model.TestEmbed;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;

import java.util.Date;

public class TestEmbedView extends BaseView {

    private String name;

    private String description;

    private Date date;

    public TestEmbedView() {}

    public TestEmbedView(TestEmbed testEmbed) {
        this.name           = testEmbed.getName();
        this.description    = testEmbed.getDescription();
        this.date           = testEmbed.getDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
