package com.github.adrien.koumgang.minetomcat.lib.service.filter;

public enum ComparisonOperator {

    // Query / Comparison Operators : These are used compare field values in queries
    /**Matches values equal to a specified value*/
    EQ("eq"),
    /**Matches all values not equal to a specified value*/
    NE("ne"),
    /**Greater than*/
    GT("gt"),
    /**Greater than or equal*/
    GTE("gte"),
    /**Less than*/
    LT("lt"),
    /**Less than or equal*/
    LTE("lte"),
    /**Matches any of the values specified in an array*/
    IN("in"),
    /**Matches none of the values specified in an array*/
    NIN("nin"),
    /***/
    BETWEEN("between"),

    // Logical Operators : Used to combine multiple conditions
    /**Joins query clauses with a logical AND*/
    AND("and"),
    /**Joins query clauses with a logical OR*/
    OR("or"),
    /**Joins query clauses with a logical NOR (not OR)*/
    NOR("nor"),
    /**Inverts the effect of a query expression*/
    NOT("not"),

    // Element Operators : For testing presence/size of fields
    /**Matches if a field exists*/
    EXISTS("exists"),
    /**matches if a field is of a given type*/
    TYPE("type"),

    //
    ISNULL("isnull"),

    // Array Operators : conditional on array contents
    /**Matches arrays that contain all elements specified*/
    ALL("all"),
    /**Matches documents with array elements matching all criteria*/
    ELEM_MATCH("elemMatch"),
    /**Matches arrays with a specified number of elements*/
    SIZE("size"),

    ;

    private final String value;
    ComparisonOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ComparisonOperator fromValue(String value) {
        for (ComparisonOperator operator : ComparisonOperator.values()) {
            if (operator.getValue().equals(value)) {
                return operator;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
