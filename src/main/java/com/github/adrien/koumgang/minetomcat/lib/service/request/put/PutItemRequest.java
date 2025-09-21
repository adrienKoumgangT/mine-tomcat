package com.github.adrien.koumgang.minetomcat.lib.service.request.put;

import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public final class PutItemRequest<T> extends BasePutRequest {

    private final String id;
    private final T item;

    private PutItemRequest(Builder<T> builder) {
        super(builder);
        this.id = builder.id();
        this.item = builder.item();
    }

    public String id() {
        return this.id;
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

    @Override
    public String toString() {
        return ToString.builder("PutItemRequest")
                .add("id", this.id())
                .add("item", this.item())
                .build();
    }

    public interface Builder<T> extends BasePutRequest.Builder {
        UserToken userToken();
        Builder<T> userToken(UserToken userToken);

        String id();
        Builder<T> id(String id);

        T item();
        Builder<T> item(T item);

        PutItemRequest<T> build();
    }

    static final class BuilderImpl<T> extends BasePutRequest.BuilderImpl implements Builder<T> {
        private String id;
        private T item;

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(PutItemRequest<T> model) {
            super(model);
            this.id = model.id;
            this.item = model.item;
        }

        @Override
        public Builder<T> userToken(UserToken userToken) {
            super.userToken(userToken);
            return this;
        }

        @Override
        public UserToken userToken() {
            return super.userToken();
        }

        @Override
        public String id() {
            return this.id;
        }

        @Override
        public Builder<T> id(String id) {
            this.id = id;
            return this;
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
        public PutItemRequest<T> build() {
            return new PutItemRequest<>(this);
        }
    }

}
