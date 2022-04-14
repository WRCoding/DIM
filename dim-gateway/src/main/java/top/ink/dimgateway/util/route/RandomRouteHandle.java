package top.ink.dimgateway.util.route;

import top.ink.dimgateway.entity.service.ServiceInfo;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * desc: 随机获取服务器节点
 *
 * @author ink
 * date:2022-04-05 10:24
 */
public class RandomRouteHandle implements RouteHandle{


    @Override
    public ServiceInfo routeServe(List<String> allService) throws RuntimeException {
        if (allService.size() == 0){
            throw new RuntimeException("没有可用的服务器");
        }
        int index = ThreadLocalRandom.current().nextInt(allService.size());
        String service = allService.get(index);
        return ServiceInfo.parseService(service);
    }
}
