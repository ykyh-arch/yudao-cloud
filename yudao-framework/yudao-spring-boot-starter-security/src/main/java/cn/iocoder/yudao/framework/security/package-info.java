/**
 * 基于 Spring Security 框架
 * 实现安全认证功能，提供以下的功能
 * 1、rpc feign 支持请求 header 中携带 login-user 信息
 *2、用户未登录拦截、登录认证失败(未登录)拦截、权限不够拦截器、token 过滤器获取 login-user 存储到 spring security 中、用户权限控制、等 bean 对象注册
 * 3、自定义的 Spring Security 配置适配器，实现 Security 访问权限控制
 *
 * @author 芋道源码
 */
package cn.iocoder.yudao.framework.security;
