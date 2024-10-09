package com.productorderbuilder.utils;

import xyz.downgoon.snowflake.Snowflake;

public class SnowflakeIdWorker {
    public String snowflakeID(Integer datacenterId, Integer workerId) {
        Snowflake idWorker = new Snowflake(datacenterId, workerId);
        long id = idWorker.nextId();
        return String.valueOf(id);
    }
}
