package top.ink.dimcore.config;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.core.KafkaAdmin;
import top.ink.dimcore.util.Register;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${dim.server.netty.port}")
    private Integer nettyPort;

    @Value("${server.port}")
    private Integer httpPort;

    @Value("${dim.server.name}")
    private String serverName;

    @Value("${message.serializer}")
    private String serializer;

    @Value("${dim.nr.timeout}")
    private Integer nrTimeout;

    public Integer getNrTimeout() {
        return nrTimeout;
    }

    public String getSerializer() {
        return serializer;
    }

    @Bean
    public ZkClient initZkClient() {
        return new ZkClient(zkAddress, zkTimeOut);
    }

    @Bean
    public LoadingCache<String, Channel> buildCache() {
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<String, Channel>() {
                    @Override
                    public Channel load(String s) throws Exception {
                        return null;
                    }
                });
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public AdminClient adminClient(){
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        KafkaAdmin kafkaAdmin = new KafkaAdmin(props);
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Bean
    @DependsOn("SpringBeanFactory")
    public Register register()  {
        try {
            log.info("register 被创建啦！！");
            String address  = InetAddress.getLocalHost().getHostAddress();
            return new Register(address, nettyPort, httpPort, serverName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
