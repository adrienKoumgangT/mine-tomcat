package com.github.adrien.koumgang.minetomcat.apps.item.model;

import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;

import java.util.List;

public class MetadataItem {

    @ModelField("version")
    private Integer version;

    @ModelField("schema_version")
    private String schemaVersion;

    @ModelField("tags")
    private List<String> tags;

    @ModelField("source")
    private String source;

    @ModelField("requestId")
    private String requestId;


}
