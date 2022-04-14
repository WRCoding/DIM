package top.ink.dimcore.handler.chain;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ink.dimcore.constant.MsgType;
import top.ink.dimcore.entity.message.Message;
import top.ink.dimcore.entity.message.systemmessage.HeartBeatMessage;
import top.ink.dimcore.server.session.cache.SessionCache;

/**
 * desc: 心跳消息处理类
 *
 * @author ink
 * date:2022-04-14 22:42
 */

@Slf4j
public class HeartBeatMessageHandle implements DimMessageHandle{
    @Override
    public boolean handle(ChannelHandlerContext ctx, Message message) {
        if (checkMessageType(message)) {
            HeartBeatMessage heartBeatMessage = HeartBeatMessage.beatMessage(message);
            ctx.channel().writeAndFlush(heartBeatMessage).addListener(future -> {
                if (future.isSuccess()) {
                    {
                        SessionCache.setIdleTime(System.currentTimeMillis(), ctx.channel());
                    }
                }
            });
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean checkMessageType(Message message) {
        return message.getMsgType() == MsgType.HEARTBEAT.type();
    }
}
