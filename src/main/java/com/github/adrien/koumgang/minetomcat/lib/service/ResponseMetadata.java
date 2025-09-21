package com.github.adrien.koumgang.minetomcat.lib.service;

import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public abstract class ResponseMetadata {

    private static final String UNKNOWN = "UNKNOWN";
    private final Map<String, String> metadata;

    protected ResponseMetadata(Map<String, String> metadata) {
        if(metadata == null) this.metadata = Collections.emptyMap();
        else this.metadata = Collections.unmodifiableMap(metadata);
    }

    protected ResponseMetadata(ResponseMetadata response) {
        this(response.metadata);
    }

    public final String toString() {
        return ToString.builder("ResponseMetadata")
                .add("metadata", this.metadata.keySet())
                .build();
    }


    protected final String getValue(String key) {
        return Optional.ofNullable(this.metadata.get(key)).orElse(UNKNOWN);
    }

}
