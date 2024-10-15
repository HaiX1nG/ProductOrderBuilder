package com.productorderbuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.productorderbuilder.bean.GoodsTable;
import com.productorderbuilder.bean.Order;
import com.productorderbuilder.utils.DataGenerator;
import com.productorderbuilder.utils.RandomTime;
import com.productorderbuilder.utils.SQLDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private final SQLDriver sqlDriver = new SQLDriver();

    public void dataSynchronization_OrderTable(long oId, String createTime, int uId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = sqlDriver.connMySQL();
            String orderTableSql = "INSERT INTO order_table (o_id, create_time, u_id) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(orderTableSql);
            ps.setLong(1, oId);
            ps.setString(2, createTime);
            ps.setInt(3, uId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void DataSynchronization_OrderInfo(long oId, List<GoodsTable> order_info) {
        Connection conn = sqlDriver.connMySQL();
        PreparedStatement ps = null;

        if (conn != null) {
            String orderInfoSql = "INSERT INTO order_info (o_id, g_id, num) VALUES (?, ?, ?)";
            try {
                for (GoodsTable goodsTable : order_info) {
                    int goodsId = goodsTable.getgId();
                    int num = goodsTable.getNum();
                    ps = conn.prepareStatement(orderInfoSql);
                    ps.setLong(1, oId);
                    ps.setInt(2, goodsId);
                    ps.setInt(3, num);

                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        DataGenerator.UserSelection userSelection = dataGenerator.selectUIdAndUsername();
        ObjectMapper objectMapper = new ObjectMapper();
        RandomTime randomTime = new RandomTime();
        Main main = new Main();

        try {
            System.out.println("开始模拟数据生成...程序将在2s后执行，如需停止请按下组合键 Ctrl + C");

            while (true) {
                int time = randomTime.sleep();
                List<GoodsTable> goodsInfo = dataGenerator.createOrderInfo();
                Order order = dataGenerator.orderData(goodsInfo);

                ObjectNode json = objectMapper.createObjectNode();
                ArrayNode orderInfoArrayNode = objectMapper.createArrayNode();
                Thread.sleep(time);
                json.put("order_id", order.getOrder_id());
                json.put("user_id", order.getUser_id());
                json.put("username", order.getUsername());
                json.put("create_time", order.getCreate_time());

                for (GoodsTable goodsTable : order.getOrder_info()) {
                    orderInfoArrayNode.add(objectMapper.valueToTree(goodsTable));
                }

                json.set("order_info", orderInfoArrayNode);

                main.dataSynchronization_OrderTable(order.getOrder_id(), order.getCreate_time(), order.getUser_id());
                main.DataSynchronization_OrderInfo(order.getOrder_id(), order.getOrder_info());
                logger.info(json.toString());
                System.out.println("此次数据生成耗时: " + time + " ms");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}