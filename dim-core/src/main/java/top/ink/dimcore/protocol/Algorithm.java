package top.ink.dimcore.protocol;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

/**
 * desc: Algorithm
 *
 * @author ink
 * date:2022-02-28 21:09
 */
public enum Algorithm implements Serializer {

    /** JSON序列化 */
    json{
        @Override
        public <T> T deserialize(Class<T> clazz, byte[] bytes) {
            String json = new String(bytes, StandardCharsets.UTF_8);
            return JSON.parseObject(json, clazz);
        }

        @Override
        public <T> byte[] serialize(T object) {
            String json = JSON.toJSONString(object);
            return json.getBytes(StandardCharsets.UTF_8);
        }
    };

}
