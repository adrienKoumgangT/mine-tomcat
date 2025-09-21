package com.github.adrien.koumgang.minetomcat.lib.service.response.post;

import com.github.adrien.koumgang.minetomcat.lib.service.response.BaseResponse;

public abstract class BasePostResponse extends BaseResponse {

    protected BasePostResponse(Builder builder) {
        super(builder);
    }

    public interface Builder extends BaseResponse.Builder {
        BasePostResponse build();
    }

    protected abstract static class BuilderImpl extends BaseResponse.BuilderImpl implements Builder {
        protected BuilderImpl() {
            super();
        }

        protected BuilderImpl(BasePostResponse model) {
            super(model);
        }
    }

}
