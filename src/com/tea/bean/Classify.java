package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Classify {
    private int id;
    private String name;

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

    public Classify(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Classify() {
    }
}
