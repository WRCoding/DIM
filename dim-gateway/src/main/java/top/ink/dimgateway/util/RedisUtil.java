package top.ink.dimgateway.util;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * desc: Redis工具类
 *
 * @author ink
 * date:2022-03-06 09:52
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    private ZSetOperations zSetOperations;
    private HashOperations hashOperations ;
    private ListOperations listOperations;
    private SetOperations setOperations;
    private ValueOperations valueOperations;

    /**
     * 只执行一次，在构造函数之后执行
     */
    @PostConstruct
    public void init(){
        zSetOperations = redisTemplate.opsForZSet();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        valueOperations = redisTemplate.opsForValue();
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
    public boolean expire(String key, Long timeout, TimeUnit timeUnit){
        return redisTemplate.expire(key,timeout,timeUnit);
    }
    /**
     * @Description: 以下是Hash类型
     * @Param: [key, hashKey, value]
     * @return: void
     * @Author: 林北
     * @Date: 2020-02-22
     */
    public <HK,T> void hashPut(String key,HK hashKey,T value){
        hashOperations.put(key,hashKey,value);
    }
    public <HK,T> T hashGet(String key,HK hashKey){
        return (T) hashOperations.get(key, hashKey);
    }
    public <HK>  void hashDelete(String key,HK hashKey){
        hashOperations.delete(key,hashKey);
    }
    public <HK> boolean hashHasKey(String key,HK hashKey){
        return hashOperations.hasKey(key, hashKey);
    }
    public <HK,T> HashMap<HK,T> hashGetEntry(String key){
        return (HashMap<HK, T>) hashOperations.entries(key);
    }
    public <HK> void hashIncrement(String key,HK hashKey,long num){
        hashOperations.increment(key,hashKey,num);
    }
    /**
     * @Description: 以下是List类型
     * @Param: [key, collection]
     * @return: void
     * @Author: 林北
     * @Date: 2020-02-22
     */
    public <T> void listRightPushAll(String key, Collection<T> collection){
        listOperations.rightPushAll(key,collection);
    }
    public <T> void listPushRight(String key,T value){
        listOperations.rightPush(key, value);
    }
    public <T> void listLeftPushAll(String key,Collection<T>collection){
        listOperations.leftPushAll(key,collection);
    }
    public <T> void listPushLeft(String key,T value){
        listOperations.leftPush(key,value);
    }
    public Long size(String key){
        return listOperations.size(key);
    }
    public <T> List<T> listGetAll(String key){
        return listOperations.range(key, 0, size(key));
    }
    public <T> List<T> listGetRange(String key,Long start,Long end){
        return listOperations.range(key, start, end);
    }
    public <T> Long listRemove(String key, T value){
        return listOperations.remove(key,0,value);
    }

    /**
     * @Description: 以下是Value
     * @Param: [key, value]
     * @return: void
     * @Author: 林北
     * @Date: 2020-02-22
     */
    public <T> void valuePut(String key,T value){
        valueOperations.set(key,value);
    }

    public <T> void valuePutWithExpire(String key,T value,long timeout, TimeUnit unit){
        valueOperations.set(key,value,timeout,unit);
    }
    public <T> T valueGet(String key){
        return (T) valueOperations.get(key);
    }
    /**
     * @Description: 以下是zSet
     * @Param: [key, value, score]
     * @return: void
     * @Author: 林北
     * @Date: 2020-02-22
     */
    public <T> void zSetPut(String key,T value,Double score){
        zSetOperations.add(key,value,score);
    }
    public <T> void zSetDelete(String key,T value){
        zSetOperations.remove(key,value);
    }
    public <T> Double zSetGetScore(String key,T value){
        return zSetOperations.score(key,value);
    }
    public <T> Set<T> zSetReverseRange(String key , Long start, Long end){
        return zSetOperations.reverseRange(key,start,end);
    }
    public <T> Set<ZSetOperations.TypedTuple<T>> zSetReverseRangeWithScores(String key , Long start, Long end){
        return zSetOperations.reverseRangeWithScores(key,start,end);
    }
    public <T> void zSetAdd(String key,T value,Double num){
        zSetOperations.incrementScore(key,value,num);
    }
}
