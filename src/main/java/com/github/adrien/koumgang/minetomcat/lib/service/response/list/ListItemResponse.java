package com.github.adrien.koumgang.minetomcat.lib.service.response.list;

import com.github.adrien.koumgang.minetomcat.lib.model.Pagination;
import com.github.adrien.koumgang.minetomcat.lib.service.ResponseMetadata;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

import java.util.ArrayList;
import java.util.List;

public final class ListItemResponse<T> extends BaseListResponse {

    private final List<String> ids;
    private final List<T> items;

    private ListItemResponse(Builder<T> builder) {
        super(builder);
        this.ids = builder.ids();
        this.items = builder.items();
    }

    public List<String> ids() {
        return this.ids;
    }
    public List<T> items() {
        return this.items;
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
        return ToString.builder("ListItemResponse")
                .add("ids", this.ids())
                .add("items", this.items())
                .build();
    }

    public interface Builder<T> extends BaseListResponse.Builder {
        List<String> ids();
        Builder<T> ids(List<String> ids);

        List<T> items();
        Builder<T> items(List<T> items);

        Builder<T> pagination(Pagination pagination);
        Builder<T> paginated(Boolean paginated);

        ListItemResponse<T> build();
    }

    static final class BuilderImpl<T> extends BaseListResponse.BuilderImpl implements Builder<T> {
        private List<String> ids = new ArrayList<>();
        private List<T> items  = new ArrayList<>();

        private BuilderImpl() {
            super();
        }

        private BuilderImpl(ListItemResponse<T> model) {
            super(model);
            this.ids = model.ids;
            this.items = model.items;
        }

        @Override
        public List<String> ids() {
            return this.ids;
        }

        @Override
        public Builder<T> ids(List<String> ids) {
            this.ids = ids != null ? ids : new ArrayList<>();
            return this;
        }

        @Override
        public List<T> items() {
            return this.items;
        }

        @Override
        public Builder<T> items(List<T> items) {
            this.items = items  != null ? items : new ArrayList<>();
            return this;
        }

        @Override
        public Builder<T> pagination(Pagination pagination) {
            super.pagination(pagination);
            return this;
        }

        @Override
        public Builder<T> paginated(Boolean paginated) {
            super.paginated(paginated);
            return this;
        }

        @Override
        public Builder<T> responseMetadata(ResponseMetadata rm) {
            super.responseMetadata(rm);
            return this;
        }

        @Override
        public ListItemResponse<T> build() {
            return new ListItemResponse<>(this);
        }
    }

}
