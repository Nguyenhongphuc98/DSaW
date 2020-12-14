package com.nguyenhongphuc98.dsaw.data.model;

public class Account {
    private String id;

    private String birthday;

    private String identity;

    private String password;

    private String phonenumber;

    private String role;

    private String username;

    private String area;

    private String area_management;

    private String email;

    private String mail;

    private int code_city;

    private int code_district;

    private int code_ward;

    public Account() {}

    public Account(String birthday, String identity, String password, String phonenumber,
                   String role, String username, String area, String area_management, String email) {
        this.birthday = birthday;
        this.identity = identity;
        this.password = password;
        this.phonenumber = phonenumber;
        this.role = role;
        this.username = username;
        this.area = area;
        this.area_management = area_management;
        this.mail = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_management() {
        return area_management;
    }

    public void setArea_management(String area_management) {
        this.area_management = area_management;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getCode_city() {
        return code_city;
    }

    public void setCode_city(int code_city) {
        this.code_city = code_city;
    }

    public int getCode_district() {
        return code_district;
    }

    public void setCode_district(int code_district) {
        this.code_district = code_district;
    }

    public int getCode_ward() {
        return code_ward;
    }

    public void setCode_ward(int code_ward) {
        this.code_ward = code_ward;
    }
}
