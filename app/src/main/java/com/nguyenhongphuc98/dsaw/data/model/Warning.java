package com.nguyenhongphuc98.dsaw.data.model;

import java.util.ArrayList;
import java.util.List;

public class Warning {
    public String content;
    public String creator;
    public String title;
    public List<String> receiver;
    public int code_city;
    public int code_district;
    public int code_ward;

    public Warning() {}

    public Warning(String title, String content, String creator, List<String> receiver, int city, int district, int ward)
    {
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.receiver = receiver;
        this.code_city = city;
        this.code_district = district;
        this.code_ward = ward;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCode_city(int code_city) {
        this.code_city = code_city;
    }

    public void setCode_district(int code_district) {
        this.code_district = code_district;
    }

    public void setCode_ward(int code_ward) {
        this.code_ward = code_ward;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreator() {
        return creator;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }

    public int getCode_city() {
        return code_city;
    }

    public int getCode_district() {
        return code_district;
    }

    public int getCode_ward() {
        return code_ward;
    }
}
