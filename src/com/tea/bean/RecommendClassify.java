package com.tea.bean;

/**
 * Created by Administrator on 2016/10/29.
 */
public class RecommendClassify {
    private int id;
    private Classify classify;
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public RecommendClassify(int id, Classify classify, int order) {
        this.id = id;
        this.classify = classify;
        this.order = order;
    }

    public RecommendClassify() {
    }
}
