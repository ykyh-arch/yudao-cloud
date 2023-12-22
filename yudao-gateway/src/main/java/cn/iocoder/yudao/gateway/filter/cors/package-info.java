package cn.iocoder.yudao.gateway.filter.cors;

/**
 * 支持 跨域 Filter 且解决了 Spring Cloud Gateway 2.x 跨域时，出现重复 Origin 的 BUG，即：The ‘Access-Control-Allow-Origin’ header contains multiple values “*, *”, but only one is allowed.报错问题
 *
 *
 *
 *
 */