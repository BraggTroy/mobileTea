package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class AdImages {
    private int id;
    private String imageName;
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public AdImages(int id, String imageName, int order) {
        this.id = id;
        this.imageName = imageName;
        this.order = order;
    }

    public AdImages() {
    }
}
