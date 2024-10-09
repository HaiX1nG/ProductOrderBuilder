package com.productorderbuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.productorderbuilder.bean.Goods;
import com.productorderbuilder.bean.Order;
import com.productorderbuilder.utils.DataGenerator;
import com.productorderbuilder.utils.RandomTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        DataGenerator dataGenerator = new DataGenerator();
        RandomTime randomTime = new RandomTime();

        try {
            System.out.println("开始模拟数据生成...程序将在2s后执行，如需停止请按下组合键 Ctrl + C");
            Thread.sleep(2000);
            System.out.println("数据生成中...");
            while (true) {
                List<Goods> goodsInfo = dataGenerator.orderInfo();
                Order order = dataGenerator.OrderData(goodsInfo);

                ObjectNode json = objectMapper.createObjectNode();
                ArrayNode orderInfoArrayNode = objectMapper.createArrayNode(); // 创建一个新的 ArrayNode

                json.put("order_id", order.getOrder_id());
                json.put("user_id", order.getUser_id());
                json.put("username", order.getUsername());
                json.put("create_time", order.getCreate_time());

                for (Goods goods : order.getOrder_info()) {
                    orderInfoArrayNode.add(objectMapper.valueToTree(goods)); // 将每个 Goods 对象添加到 orderInfoArrayNode
                }
                json.set("order_info", orderInfoArrayNode); // 将 orderInfoArrayNode 设置为 order_info 的值
//                System.out.println(json.toString());
                logger.info(json.toString());
                System.out.println("此次数据生成耗时: " + randomTime.sleep() + " ms");
                Thread.sleep(randomTime.sleep());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}