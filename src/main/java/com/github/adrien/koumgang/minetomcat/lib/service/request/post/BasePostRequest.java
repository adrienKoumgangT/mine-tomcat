package com.github.adrien.koumgang.minetomcat.lib.service.request.post;

import com.github.adrien.koumgang.minetomcat.lib.service.request.BaseRequest;

public abstract class BasePostRequest extends BaseRequest {

    protected BasePostRequest(Builder builder) {
        super(builder);
    }

    public abstract Builder toBuilder();

    public interface Builder extends BaseRequest.Builder {
        BasePostRequest build();
    }

    protected abstract static class BuilderImpl extends BaseRequest.BuilderImpl implements Builder {
        protected BuilderImpl() {}

        protected BuilderImpl(BasePostRequest model) {
            super(model);
        }
    }

}
