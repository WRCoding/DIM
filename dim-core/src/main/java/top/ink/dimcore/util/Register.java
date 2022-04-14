package top.ink.dimcore.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Arrays;
import java.util.Collections;


/**
 * desc: 注册Zookeeper线程
 *
 * @author ink
 * date:2022-04-04 15:52
 */
@Slf4j
public class Register implements Runnable{

    private String ip;
    /** tcp监听端口 */
    private Integer nettyPort;
    /** http端口 */
    private Integer httpPort;

    private String serverName;

    private ZkUtil zkUtil;

    private AdminClient adminClient;

    public String topicName;


    public Register(String ip, Integer nettyPort, Integer httpPort, String serverName) {
        this.ip = ip;
        this.nettyPort = nettyPort;
        this.httpPort = httpPort;
        this.serverName = serverName;
        this.topicName = ip.replaceAll("\\.", "-") + "-" + nettyPort + "-" + httpPort;
        this.zkUtil = SpringBeanFactory.getBean(ZkUtil.class);
        this.adminClient = SpringBeanFactory.getBean(AdminClient.class);
    }

    @Override
    public void run() {
        zkUtil.createRootNode();
        String path = zkUtil.getZkRootNode() + "/" + serverName + "@" + ip + ":" + nettyPort + ":" + httpPort;
        zkUtil.createNode(path);
        log.info("server register success, path: [{}]", path);
        NewTopic topic = new NewTopic(topicName, 3, (short) 3);
        adminClient.createTopics(Collections.singletonList(topic));
        log.info("topic create success, path: [{}]", topicName);
    }
}
