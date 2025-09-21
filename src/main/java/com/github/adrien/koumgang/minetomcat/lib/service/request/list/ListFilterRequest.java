package com.github.adrien.koumgang.minetomcat.lib.service.request.list;

import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.Condition;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ListFilterRequest extends BaseListRequest {

    private final List<Condition> conditions;

    private ListFilterRequest(Builder builder) {
        super(builder);
        this.conditions = builder.conditions();
    }

    public boolean hasCondition() {
        return conditions != null && !conditions.isEmpty();
    }

    public List<Condition> conditions() {
        return this.conditions;
    }

    public ListItemRequest toListRequest() {
        return ListItemRequest.fromFilter(this);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public String toString() {
        return ToString.builder("ListFilterRequest")
                .add("ids", this.ids())
                .add("pagination", this.pagination())
                .add("asc", this.asc())
                .add("page", this.page())
                .add("pageSize", this.pageSize())
                .add("conditions", this.conditions())
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

        List<Condition> conditions();
        Builder conditions(List<Condition> conditions);
        Builder conditions(Condition... conditions);

        ListFilterRequest build();
    }

    static final class BuilderImpl extends BaseListRequest.BuilderImpl implements Builder {
        private List<Condition> conditions;

        private BuilderImpl() {
            super();
            this.conditions = Collections.emptyList();
        }

        private BuilderImpl(ListFilterRequest model) {
            super(model);
            this.conditions = model.conditions;
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
        public List<Condition> conditions() {
            return this.conditions;
        }

        @Override
        public Builder conditions(List<Condition> conditions) {
            this.conditions = conditions;
            return this;
        }

        @Override
        public Builder conditions(Condition... conditions) {
            this.conditions(Arrays.asList(conditions));
            return this;
        }

        @Override
        public ListFilterRequest build() {
            return new ListFilterRequest(this);
        }
    }



}
