package top.ink.dimcore.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ink.dimcore.constant.MsgType;
import top.ink.dimcore.entity.message.Message;
import top.ink.dimcore.entity.message.systemmessage.*;
import top.ink.dimcore.entity.service.ServiceInfo;
import top.ink.dimcore.server.producer.KafkaService;
import top.ink.dimcore.server.session.Session;
import top.ink.dimcore.server.session.cache.SessionCache;
import top.ink.dimcore.util.RedisUtil;


import javax.annotation.Resource;

/**
 * desc: 聊天消息处理
 *
 * @author ink
 * date:2022-03-06 10:12
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class SystemMessageHandler extends SimpleChannelInboundHandler<SystemMessage> {

    @Resource
    private Session session;

    @Resource
    private KafkaService kafkaService;



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SystemMessage message) throws Exception {
        switch (MsgType.getMsgType(message.getMsgType())) {
            case INIT:
                handlerInit(ctx, message);
                break;
            case ACK:
                handlerAck(message);
                break;
            case HEARTBEAT:
                handlerHeartbeat(ctx, message);
                break;
            default:
                throw new UnsupportedOperationException(MsgType.getMsgType(message.getMsgType()) + "类型不支持");
        }
    }

    private void handlerHeartbeat(ChannelHandlerContext ctx, Message message) {
        log.info("收到心跳消息: {}", message);
        HeartBeatMessage heartBeatMessage = HeartBeatMessage.beatMessage(message);
        ctx.channel().writeAndFlush(heartBeatMessage).addListener(future -> {
           if (future.isSuccess()){{
               SessionCache.setIdleTime(System.currentTimeMillis(), ctx.channel());
           }}
        });
    }

    private void handlerInit(ChannelHandlerContext ctx, Message message) {
        Channel channel = ctx.channel();
        session.register(channel, message);
    }

    private void handlerAck(Message message) {
        String receiver = message.getReceiver();
        Channel channel = this.session.getSession(receiver);
        log.info("收到ack消息: {}", message);
        if (channel != null){
            channel.writeAndFlush(message);
        }else{
            kafkaService.handleMessage(message);
        }
    }



}
