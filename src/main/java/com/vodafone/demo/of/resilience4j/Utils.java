package com.vodafone.demo.of.resilience4j;

import java.util.function.Predicate;

public class Utils {

    public static String ANY_BACKEND = "anyBackend";

    public static void sleep(int delayInSeconds){
        try {
            Thread.sleep(delayInSeconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Predicate<Integer> oddEven(){
        return (i) -> i % 2 ==0;
    }

    public static Predicate<Integer> greaterThanFive(){
        return (i) -> i > 5;
    }



 }
