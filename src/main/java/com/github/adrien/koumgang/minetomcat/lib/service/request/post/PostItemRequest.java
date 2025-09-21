package com.github.adrien.koumgang.minetomcat.lib.service.request.post;

import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public final class PostItemRequest<T> extends BasePostRequest {

    private final T item;

    private PostItemRequest(Builder<T> builder) {
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

    @Override
    public String toString() {
        return ToString.builder("PostItemRequest")
                .add("item", this.item())
                .build();
    }

    public interface Builder<T> extends BasePostRequest.Builder {
        UserToken userToken();
        Builder<T> userToken(UserToken userToken);

        T item();
        Builder<T> item(T item);

        PostItemRequest<T> build();
    }

    static final class BuilderImpl<T> extends BasePostRequest.BuilderImpl implements Builder<T> {
        private T item;

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(PostItemRequest<T> model) {
            super(model);
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
        public T item() {
            return this.item;
        }

        @Override
        public Builder<T> item(T item) {
            this.item = item;
            return this;
        }

        @Override
        public PostItemRequest<T> build() {
            return new PostItemRequest<>(this);
        }
    }

}
