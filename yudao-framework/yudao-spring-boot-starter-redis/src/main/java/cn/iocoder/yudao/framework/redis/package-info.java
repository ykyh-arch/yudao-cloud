/**
 * 采用 Spring Data Redis 操作 Redis，底层使用 Redisson 作为客户端
 * 提供了以下功能：
 * 1、RedisTemplate 配置，实现以 JSON 序列化方式存储
 * 2、接入 Redis 作为 spring cache 的缓存实现方式，提供缓存统一配置使用自定义:作为缓存分隔符，以及自定义缓存过期时间
 *
 */
package cn.iocoder.yudao.framework.redis;
