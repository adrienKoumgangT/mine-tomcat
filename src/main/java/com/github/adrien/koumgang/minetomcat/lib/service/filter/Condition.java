package com.github.adrien.koumgang.minetomcat.lib.service.filter;

import com.github.adrien.koumgang.minetomcat.lib.service.FieldValue;
import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Condition {

    private final FieldValue fieldValue;
    private final ComparisonOperator comparisonOperator;
    private final List<Condition> conditions;

    private Condition(Builder builder) {
        this.fieldValue = builder.fieldValue();
        this.comparisonOperator = builder.comparisonOperator();
        this.conditions = builder.conditions();
    }

    public FieldValue fieldValue() {
        return this.fieldValue;
    }

    public ComparisonOperator comparisonOperator() {
        return this.comparisonOperator;
    }

    public boolean hasCondition() {
        return conditions != null && !conditions.isEmpty();
    }

    public List<Condition> conditions() {
        return this.conditions;
    }

    @Override
    public String toString() {
        return ToString.builder("Condition")
                .add("fieldValue", this.fieldValue())
                .add("comparisonOperator", this.comparisonOperator())
                .add("conditions", this.conditions())
                .build();
    }

    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public interface Builder {

        FieldValue fieldValue();
        Builder fieldValue(FieldValue fieldValue);

        ComparisonOperator comparisonOperator();
        Builder comparisonOperator(ComparisonOperator comparisonOperator);

        List<Condition> conditions();
        Builder conditions(List<Condition> conditions);
        Builder conditions(Condition... conditions);

        Condition build();
    }

    static final class BuilderImpl implements Builder {
        private FieldValue fieldValue;
        private ComparisonOperator comparisonOperator;
        private List<Condition> conditions;

        private BuilderImpl() {
            this.conditions = Collections.emptyList();
        }

        private BuilderImpl(Condition model) {
            this.fieldValue = model.fieldValue;
            this.comparisonOperator = model.comparisonOperator;
            this.conditions = model.conditions;
        }

        @Override
        public FieldValue fieldValue() {
            return this.fieldValue;
        }

        @Override
        public Builder fieldValue(FieldValue fieldValue) {
            this.fieldValue = fieldValue;
            return this;
        }

        @Override
        public ComparisonOperator comparisonOperator() {
            return this.comparisonOperator;
        }

        @Override
        public Builder comparisonOperator(ComparisonOperator comparisonOperator) {
            this.comparisonOperator = comparisonOperator;
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
        public Condition build() {
            return new Condition(this);
        }
    }


}
