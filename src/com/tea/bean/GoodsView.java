package com.tea.bean;

/**
 * Created by Administrator on 2016/11/9.
 */
public class GoodsView {

    private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsView(Goods goods) {
        this.goods = goods;
    }

    public GoodsView() {
    }

    public String getFirstImage(){
        if(null == this.goods || null == this.goods.getImage() || "".equals(this.goods.getImage())){
            return "";
        }

        String[] images = this.goods.getImage().split(",");

        if(images.length == 0){
            return null;
        }

        return images[0];
    }
}
