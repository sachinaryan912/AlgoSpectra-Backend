package org.company.algospectra_backend.ratelimiter;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int limit(); // number of requests
    long duration(); // duration value
    TimeUnit timeUnit(); // duration unit
}
