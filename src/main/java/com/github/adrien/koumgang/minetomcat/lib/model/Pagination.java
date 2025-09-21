package com.github.adrien.koumgang.minetomcat.lib.model;


public final class Pagination {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 100;
    private static final Long DEFAULT_TOTAL = 0L;

    private final Integer page;
    private final Integer pageSize;
    private final Long total;

    private Pagination(PaginationBuilder builder) {
        this.page = builder.page();
        this.pageSize = builder.pageSize();
        this.total = builder.total();
    }

    public Integer page() {
        return page;
    }

    public Integer pageSize() {
        return pageSize;
    }

    public Long total() {
        return total;
    }

    public static PaginationBuilder builder() {
        return new PaginationBuilderImpl();
    }

    public interface PaginationBuilder {
        Integer page();
        PaginationBuilder page(Integer page);

        Integer pageSize();
        PaginationBuilder pageSize(Integer pageSize);

        Long total();
        PaginationBuilder total(Long total);

        Pagination build();
    }

    static final class PaginationBuilderImpl implements PaginationBuilder {
        private Integer page = DEFAULT_PAGE;
        private Integer pageSize = DEFAULT_PAGE_SIZE;
        private Long total = DEFAULT_TOTAL;


        @Override
        public Integer page() {
            return this.page;
        }

        @Override
        public PaginationBuilder page(Integer page) {
            this.page = page != null ? page : DEFAULT_PAGE;
            return this;
        }

        @Override
        public Integer pageSize() {
            return this.pageSize;
        }

        @Override
        public PaginationBuilder pageSize(Integer pageSize) {
            this.pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
            return this;
        }

        @Override
        public Long total() {
            return total;
        }

        @Override
        public PaginationBuilder total(Long total) {
            this.total = total != null ? total : DEFAULT_TOTAL;
            return this;
        }

        @Override
        public Pagination build() {
            return new Pagination(this);
        }
    }

}
