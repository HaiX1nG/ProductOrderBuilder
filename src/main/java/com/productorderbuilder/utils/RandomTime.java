package com.productorderbuilder.utils;

import java.util.Random;

public class RandomTime {
    private final Random random = new Random();
    public int Sleep() {
        return random.nextInt((5000 - 1000) + 1000) + 1000;
    }
}
