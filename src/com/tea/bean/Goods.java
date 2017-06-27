package com.tea.bean;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Goods {
    private int id;
    private String name;
    private Double currentPrice;
    private Double originalPrice;
    private String field;
    private String effect;
    private String unit;
    private String image;
    private int number;
    private int soldNumber;

    private int ClassifyId;
    private int KindId;

    private String goodsTxt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }

    public int getClassifyId() {
        return ClassifyId;
    }

    public void setClassifyId(int classifyId) {
        ClassifyId = classifyId;
    }

    public int getKindId() {
        return KindId;
    }

    public void setKindId(int kindId) {
        KindId = kindId;
    }

    public String getGoodsTxt() {
        return goodsTxt;
    }

    public void setGoodsTxt(String goodsTxt) {
        this.goodsTxt = goodsTxt;
    }

    public String getFirstImage(){
        if(null == this.image || "".equals(this.image))
            return "";
        String[] images = this.image.split(",");
        if(null == images || images.length == 0)
            return "";
        return this.image.split(",")[0];
    }

    public Goods(int id, String name, Double currentPrice, Double originalPrice, String effect, String field,
                 String unit, String image, int number, int soldNumber, int classifyId, int kindId, String goodsTxt) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.effect = effect;
        this.field = field;
        this.unit = unit;
        this.image = image;
        this.number = number;
        this.soldNumber = soldNumber;
        ClassifyId = classifyId;
        KindId = kindId;
        this.goodsTxt = goodsTxt;
    }



    public Goods() {
    }
}
