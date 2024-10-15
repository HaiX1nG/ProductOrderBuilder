package com.productorderbuilder.utils;

import com.productorderbuilder.bean.GoodsTable;
import com.productorderbuilder.bean.Order;
import com.productorderbuilder.bean.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {
    private final SQLDriver sqlDriver = new SQLDriver();
    private final Random random = new Random();
    private final SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    public class UserSelection {
        private Integer uId;
        private String username;

        public UserSelection(Integer uId, String username) {
            this.uId = uId;
            this.username = username;
        }

        public Integer getuId() {
            return uId;
        }

        public void setuId(Integer uId) {
            this.uId = uId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public UserSelection selectUIdAndUsername() {
        Connection conn = sqlDriver.connMySQL();
        if (conn != null) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                String selectAllUsers = "select * from users";
                rs = stmt.executeQuery(selectAllUsers);
                List<Users> usersList = new ArrayList<>();

                while(rs.next()) {
                    Integer uId = rs.getInt("u_id");
                    String username = rs.getString("username");
                    String gender = rs.getString("gender");
                    int age = rs.getInt("age");
                    Users users = new Users();
                    users.setuId(uId);
                    users.setUsername(username);
                    users.setGender(gender);
                    users.setAge(age);
                    usersList.add(users);
                }

                int index = random.nextInt(usersList.size());
                Users selectUser = usersList.get(index);

                return new UserSelection(selectUser.getuId(), selectUser.getUsername());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    // 生成订单时间
    public String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatterDate = now.format(formatter);
        return formatterDate;
    }

    // 用雪花算法生成订单id
    public long orderId() {
        return snowflakeIdWorker.snowflakeID(1, 1);
    }

    // 生成一个用户的订单种类，不会超过goods总量
    public int orderInfoNum() {
        Connection conn = sqlDriver.connMySQL();
        if (conn != null) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                String selectGoodsCount = "select count(*) as total from goods";
                rs = stmt.executeQuery(selectGoodsCount);
                if (rs.next()) {
                    int row = rs.getInt("total");
                    return random.nextInt((row - 1) + 1) + 1;
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return 0;
    }

    public int num() {
        return random.nextInt((15 -1) + 1) + 1;
    }

    public List<GoodsTable> createOrderInfo() {
        Connection conn = sqlDriver.connMySQL();
        if (conn != null) {
            Statement stmt = null;
            ResultSet rs = null;
            int num = num();
            int orderCount = orderInfoNum();
            try {
                stmt = conn.createStatement();
                String selectAllGoods = "select * from goods";
                rs = stmt.executeQuery(selectAllGoods);

                Set<GoodsTable> goodsList = new HashSet<>();

                while (rs.next()) {
                    Integer gId = rs.getInt("g_id");
                    String goodsName = rs.getString("goods_name");
                    String goodsType = rs.getString("goods_type");
                    double goodsPrice = rs.getDouble("goods_price");

                    GoodsTable goodsTable = new GoodsTable(gId, goodsName, goodsType, goodsPrice, num);
                    goodsList.add(goodsTable);
                }

                List<GoodsTable> newGoodsList = new ArrayList<>();
                for (int i = 0; i < orderCount; i++) {
                    int index = random.nextInt(goodsList.size());
                    GoodsTable[] goodsArray = goodsList.toArray(new GoodsTable[0]);
                    GoodsTable selectedGoods = goodsArray[index];
                    GoodsTable newGoods = new GoodsTable(selectedGoods.getgId(), selectedGoods.getGoodsName(), selectedGoods.getGoodsType(), selectedGoods.getGoodsPrice(), num());
                    newGoodsList.add(newGoods);
                }

                return newGoodsList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public Order orderData(List<GoodsTable> goodsInfo) {
        UserSelection selection = selectUIdAndUsername();
        Integer uId = selection.getuId();
        String username = selection.getUsername();
        String createTime = createTime();
        long orderId = orderId();
        Order order = new Order(uId, username, createTime, orderId, goodsInfo);
        return order;
    }
}
