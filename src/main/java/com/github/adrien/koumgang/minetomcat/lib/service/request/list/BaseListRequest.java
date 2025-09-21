package com.github.adrien.koumgang.minetomcat.lib.service.request.list;

import com.github.adrien.koumgang.minetomcat.lib.service.request.BaseRequest;

public abstract class BaseListRequest extends BaseRequest {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 100;

    private final Boolean ids;
    private final Boolean pagination;
    private final Boolean asc;
    private final Integer page;
    private final Integer pageSize;

    protected BaseListRequest(Builder builder) {
        super(builder);
        this.ids = builder.ids();
        this.pagination = builder.pagination();
        this.asc = builder.asc();
        this.page = builder.page();
        this.pageSize = builder.pageSize();
    }

    public final Boolean ids() {
        return this.ids;
    }

    public final Boolean pagination() {
        return this.pagination;
    }

    public final Boolean asc() {
        return this.asc;
    }

    public final Integer page() {
        return this.page;
    }

    public final Integer pageSize() {
        return this.pageSize;
    }

    public abstract Builder toBuilder();

    public interface Builder extends BaseRequest.Builder {

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

        BaseListRequest build();
    }

    protected abstract static class BuilderImpl extends BaseRequest.BuilderImpl implements Builder {

        private Boolean ids = false;
        private Boolean pagination = false;
        private Boolean asc = false;
        private Integer page = DEFAULT_PAGE;
        private Integer pageSize = DEFAULT_PAGE_SIZE;

        protected BuilderImpl() {}

        protected BuilderImpl(BaseListRequest request) {
            super(request);
        }

        @Override
        public Boolean ids() {
            return this.ids;
        }

        @Override
        public Builder ids(Boolean ids) {
            this.ids = ids != null ? ids : false;
            return this;
        }

        @Override
        public Boolean pagination() {
            return this.pagination;
        }

        @Override
        public Builder pagination(Boolean pagination) {
            this.pagination = pagination != null ? pagination : false;
            return this;
        }

        @Override
        public Boolean asc() {
            return this.asc;
        }

        @Override
        public Builder asc(Boolean asc) {
            this.asc = asc != null ? asc : false;
            return this;
        }

        @Override
        public Integer page() {
            return this.page;
        }

        @Override
        public Builder page(Integer page) {
            this.page = page != null ? page : DEFAULT_PAGE;
            return this;
        }

        @Override
        public Integer pageSize() {
            return this.pageSize;
        }

        @Override
        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
            return this;
        }

    }

}
