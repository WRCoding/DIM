package top.ink.dimgateway.cache;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ink.dimgateway.util.ZkUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * desc: 服务节点缓存(本地缓存)
 *
 * @author ink
 * date:2022-04-04 22:59
 */
@Slf4j
@Component
public class ServerCache {

    @Resource
    private LoadingCache<String,String> cache;

    @Resource
    private ZkUtil zkUtil;

    /**
     * Description: 添加服务器节点到缓存中
     * @param key
     * @param key
     * return void
     * Author: ink
     * Date: 2022/4/4
    */
    public void addServer(String key){
        cache.put(key, key);
    }

    /**
     * Description: 批量添加服务器节点到缓存中
     * @param map
     * return void
     * Author: ink
     * Date: 2022/4/4
    */
    public void addServerBatch(Map<String,String> map){
        cache.putAll(map);
    }

    /**
     * Description: 获取所以服务器节点
     * @return java.util.List<java.lang.String>
     * Author: ink
     * Date: 2022/4/4
    */
    public List<String> getAllServer(){
        if (cache.size() == 0){
            addServerBatch(zkUtil.getAllServer());
        }
        return new ArrayList<>(cache.asMap().keySet());
    }

    /**
     * Description: 刷新本地缓存,先删除缓存再更新值
     * Author: ink
     * Date: 2022/4/4
    */
    public void refreshCache(){
        cache.invalidateAll();
        addServerBatch(zkUtil.getAllServer());
    }

    public void updateCache(List<String> allService){
        cache.invalidateAll();
        allService.forEach(this::addServer);
    }
}
