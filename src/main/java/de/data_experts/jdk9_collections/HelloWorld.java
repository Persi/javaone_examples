package de.data_experts.jdk9_collections;

import io.reactivex.*;

import java.util.concurrent.atomic.AtomicInteger;

public class HelloWorld {
    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);
    }
}