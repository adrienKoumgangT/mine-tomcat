package com.github.adrien.koumgang.minetomcat.lib.service.response.list;

import com.github.adrien.koumgang.minetomcat.lib.model.Pagination;
import com.github.adrien.koumgang.minetomcat.lib.service.response.BaseResponse;

public abstract class BaseListResponse extends BaseResponse {
    private static final Boolean DEFAULT_PAGINATE_STATUS = false;

    private final Pagination pagination;
    private final Boolean paginated;


    protected BaseListResponse(Builder builder) {
        super(builder);
        this.pagination = builder.pagination();
        this.paginated = builder.paginated();
    }

    public final Pagination pagination() {
        return this.pagination;
    }

    public interface Builder extends BaseResponse.Builder {
        Pagination pagination();
        Builder pagination(Pagination pagination);

        Boolean paginated();
        Builder paginated(Boolean paginated);

        BaseListResponse build();
    }

    protected abstract static class BuilderImpl extends BaseResponse.BuilderImpl implements Builder {
        private Pagination pagination;
        private Boolean paginated = DEFAULT_PAGINATE_STATUS;

        protected BuilderImpl() {
            super();
        }

        protected BuilderImpl(BaseListResponse model) {
            super(model);
            this.pagination = model.pagination;
            this.paginated = model.paginated;
        }

        @Override
        public Pagination pagination() {
            return this.pagination;
        }

        @Override
        public Builder pagination(Pagination pagination) {
            this.pagination = pagination;
            return this;
        }

        @Override
        public Boolean paginated() {
            return this.paginated;
        }

        @Override
        public Builder paginated(Boolean paginated) {
            this.paginated = paginated != null ? paginated : DEFAULT_PAGINATE_STATUS;
            return this;
        }
    }

}
