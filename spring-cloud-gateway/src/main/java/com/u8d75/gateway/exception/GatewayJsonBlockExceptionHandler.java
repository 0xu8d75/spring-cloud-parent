package com.u8d75.gateway.exception;

import com.u8d75.core.base.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * GatewayJsonBlockExceptionHandler
 */
@Slf4j
public class GatewayJsonBlockExceptionHandler implements WebExceptionHandler {

    private List<ViewResolver> viewResolvers;
    private List<HttpMessageWriter<?>> messageWriters;
    private ServerResponse.Context context;

    public GatewayJsonBlockExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolvers;
        this.messageWriters = serverCodecConfigurer.getWriters();
        this.context = new ServerResponse.Context() {
            @Override
            public List<HttpMessageWriter<?>> messageWriters() {
                return GatewayJsonBlockExceptionHandler.this.messageWriters;
            }

            @Override
            public List<ViewResolver> viewResolvers() {
                return GatewayJsonBlockExceptionHandler.this.viewResolvers;
            }
        };
    }

    /**
     * writeResponse
     *
     * @param response response
     * @param exchange exchange
     * @return Mono<Void>
     */
    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        return response.writeTo(exchange, context);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("gateway exception:", ex);

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        return ServerResponse
                .status(((ResponseStatusException) ex).getStatusCode())
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(BodyInserters.fromValue(BaseResult.innerError()))
                .flatMap(response -> writeResponse(response, exchange));
    }

}
