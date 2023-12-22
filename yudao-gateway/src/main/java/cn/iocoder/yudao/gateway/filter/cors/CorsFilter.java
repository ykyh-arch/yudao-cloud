package cn.iocoder.yudao.gateway.filter.cors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 跨域 Filter
 *
 * @author 芋道源码
 */
@Component
public class CorsFilter implements WebFilter {

    private static final String ALL = "*";
    private static final String MAX_AGE = "3600L";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 非跨域请求，直接放行
        ServerHttpRequest request = exchange.getRequest();
        if (!CorsUtils.isCorsRequest(request)) {
            return chain.filter(exchange);
        }

        // 设置跨域响应头
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add("Access-Control-Allow-Origin", ALL);
        headers.add("Access-Control-Allow-Methods", ALL);
        headers.add("Access-Control-Allow-Headers", ALL);
        headers.add("Access-Control-Max-Age", MAX_AGE);
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            // Mono.empty() 是 Spring WebFlux 中的一个方法，它返回一个空的 Mono 对象。在响应式编程中，Mono 是一个表示单个异步结果的类。当您需要表示没有数据可供处理时，可以使用 Mono.empty() 方法创建一个空的 Mono 对象。这个空的 Mono 对象不会发生任何值，也不会完成或失败。
            return Mono.empty();
        }
        return chain.filter(exchange);
    }

}
