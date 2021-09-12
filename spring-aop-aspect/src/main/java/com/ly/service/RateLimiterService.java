package com.ly.service;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private Map<String,RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    private RateLimiter getOrCreateRateLimiterByName(String name,double rate) {
        RateLimiter rateLimiter = rateLimiterMap.get(name);
        rateLimiter.tryAcquire();
        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(rate);
            rateLimiterMap.put(name, rateLimiter);

        }
        return rateLimiter;
    }
}
