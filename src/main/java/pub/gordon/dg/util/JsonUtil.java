package pub.gordon.dg.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Gordon
 * @date 2017-11-16 23:43
 */
public class JsonUtil {
    private static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();


    /**
     * 将JSON字符串转换为{@code clazz}实例
     *
     * @param jsonStr     JSON字符串
     * @param targetClazz 转换后的目标类
     * @param <T>         generic
     * @return 目标类型实例
     */
    public static <T> T parse(String jsonStr, Class<T> targetClazz) throws IOException {
        JsonNode jn = JACKSON_MAPPER.readTree(jsonStr);
        T target = JACKSON_MAPPER.convertValue(jn, targetClazz);
        return target;
    }

}
