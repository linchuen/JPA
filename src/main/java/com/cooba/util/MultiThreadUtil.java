package com.cooba.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MultiThreadUtil {

    public static void run(int loopTime, Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(IntStream.rangeClosed(1, loopTime)
                .boxed()
                .map(i -> CompletableFuture.runAsync(runnable, executorService)).toArray(CompletableFuture[]::new));
        allFuture.join();
        System.out.println("All completed");
    }
}
