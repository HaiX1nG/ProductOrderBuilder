package com.productorderbuilder.bean;

import java.util.List;

public class Order {
    private Integer user_id;
    private String username;
    private String create_time;
    private long order_id;
    private List<GoodsTable> order_info;

    public Order(Integer user_id, String username, String create_time, long order_id, List<GoodsTable> order_info) {
        this.user_id = user_id;
        this.username = username;
        this.create_time = create_time;
        this.order_id = order_id;
        this.order_info = order_info;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public List<GoodsTable> getOrder_info() {
        return order_info;
    }

    public void setOrder_info(List<GoodsTable> order_info) {
        this.order_info = order_info;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", create_time='" + create_time + '\'' +
                ", order_id=" + order_id +
                ", order_info=" + order_info +
                '}';
    }
}
