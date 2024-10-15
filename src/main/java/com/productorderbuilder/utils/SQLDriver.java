package com.productorderbuilder.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDriver {
    public Connection connMySQL() {
        String url = "jdbc:mysql://localhost:3306/order?serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        Connection connection = null;

        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
