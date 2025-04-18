package org.company.algospectra_backend.ratelimiter;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.*;

@Aspect
@Component
@Slf4j
public class RateLimitAspect {

    private final Map<String, InMemoryRateLimiter> limiterCache = new ConcurrentHashMap<>();

    @Around("@annotation(org.company.algospectra_backend.ratelimiter.RateLimit)")
    public Object handleRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        // Get Client IP
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String clientIp = request.getRemoteAddr();

        // Unique key per endpoint + IP
        String key = method.getName() + ":" + clientIp;

        // Fetch or create limiter
        limiterCache.computeIfAbsent(key,
                k -> new InMemoryRateLimiter(rateLimit.limit(), rateLimit.duration(), rateLimit.timeUnit()));

        boolean allowed = limiterCache.get(key).isAllowed(key);

        if (!allowed) {
            log.warn("Rate limit exceeded for {} on {}", clientIp, method.getName());
            throw new RateLimitExceededException("Too many requests. Please try again later.");
        }

        return joinPoint.proceed();
    }
}
