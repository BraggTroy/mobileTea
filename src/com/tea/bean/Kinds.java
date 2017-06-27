package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Kinds {
    private int id;
    private String name;
    private Classify classify;
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

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Kinds(int id, String name, Classify classify, String image) {
        this.id = id;
        this.name = name;
        this.classify = classify;
        this.image = image;
    }

    public Kinds() {
    }
}
