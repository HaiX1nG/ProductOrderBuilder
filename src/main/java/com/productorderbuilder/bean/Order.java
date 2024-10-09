package com.productorderbuilder.bean;

import java.util.List;

public class Order {
    private String user_id;
    private String username;
    private String create_time;
    private String order_id;
    private List<Goods> order_info;

    @Override
    public String toString() {
        return "Order{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", create_time='" + create_time + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_info=" + order_info +
                '}';
    }

    public Order(String user_id, String username, String create_time, String order_id, List<Goods> order_info) {
        this.user_id = user_id;
        this.username = username;
        this.create_time = create_time;
        this.order_id = order_id;
        this.order_info = order_info;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<Goods> getOrder_info() {
        return order_info;
    }

    public void setOrder_info(List<Goods> order_info) {
        this.order_info = order_info;
    }
}
