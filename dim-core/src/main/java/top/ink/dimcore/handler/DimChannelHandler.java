package top.ink.dimcore.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import top.ink.dimcore.config.BeanConfig;
import top.ink.dimcore.entity.message.systemmessage.InitMessage;
import top.ink.dimcore.server.session.cache.SessionCache;
import top.ink.dimcore.util.SpringBeanFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * desc: message入站处理器
 *
 * @author ink
 * date:2022-03-06 09:37
 */
@Slf4j
@ChannelHandler.Sharable
public class DimChannelHandler extends ChannelInboundHandlerAdapter {

    private static final Long SECOND_2_MILLIS = 60 * 1000L;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        Integer nrTimeout = SpringBeanFactory.getBean(BeanConfig.class).getNrTimeout();
        // 触发了读空闲事件
        if (event.state() == IdleState.READER_IDLE) {
            Channel channel = ctx.channel();
            Long idleTime = SessionCache.getIdleTime(channel);
            long currentTimeMillis = System.currentTimeMillis();
            if (idleTime != null && currentTimeMillis - idleTime > nrTimeout * SECOND_2_MILLIS) {
                offLineAndClose(channel,true);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("ChatMessageHandler channelActive: {}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        offLineAndClose(ctx.channel(),false);
    }

    private void offLineAndClose(Channel channel, boolean idle) {
        SessionCache sessionCache = SpringBeanFactory.getBean(SessionCache.class);
        String user = sessionCache.getUser(channel);
        if (user != null) {
            sessionCache.delete(user);
            InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
            String address = inetSocketAddress.getHostString() + ":" + inetSocketAddress.getPort();
            if (!idle) {
                log.info("用户 [{}] 下线, 地址: {}", user, address);
            }else{
                log.info("用户 [{}] 超过30分钟未活动, 地址: {}", user, address);
            }
            channel.close();
        }
    }
}
