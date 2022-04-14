package top.ink.dimgateway.util.route;

import top.ink.dimgateway.entity.service.ServiceInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * desc: 轮询获取服务器节点
 *
 * @author ink
 * date:2022-04-05 10:07
 */
public class LoopRouteHandle implements RouteHandle{

    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public ServiceInfo routeServe(List<String> allService) throws RuntimeException {
        if (allService.size() == 0){
            throw new RuntimeException("没有可用的服务器");
        }
        int index = count.incrementAndGet() % allService.size();
        String service = allService.get(index);
        return ServiceInfo.parseService(service);
    }
}
