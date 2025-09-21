package com.github.adrien.koumgang.minetomcat.lib.service.response;

import com.github.adrien.koumgang.minetomcat.lib.service.ResponseMetadata;

public abstract class BaseResponse {

    private final ResponseMetadata responseMetadata;

    protected BaseResponse(Builder builder) {
        this.responseMetadata = builder.responseMetadata();
    }

    public ResponseMetadata responseMetadata() {
        return this.responseMetadata;
    }

    public abstract Builder toBuilder();

    public interface Builder {
        ResponseMetadata responseMetadata();

        Builder responseMetadata(ResponseMetadata responseMetadata);

        BaseResponse build();
    }

    protected abstract static class BuilderImpl implements Builder {
        private ResponseMetadata responseMetadata;

        protected BuilderImpl() {}

        protected BuilderImpl(BaseResponse response) {
            this.responseMetadata = response.responseMetadata();
        }

        @Override
        public Builder responseMetadata(ResponseMetadata responseMetadata) {
            this.responseMetadata = responseMetadata;
            return this;
        }

        @Override
        public ResponseMetadata responseMetadata() {
            return responseMetadata;
        }
    }

}
