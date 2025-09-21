package com.github.adrien.koumgang.minetomcat.lib.service.response.get;

import com.github.adrien.koumgang.minetomcat.lib.service.response.BaseResponse;

public abstract class BaseGetResponse extends BaseResponse {

    protected BaseGetResponse(Builder builder) {
        super(builder);
    }

    public interface Builder extends BaseResponse.Builder {
        BaseGetResponse build();
    }

    protected abstract static class BuilderImpl extends BaseResponse.BuilderImpl implements Builder {
        protected BuilderImpl() {
            super();
        }

        protected BuilderImpl(BaseGetResponse model) {
            super(model);
        }
    }

}
