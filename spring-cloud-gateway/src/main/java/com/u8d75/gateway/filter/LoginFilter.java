package com.u8d75.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.u8d75.core.base.result.BaseResult;
import com.u8d75.core.common.Constants;
import com.u8d75.core.constant.ResultCodeEnum;
import com.u8d75.core.token.GlobalTokenHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * LoginFilter
 */
@Slf4j
public class LoginFilter implements GlobalFilter, Ordered {

    public static final int LOGIN_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 1;

    Environment environment;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public LoginFilter(Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getOrder() {
        return LOGIN_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        String[] unauthurls = environment.getProperty("unauthurls", String[].class, new String[]{});

        for (String pattern : unauthurls) {
            if (antPathMatcher.match(pattern, path)) {
                return chain.filter(exchange);
            }
        }

        String token = exchange.getRequest().getHeaders().getFirst(Constants.X_AUTHORIZATION);
        if (token == null || token.isEmpty()) {
            return getReturnNoLogin(exchange);
        }
        DecodedJWT decodedJWT = GlobalTokenHolder.decodeToken(token);
        if (null == decodedJWT) {
            return getReturnNoLogin(exchange);
        }
        if (!GlobalTokenHolder.verifyAccessToken(GlobalTokenHolder.DEFAULT_TOKEN_SECRET, decodedJWT)) {
            return getReturnNoLogin(exchange);
        }
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            HashMap<String, Object> data = JSON.parseObject(Base64.decodeBase64(decodedJWT.getPayload()), HashMap.class);
            data.forEach((key, value) -> {
                try {
                    httpHeader.set(key, URLEncoder.encode(value.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    log.error("set header error:", e);
                }
            });
        };
        ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders).build();
        ServerWebExchange build = exchange.mutate().request(host).build();
        return chain.filter(build);

    }

    /**
     * getReturnNoLogin
     *
     * @param exchange exchange
     * @return Mono<Void>
     */
    public Mono<Void> getReturnNoLogin(ServerWebExchange exchange) {
        return Mono.defer(() -> {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(JSON.toJSONString(new BaseResult<>(ResultCodeEnum.GLOBAL_NOT_LOGIN)).getBytes(StandardCharsets.UTF_8));
            return exchange.getResponse().writeWith(Mono.just(buffer));
        });
    }
}
