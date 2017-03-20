package com.example.dfrie.nytexplore.enums;

/**
 * Created by dfrie on 3/19/2017.
 */

public enum SortOrderEnum {

    RELEVANCE(0, "Relevance"),
    DATE_ASC (1, "Publish Date Ascending"),
    DATE_DESC(2, "Publish Date Descending");

    private int value;
    private String name;

    private SortOrderEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static SortOrderEnum getEnumFor(int code) {
        switch (code) {
            case 1:
                return DATE_ASC;
            case 2:
                return DATE_DESC;
        }
        return RELEVANCE;
    }

    public static SortOrderEnum getEnumFor(String str) {
        if (str.toUpperCase().equals(DATE_ASC.getName().toUpperCase())) {
            return DATE_ASC;
        }
        if (str.toUpperCase().equals(DATE_DESC.getName().toUpperCase())) {
            return DATE_DESC;
        }
        return RELEVANCE;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
