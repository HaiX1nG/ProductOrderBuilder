package com.productorderbuilder.bean;

import java.util.Objects;

public class GoodsTable {
    private Integer gId;
    private String goodsName;
    private String goodsType;
    private double goodsPrice;

    private int num;

    public GoodsTable(Integer gId, String goodsName, String goodsType, double goodsPrice, int num) {
        this.gId = gId;
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.goodsPrice = goodsPrice;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsTable goodsTable = (GoodsTable) o;
        return Objects.equals(gId, goodsTable.gId) &&
                Objects.equals(goodsName, goodsTable.goodsName) &&
                Objects.equals(goodsType, goodsTable.goodsType) &&
                Objects.equals(goodsPrice, goodsTable.goodsPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gId, goodsName, goodsType, goodsPrice);
    }
}
