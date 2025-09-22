package com.github.adrien.koumgang.minetomcat.lib.service.response.get;

import com.github.adrien.koumgang.minetomcat.lib.service.ResponseMetadata;

import java.util.Map;

public class GetResponseMetadata extends ResponseMetadata {

    private GetResponseMetadata(ResponseMetadata responseMetadata) {
        super(responseMetadata);
    }

    public static GetResponseMetadata of(ResponseMetadata responseMetadata) {
        return new GetResponseMetadata(responseMetadata);
    }

    private GetResponseMetadata(Map<String, String> metadata) {
        super(metadata);
    }

    public static GetResponseMetadata of(Map<String, String> metadata) {
        return new GetResponseMetadata(metadata);
    }

}
