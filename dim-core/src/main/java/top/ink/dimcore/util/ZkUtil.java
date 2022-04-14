package top.ink.dimcore.util;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * desc: zookeeper工具类
 *
 * @author ink
 * date:2022-04-04 15:29
 */
@Component
@Slf4j
public class ZkUtil {

    @Resource
    private ZkClient zkClient;

    @Value("${dim.zookeeper.rootNode}")
    private String zkRootNode;


    public String getZkRootNode() {
        return zkRootNode;
    }

    /**
     * Description: 创建根节点,如果没有
     * Author: ink
     * Date: 2022/4/4
    */
    public void createRootNode(){
        if (!zkClient.exists(zkRootNode)){
            zkClient.createPersistent(zkRootNode);
        }
    }

    /**
     * Description: 创建节点(临时节点)
     * @param path
     * return void
     * Author: ink
     * Date: 2022/4/4
    */
    public void createNode(String path){
        zkClient.createEphemeral(path);
    }

}
