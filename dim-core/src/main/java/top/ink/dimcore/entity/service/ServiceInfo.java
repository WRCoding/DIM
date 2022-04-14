package top.ink.dimcore.entity.service;

import lombok.Data;

/**
 * desc: 服务器节点类
 *
 * @author ink
 * date:2022-04-05 10:10
 */
@Data
public class ServiceInfo {
    private String topicName;
    private String ip;
    private Integer nettyPort;
    private Integer httpPort;


    public ServiceInfo(String ip, Integer nettyPort, Integer httpPort) {
        this.ip = ip;
        this.nettyPort = nettyPort;
        this.httpPort = httpPort;
        this.topicName =  ip.replaceAll("\\.", "-") + "-" + nettyPort + "-" + httpPort;
    }

    public static ServiceInfo parseService(String service){
        //"/server@" + ip + ":" + nettyPort + ":" + httpPort
        String[] info = service.split("@")[1].split(":");
        return new ServiceInfo(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
    }
}
