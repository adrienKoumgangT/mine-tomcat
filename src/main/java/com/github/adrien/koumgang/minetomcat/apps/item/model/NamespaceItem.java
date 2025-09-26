package com.github.adrien.koumgang.minetomcat.apps.item.model;

import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;

public class NamespaceItem {

    @ModelField("db")
    private String db;

    @ModelField("collection")
    private String collection;

}
