package top.ink.dimgateway.util;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * desc: 监听zookeeper
 *
 * @author ink
 * date:2022-04-04 21:25
 */
@Slf4j
public class ZkListener implements Runnable{


    private ZkUtil zkUtil;

    public ZkListener() {
        this.zkUtil = SpringBeanFactory.getBean(ZkUtil.class);
    }

    @Override
    public void run() {
        log.info("---zk监听启动---");
        zkUtil.subscribeEvent();
    }
}
