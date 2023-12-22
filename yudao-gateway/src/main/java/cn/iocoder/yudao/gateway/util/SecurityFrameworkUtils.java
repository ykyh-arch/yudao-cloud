package cn.iocoder.yudao.gateway.util;

import cn.hutool.core.map.MapUtil;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.gateway.filter.security.LoginUser;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * 安全服务工具类
 *
 * copy from yudao-spring-boot-starter-security 的 SecurityFrameworkUtils 类
 *
 * @author 芋道源码
 */
public class SecurityFrameworkUtils {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTHORIZATION_BEARER = "Bearer";

    private static final String LOGIN_USER_HEADER = "login-user";

    private static final String LOGIN_USER_ID_ATTR = "login-user-id";
    private static final String LOGIN_USER_TYPE_ATTR = "login-user-type";

    private SecurityFrameworkUtils() {}

    /**
     * 从请求中，获得认证 Token
     *
     * @param exchange 请求
     * @return 认证 Token
     */
    public static String obtainAuthorization(ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
        if (index == -1) { // 未找到
            return null;
        }
        return authorization.substring(index + 7).trim();
    }

    /**
     * 设置登录用户
     *
     * @param exchange 请求
     * @param user 用户
     */
    public static void setLoginUser(ServerWebExchange exchange, LoginUser user) {
        exchange.getAttributes().put(LOGIN_USER_ID_ATTR, user.getId());
        exchange.getAttributes().put(LOGIN_USER_TYPE_ATTR, user.getUserType());
    }

    /**
     * 移除请求头的用户
     *
     * @param exchange 请求
     * @return 请求
     */
    public static ServerWebExchange removeLoginUser(ServerWebExchange exchange) {
        // 如果不包含，直接返回
        if (!exchange.getRequest().getHeaders().containsKey(LOGIN_USER_HEADER)) {
            return exchange;
        }
        // 如果包含，则移除。参考 RemoveRequestHeaderGatewayFilterFactory 实现
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(httpHeaders -> httpHeaders.remove(LOGIN_USER_HEADER)).build();
        return exchange.mutate().request(request).build();
    }

    /**
     * 获得登录用户的编号
     *
     * @param exchange 请求
     * @return 用户编号
     */
    public static Long getLoginUserId(ServerWebExchange exchange) {
        return MapUtil.getLong(exchange.getAttributes(), LOGIN_USER_ID_ATTR);
    }

    /**
     * 获得登录用户的类型
     *
     * @param exchange 请求
     * @return 用户类型
     */
    public static Integer getLoginUserType(ServerWebExchange exchange) {
        return MapUtil.getInt(exchange.getAttributes(), LOGIN_USER_TYPE_ATTR);
    }

    /**
     * 将 user 并设置到 login-user 的请求头，使用 json 存储值
     *
     * @param builder 请求
     * @param user 用户
     */
    public static void setLoginUserHeader(ServerHttpRequest.Builder builder, LoginUser user) {
        // 在实际应用中，例如在Spring Cloud Gateway中，底层使用Netty处理网络请求，会创建并使用ServerHttpRequest实例来承载请求相关的属性和请求体。具体的实现类是ReactorServerHttpRequest，这是由于它的父类AbstractServerHttpRequest在初始化内部属性headers的时候会把请求的HTTP头部封装为只读的实例。
        builder.header(LOGIN_USER_HEADER, JsonUtils.toJsonString(user));
    }

}
