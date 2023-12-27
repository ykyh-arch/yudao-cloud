package cn.iocoder.yudao.framework.mybatis.core.type;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Set;

/**
 * 参考 {@link com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler} 实现
 * 在我们将字符串反序列化为 Set 并且泛型为 Long 时，如果每个元素的数值太小，会被处理成 Integer 类型，导致可能存在隐性的 BUG。
 *
 * 例如说哦，SysUserDO 的 postIds 属性
 *
 * @author 芋道源码
 */
public class JsonLongSetTypeHandler extends AbstractJsonTypeHandler<Object> {

    // 在 Java 中，由于泛型的类型信息在编译时会被擦除，而 TypeReference 类在反序列化过程中保留泛型类型信息，在反序列化过程中保留泛型类型信息
    // 支持 Jackson 库的泛型对象的序列化和反序列化操作
    private static final TypeReference<Set<Long>> TYPE_REFERENCE = new TypeReference<Set<Long>>(){};

    @Override
    protected Object parse(String json) {
        return JsonUtils.parseObject(json, TYPE_REFERENCE);
    }

    @Override
    protected String toJson(Object obj) {
        return JsonUtils.toJsonString(obj);
    }

}
