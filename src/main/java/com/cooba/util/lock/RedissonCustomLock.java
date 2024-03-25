package com.cooba.util.lock;

import com.cooba.exception.NoLockException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RedissonCustomLock implements CustomLock {
    private final RedissonClient redissonClient;

    @Override
    public void tryLock(String key, long leaseTime, TimeUnit unit, Runnable runnable) {
        RLock lock = redissonClient.getLock(key);

        try {
            if (lock.tryLock(3000L, leaseTime, unit)) {
                try {
                    runnable.run();
                } finally {
                    lock.unlock();
                }
            } else {
                throw new NoLockException();
            }
        } catch (InterruptedException e) {
            throw new NoLockException();
        }
    }

    @Override
    public <T> T tryLock(String key, long leaseTime, TimeUnit unit, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);

        try {
            if (lock.tryLock(3000L, leaseTime, unit)) {
                try {
                   return supplier.get();
                } finally {
                    lock.unlock();
                }
            } else {
                throw new NoLockException();
            }
        } catch (InterruptedException e) {
            throw new NoLockException();
        }
    }
}
