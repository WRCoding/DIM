package top.ink.dimcore.server.session.cache;

import com.google.common.cache.LoadingCache;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ink.dimcore.util.RedisUtil;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * desc: 缓存连接到这台服务器的会话(本地会话)
 * @author ink
 * date:2022-04-05 14:53
 */
@Slf4j
@Component
public class SessionCache {

    @Resource
    private LoadingCache<String, Channel> cache;

    @Resource
    private RedisUtil redisUtil;

    private static final AttributeKey<Long> CHANNEL_ATTRIBUTE_KEY = AttributeKey.valueOf("idleTime");

    private final static String ROUTE_PREFIX = "dim-route:";

    public static void setIdleTime(Long idleTime, Channel channel){
        channel.attr(CHANNEL_ATTRIBUTE_KEY).set(idleTime);
    }

    public static Long getIdleTime(Channel channel){
        return channel.attr(CHANNEL_ATTRIBUTE_KEY).get();
    }

    public void addCache(String key, Channel channel){
        cache.put(key, channel);
    }

    public Channel getIfPresent(String key){
        return cache.getIfPresent(key);
    }

    public void delete(String key){
        cache.invalidate(key);
        redisUtil.delete(ROUTE_PREFIX + key);
    }

    public String getUser(Channel channel){
        Set<Map.Entry<String, Channel>> entrySet = cache.asMap().entrySet();
        for (Map.Entry<String, Channel> channelEntry : entrySet) {
            if (channelEntry.getValue().equals(channel)){
                return channelEntry.getKey();
            }
        }
        return null;
    }
}
