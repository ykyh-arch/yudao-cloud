/**
 * 开发环境拓展，实现类似阿里的特性环境的能力
 * 1. https://segmentfault.com/a/1190000018022987
 * env 提供一种 tag 机制，通过配置文件配置 yudao.env.tag 将其设置到 spring.cloud.nacos.discovery.metadata.tag 注册中心，
 * 根据 ServiceInstance 中 Metadata 是否包含与请求头相同的 tag 选择对应的服务实例列表
 * 后续网关灰度发布时，tag 机制也是判断 ServiceInstance 中 Metadata 是否包含与请求头相同的 tag 选择对应的服务实例列表
 *
 * @author 芋道源码
 */
package cn.iocoder.yudao.framework.env;
