/**
 * 用户操作日志：记录用户的操作，用于对用户的操作的审计与追溯，永久保存。
 * 提供了以下功能：
 * 1、切面方式记录，对 @OperateLog 或 @Operation 标记的方法进行日志记录，持久化到数据库
 *
 *
 * @author 芋道源码
 */
package cn.iocoder.yudao.framework.operatelog;
