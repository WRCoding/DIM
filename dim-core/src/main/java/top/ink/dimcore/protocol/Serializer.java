package top.ink.dimcore.protocol;

/**
 * desc: Serializer
 *
 * @author ink
 * date:2022-02-28 21:06
 */
public interface Serializer {

    /**
     * Description: 反序列化
     * @param clazz
     * @param bytes
     * @return T
     * Author: ink
     * Date: 2022/2/28
    */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * Description: 序列化
     * @param object
     * @return byte[]
     * Author: ink
     * Date: 2022/2/28
    */
    <T> byte[] serialize(T object);
}
