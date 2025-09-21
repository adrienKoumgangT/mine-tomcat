package com.github.adrien.koumgang.minetomcat.lib.service.request.put;

import com.github.adrien.koumgang.minetomcat.lib.service.request.BaseRequest;

public abstract class BasePutRequest extends BaseRequest {

    protected BasePutRequest(Builder builder) {
        super(builder);
    }

    public abstract Builder toBuilder();

    public interface Builder extends BaseRequest.Builder {
        BasePutRequest build();
    }

    protected abstract static class BuilderImpl extends BaseRequest.BuilderImpl implements Builder {
        protected  BuilderImpl() {}

        protected  BuilderImpl(BasePutRequest model) {
            super(model);
        }
    }

}
