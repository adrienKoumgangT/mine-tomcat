package com.github.adrien.koumgang.minetomcat.lib.service.request.delete;

import com.github.adrien.koumgang.minetomcat.lib.service.request.BaseRequest;

public abstract class BaseDeleteRequest extends BaseRequest {

    protected BaseDeleteRequest(Builder builder) {
        super(builder);
    }

    public abstract Builder toBuilder();

    public interface Builder extends BaseRequest.Builder {
        BaseDeleteRequest build();
    }

    protected abstract static class BuilderImpl extends BaseRequest.BuilderImpl implements Builder {
        protected BuilderImpl() {}

        protected BuilderImpl(BaseDeleteRequest model) {
            super(model);
        }
    }

}
