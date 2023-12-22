package cn.iocoder.yudao.gateway.filter.grey;

/**
 * 将 spring cloud gateway 的网关路由，整合添加了灰度 + LB （负载均衡）的功能！
 * 1、将网关的路由 uri: 将默认 lb: 替换成 grayLb:
 * 2、实现自定义的路由策略，{@link cn.iocoder.yudao.gateway.filter.grey.GrayLoadBalancer}
 *
 *
 */