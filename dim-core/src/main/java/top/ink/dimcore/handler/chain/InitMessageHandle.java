package top.ink.dimcore.handler.chain;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ink.dimcore.constant.MsgType;
import top.ink.dimcore.entity.message.Message;
import top.ink.dimcore.server.session.Session;
import top.ink.dimcore.util.SpringBeanFactory;

import javax.annotation.Resource;

/**
 * desc: 初始化信息处理类
 *
 * @author ink
 * date:2022-04-14 22:26
 */

@Slf4j
public class InitMessageHandle implements DimMessageHandle{



    @Override
    public boolean handle(ChannelHandlerContext ctx, Message message) {
        if (checkMessageType(message)) {
            Session session = SpringBeanFactory.getBean(Session.class);
            Channel channel = ctx.channel();
            session.register(channel, message);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean checkMessageType(Message message) {
        return message.getMsgType() == MsgType.INIT.type();
    }
}
