package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Users {
    private int id;
    private String name;
    private String sex;
    private String password;
    private String image;
    private String email;
    private String phoneNumber;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Users(int id, String name, String sex, String password, String image,
                 String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.password = password;
        this.image = image;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Users() {
    }
}
