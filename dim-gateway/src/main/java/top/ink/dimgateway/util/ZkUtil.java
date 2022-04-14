package top.ink.dimgateway.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.ink.dimgateway.cache.ServerCache;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * desc: zookeeper工具类
 * @author ink
 * date:2022-04-04 21:21
 */
@Component
@Slf4j
public class ZkUtil {
    @Resource
    private ZkClient zkClient;

    @Resource
    private ServerCache serverCache;

    @Value("${dim.zookeeper.rootNode}")
    private String zkRootNode;

    public void subscribeEvent() {
        zkClient.subscribeChildChanges(zkRootNode, (parentPath, currentChildren) -> {
            log.info("subscribeChildChanges: [{}]", currentChildren.toString());

            //update local cache, delete and save.
            serverCache.updateCache(currentChildren);
        });
    }

    public Map<String,String> getAllServer(){
        List<String> children = zkClient.getChildren(zkRootNode);
        log.info("getAllServer: [{}]", JSON.toJSONString(children));
        return children.stream().collect(Collectors.toMap((key -> key),(key -> key)));
    }
}
