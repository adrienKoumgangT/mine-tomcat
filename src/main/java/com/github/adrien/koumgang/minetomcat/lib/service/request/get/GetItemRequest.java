package com.github.adrien.koumgang.minetomcat.lib.service.request.get;

import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public final class GetItemRequest extends BaseGetRequest {

    private final String id;

    private GetItemRequest(Builder builder) {
        super(builder);
        this.id = builder.id();
    }

    public String id() {
        return id;
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public String toString() {
        return ToString.builder("GetItemRequest")
                .add("id", this.id())
                .build();
    }

    public interface Builder extends BaseGetRequest.Builder {
        UserToken userToken();
        Builder userToken(UserToken userToken);

        String id();
        Builder id(String id);

        GetItemRequest build();
    }

    static final class BuilderImpl extends BaseGetRequest.BuilderImpl implements Builder {

        private String id;

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(GetItemRequest model) {
            super(model);
            this.id = model.id;
        }

        @Override
        public Builder userToken(UserToken userToken) {
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
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        @Override
        public GetItemRequest build() {
            return new GetItemRequest(this);
        }
    }

}
