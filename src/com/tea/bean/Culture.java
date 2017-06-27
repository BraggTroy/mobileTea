package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Culture {
    private int id;
    private String name;
    private String txt;
    private String image;
    private Kinds kinds;
    private String time;

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

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Kinds getKinds() {
        return kinds;
    }

    public void setKinds(Kinds kinds) {
        this.kinds = kinds;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Culture(int id, String name, String txt, String image, Kinds kinds, String time) {
        this.id = id;
        this.name = name;
        this.txt = txt;
        this.image = image;
        this.kinds = kinds;
        this.time = time;
    }

    public Culture() {
    }
}
