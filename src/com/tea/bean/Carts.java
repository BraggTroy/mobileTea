package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Carts {
    private int id;
    private int goodsNum;
    private Goods goods;
    private Users users;

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

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Carts(int id, int goodsNum, Goods goods, Users users) {
        this.id = id;
        this.goodsNum = goodsNum;
        this.goods = goods;
        this.users = users;
    }

    public Carts() {
    }
}
