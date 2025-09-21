package com.github.adrien.koumgang.minetomcat.lib.service.response.put;

import com.github.adrien.koumgang.minetomcat.lib.service.ResponseMetadata;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public class PutItemResponse<T> extends BasePutResponse {
    private static final Boolean DEFAULT_UPDATE_STATUS = false;

    private final T item;
    private final Boolean updated;

    private PutItemResponse(Builder<T> builder) {
        super(builder);
        this.item = builder.item();
        this.updated = builder.updated();
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public T item() {
        return this.item;
    }

    public Boolean updated() {
        return this.updated;
    }

    @Override
    public Builder<T> toBuilder() {
        return new BuilderImpl<T>(this);
    }

    public static <T> Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public String toString() {
        return ToString.builder("PutItemResponse")
                .add("item", this.item())
                .build();
    }

    public interface Builder<T> extends BasePutResponse.Builder {
        T item();
        Builder<T> item(T item);

        Boolean updated();
        Builder<T> updated(Boolean updated);

        ResponseMetadata responseMetadata();

        Builder<T> responseMetadata(ResponseMetadata responseMetadata);

        PutItemResponse<T> build();
    }

    static final class BuilderImpl<T> extends BasePutResponse.BuilderImpl implements Builder<T> {
        private T item;
        private Boolean updated = DEFAULT_UPDATE_STATUS;

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(PutItemResponse<T> model) {
            super(model);
            this.updated = model.updated;
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
        public Boolean updated() {
            return this.updated;
        }

        @Override
        public Builder<T> updated(Boolean updated) {
            this.updated = updated != null ? updated : DEFAULT_UPDATE_STATUS;
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
        public PutItemResponse<T> build() {
            return new PutItemResponse<>(this);
        }
    }

}
