/**
 * 消息队列，基于 Redis 提供：
 * 1. 基于 Pub/Sub 实现广播消费
 * 2. 基于 Stream 实现集群消费
 * 可以实现消息的生产与消费过程中的，前置或后置自定义逻辑
 *
 */
package cn.iocoder.yudao.framework.mq.redis;
