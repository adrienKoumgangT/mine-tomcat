package com.github.adrien.koumgang.minetomcat.lib.model;

import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.PaginationView;

import java.util.List;

public class ListItemView<T> extends BaseView {

    private List<T> items;

    private PaginationView pagination;

    public ListItemView() {}

    public ListItemView(List<T> items, PaginationView pagination) {
        this.items = items;
        this.pagination = pagination;
    }

    public List<T> getItems() {
        return items;
    }

    public ListItemView<T> setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public PaginationView getPagination() {
        return pagination;
    }

    public ListItemView<T> setPagination(PaginationView pagination) {
        this.pagination = pagination;
        return this;
    }
}
