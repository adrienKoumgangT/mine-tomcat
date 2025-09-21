package com.github.adrien.koumgang.minetomcat.lib.service.response.get;

import com.github.adrien.koumgang.minetomcat.lib.service.ResponseMetadata;

public final class GetItemResponse<T> extends BaseGetResponse {

    private final T item;

    private GetItemResponse(Builder<T> builder) {
        super(builder);
        this.item = builder.item();
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public T item() {
        return this.item;
    }

    @Override
    public Builder<T> toBuilder() {
        return new BuilderImpl<>(this);
    }

    public static <T> Builder<T> builder() {
        return new BuilderImpl<>();
    }

    public interface Builder<T> extends BaseGetResponse.Builder {
        T item();
        Builder<T> item(T item);

        ResponseMetadata responseMetadata();

        Builder<T> responseMetadata(ResponseMetadata responseMetadata);

        GetItemResponse<T> build();
    }

    static final class BuilderImpl<T> extends BaseGetResponse.BuilderImpl implements Builder<T> {
        private T item;

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(GetItemResponse<T> model) {
            super(model);
            this.item = model.item;
        }

        @Override
        public T item() {
            return this.item;
        }

        @Override
        public Builder<T> item(T item) {
            this.item = item;
            return this;
        }

        @Override
        public Builder<T> responseMetadata(ResponseMetadata responseMetadata) {
            super.responseMetadata(responseMetadata);
            return this;
        }

        @Override
        public ResponseMetadata responseMetadata() {
            return super.responseMetadata();
        }

        @Override
        public GetItemResponse<T> build() {
            return new GetItemResponse<>(this);
        }
    }

}