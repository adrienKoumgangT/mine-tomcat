package com.github.adrien.koumgang.minetomcat.lib.service.response.put;

import com.github.adrien.koumgang.minetomcat.lib.service.response.BaseResponse;

public abstract class BasePutResponse extends BaseResponse {

    protected BasePutResponse(Builder builder) {
        super(builder);
    }

    public interface Builder extends BaseResponse.Builder {
        BasePutResponse build();
    }

    protected abstract static class BuilderImpl extends BaseResponse.BuilderImpl implements Builder {
        protected BuilderImpl() {
            super();
        }

        protected BuilderImpl(BasePutResponse model) {
            super(model);
        }
    }

}
