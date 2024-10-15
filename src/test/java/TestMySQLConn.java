import com.productorderbuilder.bean.Users;
import com.productorderbuilder.utils.SQLDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMySQLConn {
    private static final Random random = new Random();

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

    public int orderInfoNum() {
        SQLDriver sqlDriver = new SQLDriver();
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
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        TestMySQLConn testMySQLConn = new TestMySQLConn();
        Connection connection = testMySQLConn.connMySQL();

        if (connection != null) {
            Statement stmt = null;
            ResultSet resultSet = null;

            try {
                stmt = connection.createStatement();
                String sql = "select * from users";
                resultSet = stmt.executeQuery(sql);
                List<Users> list = new ArrayList<>();

                while (resultSet.next()) {
                    Users users = new Users();
                    users.setuId(resultSet.getInt("u_id"));
                    users.setUsername(resultSet.getString("username"));
                    users.setGender(resultSet.getString("gender"));
                    users.setAge(resultSet.getInt("age"));
                    list.add(users);
                }

                int index = random.nextInt(list.size());
                System.out.println("u_id: " + list.get(index).getuId() + " username: " + list.get(index).getUsername());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (resultSet != null) resultSet.close();
                    if (stmt != null) stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(testMySQLConn.orderInfoNum());
        }
    }
}
