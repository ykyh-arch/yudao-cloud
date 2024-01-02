package cn.iocoder.yudao.framework.env.core.fegin;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.env.core.context.EnvContextHolder;
import cn.iocoder.yudao.framework.env.core.util.EnvUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 多环境的 {@link RequestInterceptor} 实现类：Feign 请求时，将 tag 设置到 header 中，继续传递给被调用的服务
 *
 * @author 芋道源码
 */
public class EnvRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 通过 web servlet 将 tag 存储起来
        String tag = EnvContextHolder.getTag();
        if (StrUtil.isNotEmpty(tag)) {
            EnvUtils.setTag(requestTemplate, tag);
        }
    }

}
