package com.github.adrien.koumgang.minetomcat.lib.service.response.delete;

import com.github.adrien.koumgang.minetomcat.lib.service.response.BaseResponse;

public abstract class BaseDeleteResponse extends BaseResponse {

    protected BaseDeleteResponse(Builder builder) {
        super(builder);
    }

    public interface Builder extends BaseResponse.Builder {

        BaseDeleteResponse build();
    }

    protected abstract static class BuilderImpl extends BaseResponse.BuilderImpl implements Builder {
        public BuilderImpl() {
            super();
        }

        public BuilderImpl(BaseDeleteResponse model) {
            super(model);
        }
    }
}
