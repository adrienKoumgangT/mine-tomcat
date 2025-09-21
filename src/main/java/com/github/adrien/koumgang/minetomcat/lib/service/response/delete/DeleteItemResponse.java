package com.github.adrien.koumgang.minetomcat.lib.service.response.delete;

import com.github.adrien.koumgang.minetomcat.lib.service.ResponseMetadata;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public final class DeleteItemResponse<T> extends BaseDeleteResponse {

    private static final Boolean DEFAULT_DELETE_STATUS = false;

    private final T item;
    private final Boolean deleted;

    private DeleteItemResponse(Builder<T> builder) {
        super(builder);
        this.item = builder.item();
        this.deleted = builder.deleted();
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public T item() {
        return this.item;
    }

    public Boolean deleted() {
        return this.deleted;
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
        return ToString.builder("DeleteItemResponse")
                .add("item", this.item())
                .add("deleted", this.deleted())
                .build();
    }

    public interface Builder<T> extends BaseDeleteResponse.Builder {
        T item();
        Builder<T> item(T item);

        Boolean deleted();
        Builder<T> deleted(Boolean deleted);

        ResponseMetadata responseMetadata();

        Builder<T> responseMetadata(ResponseMetadata responseMetadata);

        DeleteItemResponse<T> build();
    }

    static final class BuilderImpl<T> extends BaseDeleteResponse.BuilderImpl implements Builder<T> {
        private T item;
        private Boolean deleted = DEFAULT_DELETE_STATUS;

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(DeleteItemResponse<T> model) {
            super(model);
            this.item = model.item;
            this.deleted = model.deleted;
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
        public Boolean deleted() {
            return this.deleted;
        }

        @Override
        public Builder<T> deleted(Boolean deleted) {
            this.deleted = deleted != null ? deleted : DEFAULT_DELETE_STATUS;
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
        public DeleteItemResponse<T> build() {
            return new DeleteItemResponse<>(this);
        }
    }

}
