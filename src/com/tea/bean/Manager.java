package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Manager {
    private int id;
    private String name;
    private String password;
    private String image;

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

    public Manager(int id, String name, String password, String image) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public Manager() {
    }
}
