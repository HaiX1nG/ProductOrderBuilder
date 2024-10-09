package com.productorderbuilder.utils;

import com.productorderbuilder.bean.Goods;
import com.productorderbuilder.bean.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {
    private final SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
    private final Random random = new Random();
    public String usernameCreate() {
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

    public String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatterDate = now.format(formatter);
        return formatterDate;
    }


    // 用于生成订单id
    private static int start = 0;

    public String orderId() {
        long timestamp = System.currentTimeMillis();
        start++;
        String orderId = timestamp + "-sdq4kj3wiw-" + start;
        return orderId;
    }

    public int orderInfoNum() {
        Random random = new Random();
        return random.nextInt((13 - 1) + 1) + 1;
    }

    public int num() {
        Random random = new Random();
        return random.nextInt((15 - 1) + 1) + 1;
    }

    public List<Goods> orderInfo() {
        int num = num();
        int orderCount = orderInfoNum();
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
            Goods goods = new Goods(selectedGoods.getGoods_id(), selectedGoods.getGoods_name(), num, selectedGoods.getType(), selectedGoods.getGoods_price());
            ordersSet.add(goods);
        }
        return new ArrayList<>(ordersSet);
    }

    public Order OrderData(List<Goods> goodsInfo) {
        String userId = snowflakeIdWorker.snowflakeID(1, 1);
        String username = usernameCreate();
        String createTime = createTime();
        String orderId = orderId();
        Order order = new Order(userId, username, createTime, orderId, goodsInfo);
        return order;

    }
}
