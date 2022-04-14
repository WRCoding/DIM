package top.ink.dimcore.server.session;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.ink.dimcore.entity.message.Message;
import top.ink.dimcore.entity.message.systemmessage.InitMessage;
import top.ink.dimcore.entity.message.systemmessage.NotifyMessage;
import top.ink.dimcore.server.session.cache.SessionCache;
import top.ink.dimcore.util.RedisUtil;


import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


/**
 * desc: 会话管理
 *
 * @author ink
 * date:2022-03-06 16:07
 */
@Slf4j
@Service
public class ChatSession implements Session {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SessionCache sessionCache;


    @Override
    public void register(Channel channel, Message message) {
        NioSocketChannel sc = (NioSocketChannel) channel;
        String[] ids = channel.id().asLongText().split("-");
        StringBuffer channelId = new StringBuffer(ids[0]);
        channelId.append("-").append(ids[3]).append("-").append(ids[4]);
        InetSocketAddress address = sc.remoteAddress();
        log.info("用户: {}, ip: {}, port:{} 登录, channelId: {}", message.getSender(), address.getHostString(), address.getPort(), channelId);
        sessionCache.addCache(message.getSender(), channel);
        SessionCache.setIdleTime(System.currentTimeMillis(), channel);
    }

    @Override
    public void unRegister(String lid) {
        sessionCache.delete(lid);
    }

    @Override
    public Channel getSession(String lid){
        return sessionCache.getIfPresent(lid);
    }

    @Override
    public boolean exist(String lid) {
        return redisUtil.hasKey(lid);
    }


//    @Override
//    public void notify(String lid, String content) {
//        Channel channel = getSession(lid);
//        NotifyMessage notifyMessage = new NotifyMessage(content);
//        channel.writeAndFlush(notifyMessage);
//    }
}
