package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Recommend {
    private int id;
    private Goods goods;
    private int classifyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public Recommend(int id, Goods goods, int classifyId) {
        this.id = id;
        this.goods = goods;
        this.classifyId = classifyId;
    }

    public Recommend() {
    }
}
