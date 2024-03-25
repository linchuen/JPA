package com.cooba.util.lock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface CustomLock {
    void tryLock(String key, long leaseTime, TimeUnit unit, Runnable runnable);

    <T> T tryLock(String key, long leaseTime, TimeUnit unit, Supplier<T> supplier);
}
