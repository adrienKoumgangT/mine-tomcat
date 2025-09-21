package com.github.adrien.koumgang.minetomcat.lib.service;

import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

public final class FieldValue {

    private final String name;
    private final Value value;

    private FieldValue(Builder builder) {
        this.name = builder.name();
        this.value = builder.value();
    }

    public String name() {
        return this.name;
    }
    public Value value() {
        return this.value;
    }

    public String toString() {
        return ToString.builder("FieldValue")
                .add("name", this.name())
                .add("value", this.value())
                .build();
    }

    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public interface Builder {
        String name();
        Builder name(String name);

        Value value();
        Builder value(Value value);

        FieldValue build();
    }

    static final class BuilderImpl implements Builder {
        private String name;
        private Value value;

        private BuilderImpl() {}

        private BuilderImpl(FieldValue model) {
            this.name = model.name;
            this.value = model.value;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Value value() {
            return this.value;
        }

        @Override
        public Builder value(Value value) {
            this.value = value;
            return this;
        }

        @Override
        public FieldValue build() {
            return new FieldValue(this);
        }
    }

}
