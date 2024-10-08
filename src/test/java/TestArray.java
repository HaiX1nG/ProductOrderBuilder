import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.productorderbuilder.bean.Goods;
import com.productorderbuilder.bean.Order;
import com.productorderbuilder.utils.RandomTime;
import com.productorderbuilder.utils.SnowflakeIdWorker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestArray {
    public void randomArray() {
        Random random = new Random();
        while (true){
            List<String> a = new ArrayList<>();
            String[] b = {
                    "A", "B", "C", "D", "E"
            };

            for(int i = 0; i < 5; i++) {
                int index = random.nextInt(b.length);
                String value = b[index];
                if (!a.contains(value)) {
                    a.add(value);
                }
            }
            System.out.println(a);
        }
    }

    private final SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
    private final Random random = new Random();

    public String UsernameCreate() {
        Random random = new Random();
        List<String> names = new ArrayList<>();
        String[] firstNames = {
                "Alice", "Bob", "Charlie", "David", "Eva",
                "Fiona", "George", "Hannah", "Ian", "Julia",
                "Kevin", "Lucy", "Michael", "Nathan", "Olivia",
                "Patrick", "Quinn", "Rachel", "Steven", "Tina",
                "Uma", "Victor", "Wendy", "Xander", "Yvonne", "Zach"
        };

        for (int i = 0; i < 30; i++) {
            int index = random.nextInt(firstNames.length);
            names.add(firstNames[index]);
        }

        // 随机选名
        int randomIndex = random.nextInt(names.size());
        return names.get(randomIndex);
    }

    public String CreateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatterDate = now.format(formatter);
        return formatterDate;
    }


    // 用于生成订单id
    private static int start = 0;

    public String OrderId() {
        long timestamp = System.currentTimeMillis();
        start++;
        String orderId = timestamp + "-sdq4kj3wiw-" + start;
        return orderId;
    }

    public int OrderInfoNum() {
        Random random = new Random();
        return random.nextInt((13 - 1) + 1) + 1;
    }

    public int Num() {
        Random random = new Random();
        return random.nextInt((15 - 1) + 1) + 1;
    }

    public List<Goods> orderInfo() {
        Random random = new Random();
        int num = Num();
        int orderCount = OrderInfoNum();
            Set<Goods> ordersSet = new HashSet<>();
            Goods[] goodsArray = {
                    new Goods("1001", "可口可乐", num, "饮料", 3.00),
                    new Goods("1002", "百事可乐", num, "饮料", 3.00),
                    new Goods("1003", "芬达", num, "饮料", 3.00),
                    new Goods("1004", "老婆饼", num, "方便食品", 3.90),
                    new Goods("1005", "统一老坛酸菜牛肉面", num, "方便食品", 5.00),
                    new Goods("1006", "苹果", num, "水果蔬菜", 8.50),
                    new Goods("1007", "香蕉", num, "水果蔬菜", 12.60),
                    new Goods("1008", "奥利奥夹心饼干(草莓味)", num, "方便食品", 8.90),
                    new Goods("1009", "三全汤圆", num, "速冻食品", 16.90),
                    new Goods("1010", "思念水饺", num, "速冻食品", 28.90),
                    new Goods("1011", "牛奶", num, "饮料", 5.00),
                    new Goods("1012", "心相印纸巾", num, "清洁纸品", 6.90),
                    new Goods("1013", "纸尿裤", num, "清洁纸品", 12.80)
            };

            for (int i = 0; i < orderCount; i++) {
                int index = random.nextInt(goodsArray.length);
                Goods selectedGoods = goodsArray[index];
                // 确保每次生成的 Goods 对象都有唯一的属性值
                Goods goods = new Goods(selectedGoods.getGoods_id(), selectedGoods.getGoods_name(), Num(), selectedGoods.getType(), selectedGoods.getGoods_price());
                ordersSet.add(goods);
            }
            return new ArrayList<>(ordersSet);
    }

    public Order OrderData(List<Goods> goodsInfo) {
        long userId = snowflakeIdWorker.SnowflakeID(1, 1);
        String username = UsernameCreate();
        String createTime = CreateTime();
        String orderId = OrderId();
        Order order = new Order(userId, username, createTime, orderId, goodsInfo);
        return order;

    }


    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        TestArray testArray = new TestArray();
        RandomTime randomTime = new RandomTime();
        try {
            while (true) {
                List<Goods> goodsInfo = testArray.orderInfo();
                Order order = testArray.OrderData(goodsInfo);

                ObjectNode json = objectMapper.createObjectNode();
                ArrayNode arrayNode = objectMapper.createArrayNode();

                json.put("order_id", order.getOrder_id());
                json.put("user_id", order.getUser_id());
                json.put("username", order.getUsername());
                json.put("create_time", order.getCreate_time());
                arrayNode.add(objectMapper.valueToTree(order.getOrder_info()));
                json.set("order_info", arrayNode);
                System.out.println(json.toString());
                Thread.sleep(randomTime.Sleep());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
