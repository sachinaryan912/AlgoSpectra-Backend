package org.company.algospectra_backend.ratelimiter;



public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
