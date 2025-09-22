package com.github.adrien.koumgang.minetomcat.apps.metadata.service;

public enum MetadataFilter {

    METADATA_TYPE("metadataType"),
    NAME("name"),
    ;

    private final String value;

    MetadataFilter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MetadataFilter getByValue(String value){
        for (MetadataFilter filter : MetadataFilter.values()) {
            if (filter.value.equals(value)) {
                return filter;
            }
        }
        return null;
    }
}
