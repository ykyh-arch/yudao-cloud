/**
 * 使用 MyBatis Plus 提升使用 MyBatis 的开发效率，提供了以下功能
 * 1、IdType 智能选择
 * 2、MyBatis Plus 自定义封装了分页插件、自动填充参数、IdType = INPUT 时，IKeyGenerator 生成策略
 * 3、此外还对 MyBatis Plus 组件进一步封装，如：
 * - BaseMapperX 封装了基础 crud 和 join 功能、
 * - LambdaQueryWrapperX、QueryWrapperX、MPJLambdaWrapperX 过滤值不存在的条件拼接、
 * - EncryptTypeHandler 对字符串进行加解密
 * - IntegerListTypeHandler List<Integer> 的类型转换器，对应数据库的 varchar 类型
 * - JsonLongSetTypeHandler Set<Long> 的类型转换器，对应数据库的 varchar 类型
 * - LongListTypeHandler List<Long> 的类型转换器，对应数据库的 varchar 类型
 * - StringListTypeHandler List<String> 的类型转换器，对应数据库的 varchar 类型
 *
 */
package cn.iocoder.yudao.framework.mybatis;
