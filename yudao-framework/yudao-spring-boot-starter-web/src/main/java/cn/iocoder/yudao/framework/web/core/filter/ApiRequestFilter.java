package cn.iocoder.yudao.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.web.config.WebProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤 /admin-api、/app-api 等 API 请求的过滤器
 *
 * @author 芋道源码
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter { // 它的主要作用是确保每个请求只被该过滤器链中的某个过滤器执行一次。

    protected final WebProperties webProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) { // 返回true，表示应该跳过当前过滤器的执行，直接进入下一个过滤器；如果返回false，表示应该继续执行当前过滤器。
        // 只过滤 API 请求的地址
        return !StrUtil.startWithAny(request.getRequestURI(), webProperties.getAdminApi().getPrefix(),
                webProperties.getAppApi().getPrefix());
    }

}
