package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Orders {
    private int id;
    private String name;
    private Goods goods;
    private int goodsNum;
    private Users users;
    private String time;
    private int state;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Orders(int id, String name, Goods goods, int goodsNum, Users users, String time, int state) {
        this.id = id;
        this.name = name;
        this.goods = goods;
        this.goodsNum = goodsNum;
        this.users = users;
        this.time = time;
        this.state = state;
    }

    public Orders() {
    }
}
