package org.company.algospectra_backend.ratelimiter;


import java.time.Instant;
import java.util.Deque;
import java.util.concurrent.*;

public class InMemoryRateLimiter {

    private final int limit;
    private final long durationInMillis;
    private final ConcurrentHashMap<String, Deque<Long>> requestMap = new ConcurrentHashMap<>();

    public InMemoryRateLimiter(int limit, long duration, TimeUnit unit) {
        this.limit = limit;
        this.durationInMillis = unit.toMillis(duration);
    }

    public synchronized boolean isAllowed(String key) {
        long now = Instant.now().toEpochMilli();
        Deque<Long> timestamps = requestMap.computeIfAbsent(key, k -> new ConcurrentLinkedDeque<>());

        // Remove outdated timestamps
        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > durationInMillis) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < limit) {
            timestamps.offerLast(now);
            return true;
        } else {
            return false;
        }
    }
}

