package com.productorderbuilder.bean;

import java.util.Objects;

public class Goods {
    private Integer goods_id;
    private String goods_name;
    private int num;
    private String type;
    private double goods_price;

    public Goods(Integer goods_id, String goods_name, int num, String type, double goods_price) {
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.num = num;
        this.type = type;
        this.goods_price = goods_price;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", num=" + num +
                ", type='" + type + '\'' +
                ", goods_price=" + goods_price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(goods_id, goods.goods_id) &&
                Objects.equals(goods_name, goods.goods_name) &&
                Objects.equals(type, goods.type) &&
                Objects.equals(goods_price, goods.goods_price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods_id, goods_name, type, goods_price);
    }
}
