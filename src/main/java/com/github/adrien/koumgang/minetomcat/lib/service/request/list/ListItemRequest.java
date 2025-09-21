package com.github.adrien.koumgang.minetomcat.lib.service.request.list;

import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public class ListItemRequest extends BaseListRequest {

    private ListItemRequest(Builder builder) {
        super(builder);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public final String toString() {
        return ToString.builder("ListItemRequest")
                .add("ids", this.ids())
                .add("pagination", this.pagination())
                .add("asc", this.asc())
                .add("page", this.page())
                .add("pageSize", this.pageSize())
                .build();
    }

    public interface Builder extends BaseListRequest.Builder {
        UserToken userToken();
        Builder userToken(UserToken userToken);

        Boolean ids();
        Builder ids(Boolean ids);

        Boolean pagination();
        Builder pagination(Boolean pagination);

        Boolean asc();
        Builder asc(Boolean asc);

        Integer page();
        Builder page(Integer page);

        Integer pageSize();
        Builder pageSize(Integer pageSize);

        ListItemRequest build();
    }

    static final class BuilderImpl extends BaseListRequest.BuilderImpl implements Builder {
        private BuilderImpl() {
            super();
        }

        private BuilderImpl(ListItemRequest model) {
            super(model);
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
        public Boolean ids() {
            return super.ids();
        }

        @Override
        public Builder ids(Boolean ids) {
            super.ids(ids);
            return this;
        }

        @Override
        public Boolean pagination() {
            return super.pagination();
        }

        @Override
        public Builder pagination(Boolean pagination) {
            super.pagination(pagination);
            return this;
        }

        @Override
        public Boolean asc() {
            return super.asc();
        }

        @Override
        public Builder asc(Boolean asc) {
            super.asc(asc);
            return this;
        }

        @Override
        public Integer page() {
            return super.page();
        }

        @Override
        public Builder page(Integer page) {
            super.page(page);
            return this;
        }

        @Override
        public Integer pageSize() {
            return super.pageSize();
        }

        @Override
        public Builder pageSize(Integer pageSize) {
            super.pageSize(pageSize);
            return this;
        }

        @Override
        public ListItemRequest build() {
            return new ListItemRequest(this);
        }
    }

}
