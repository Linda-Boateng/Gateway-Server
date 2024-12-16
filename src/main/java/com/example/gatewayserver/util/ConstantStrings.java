package com.example.gatewayserver.util;

public final class ConstantStrings {
    private ConstantStrings() {
    }
    public static final String USER_SERVICE = "http://localhost:9000";
    public static final String BOOK_SERVICE = "http://localhost:5008";
    public static final String ORDER_SERVICE = "http://localhost:5007";
    public static final String FALLBACK_MESSAGE = "Service is currently unavailable. Please try again later.";
    public static final String FALLBACK_URL = "forward:/fallbackRoute";
    public static final String FALLBACK_ROUTE = "/fallbackRoute";
    public static final String USER_SERVICE_ROUTE = "/api/auth/**";
    public static final String ORDER_SERVICE_ROUTE = "/api/v1/**";
    public static final String BOOK_SERVICE_ROUTE = "/api/v1/user/**";
    public static final String USER_SERVICE_CIRCUITBREAKER = "user-service-circuitbreaker";
    public static final String ORDER_SERVICE_CIRCUITBREAKER = "order-service-circuitbreaker";
    public static final String BOOK_SERVICE_CIRCUITBREAKER = "book-service-circuitbreaker";
    public static final String USER_SERVICE_RETRY = "user-service-retry";
    public static final String ORDER_SERVICE_RETRY = "order-service-retry";
    public static final String BOOK_SERVICE_RETRY = "book-service-retry";

}
