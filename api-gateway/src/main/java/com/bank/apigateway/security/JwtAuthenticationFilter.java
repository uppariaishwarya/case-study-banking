package com.bank.apigateway.security;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -1; // ✅ HIGH PRIORITY (IMPORTANT)
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ✅ Allow auth endpoints only
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); // ✅ BLOCK
        }

        String token = authHeader.substring(7);

        try {
        	var claims = JwtUtil.validateToken(token);

        	String username = claims.getSubject();
        	String role = claims.get("role", String.class);   // ✅ ADD THIS

        	var mutatedRequest = exchange.getRequest().mutate()
        	        .header("X-User", username)
        	        .header("X-Role", role)      // ✅ ADD THIS
        	        .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); // ✅ BLOCK
        }
    }
}
