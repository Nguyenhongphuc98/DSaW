package com.nguyenhongphuc98.dsaw.data.model;

import java.util.ArrayList;
import java.util.List;

public class District {
    private String name;
    private String code;

    public District() {}

    public District(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.name; // What to display in the Spinner list.
    }
}
