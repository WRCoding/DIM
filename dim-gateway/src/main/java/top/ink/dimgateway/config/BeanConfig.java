package top.ink.dimgateway.config;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.core.KafkaAdmin;
import top.ink.dimgateway.util.route.RouteHandle;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * desc: Bean配置类
 *
 * @author ink
 * date:2022-04-04 15:02
 */
@Configuration
@Slf4j
public class BeanConfig {

    @Value("${dim.zookeeper.address}")
    private String zkAddress;

    @Value("${dim.zookeeper.connect.timeout}")
    private Integer zkTimeOut;

    @Value("${dim.routehandle.class}")
    private String routeHandleClass;



    @Bean
    public ZkClient initZkClient() {
        return new ZkClient(zkAddress, zkTimeOut);
    }

    @Bean
    public LoadingCache<String, String> buildCache() {
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return null;
                    }
                });
    }

    @Bean
    public RouteHandle getRouteHandle() throws Exception {
       return (RouteHandle) Class.forName(routeHandleClass).getDeclaredConstructor().newInstance();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
