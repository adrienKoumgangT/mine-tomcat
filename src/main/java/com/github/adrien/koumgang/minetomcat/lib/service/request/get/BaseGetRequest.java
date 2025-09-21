package com.github.adrien.koumgang.minetomcat.lib.service.request.get;

import com.github.adrien.koumgang.minetomcat.lib.service.request.BaseRequest;

public abstract class BaseGetRequest extends BaseRequest {

    protected BaseGetRequest(Builder builder) {
        super(builder);
    }

    public abstract Builder toBuilder();

    public interface Builder extends BaseRequest.Builder {
        BaseGetRequest build();
    }

    protected abstract static class BuilderImpl extends BaseRequest.BuilderImpl implements Builder {
        protected BuilderImpl() {}

        protected BuilderImpl(BaseGetRequest model) {
            super(model);
        }
    }

}
