package com.github.adrien.koumgang.minetomcat.lib.service.response.post;

import com.github.adrien.koumgang.minetomcat.lib.service.ResponseMetadata;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public final class PostItemResponse<T> extends BasePostResponse {
    private static final Boolean DEFAULT_CREATE_STATUS = false;

    private final T item;
    private final Boolean created;

    private PostItemResponse(Builder<T> builder) {
        super(builder);
        this.item = builder.item();
        this.created = builder.created();
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public T item() {
        return this.item;
    }

    public Boolean created() {
        return this.created;
    }

    @Override
    public Builder<T> toBuilder() {
        return new BuilderImpl<>(this);
    }

    public static <T> Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public String toString() {
        return ToString.builder("PostItemResponse")
                .add("item", this.item())
                .build();
    }

    public interface Builder<T> extends BasePostResponse.Builder {
        T item();
        Builder<T> item(T item);

        Boolean created();
        Builder<T> created(Boolean created);

        ResponseMetadata responseMetadata();

        Builder<T> responseMetadata(ResponseMetadata responseMetadata);

        PostItemResponse<T> build();
    }

    static final class BuilderImpl<T> extends BasePostResponse.BuilderImpl implements Builder<T> {
        private T item;
        private Boolean created = DEFAULT_CREATE_STATUS;

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(PostItemResponse<T> model) {
            super(model);
            this.item = model.item;
            this.created = model.created;
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
        public Boolean created() {
            return this.created;
        }

        @Override
        public Builder<T> created(Boolean created) {
            this.created = created != null ? created : DEFAULT_CREATE_STATUS;
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
        public PostItemResponse<T> build() {
            return new PostItemResponse<>(this);
        }
    }

}
