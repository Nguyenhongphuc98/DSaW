package com.nguyenhongphuc98.dsaw.data.model;

public class Ward {
    private String name;
    private String code;

    public Ward() {};

    public Ward(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name; // What to display in the Spinner list.
    }
}
