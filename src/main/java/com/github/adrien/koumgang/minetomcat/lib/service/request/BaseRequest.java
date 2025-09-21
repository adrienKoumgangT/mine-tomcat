package com.github.adrien.koumgang.minetomcat.lib.service.request;

import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;

import java.util.Optional;

public abstract class BaseRequest {

    private final UserToken userToken;

    protected BaseRequest(Builder builder) {
        this.userToken = builder.userToken();
    }

    public final Optional<UserToken> userToken() {
        return Optional.ofNullable(this.userToken);
    }

    public abstract Builder toBuilder();

    public interface Builder {

        UserToken userToken();

        Builder userToken(UserToken userToken);


        BaseRequest build();
    }

    protected abstract static class BuilderImpl implements Builder {

        private UserToken userToken;

        protected BuilderImpl() {}

        protected BuilderImpl(BaseRequest request) {
            request.userToken().ifPresent(this::userToken);
        }

        @Override
        public Builder userToken(UserToken userToken) {
            this.userToken = userToken;
            return this;
        }

        @Override
        public UserToken userToken() {
            return userToken;
        }
    }

}
