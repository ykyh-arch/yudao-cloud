/**
 * 使用 Resilience4j 组件，实现服务保障，包括：
 * 1. 熔断器 - @CircuitBreaker
 * 2. 限流器 - @RateLimiter
 * 3. 舱壁隔离 - @Bulkhead
 * 4. 重试 - @Retry
 * 5. 限时器 - @TimeLimiter
 * 最新实现服务的限流与熔断，都是通过 aop 来实现的
 *
 */
package cn.iocoder.yudao.framework.resilience4j;
