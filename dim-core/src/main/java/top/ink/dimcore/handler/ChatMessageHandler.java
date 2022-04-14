package top.ink.dimcore.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ink.dimcore.constant.MsgType;
import top.ink.dimcore.entity.message.chatmessage.ChatMessage;
import top.ink.dimcore.entity.message.chatmessage.TextMessage;
import top.ink.dimcore.entity.message.systemmessage.AckMessage;
import top.ink.dimcore.entity.service.ServiceInfo;
import top.ink.dimcore.server.producer.KafkaService;
import top.ink.dimcore.server.session.Session;
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
public class ChatMessageHandler extends SimpleChannelInboundHandler<ChatMessage> {

    @Resource
    private Session session;


    @Resource
    private KafkaService kafkaService;




    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage chatMessage) throws Exception {
        System.out.println("ChatMessageHandler:channelRead0");
        System.out.println(chatMessage.toString());
        switch (MsgType.getMsgType(chatMessage.getMsgType())) {
            case SINGLE:
                handlerSingle(chatMessage);
                break;
            case GROUP:
                handlerGroup(ctx,chatMessage);
                break;
            default:
                throw new UnsupportedOperationException(MsgType.getMsgType(chatMessage.getMsgType()) + "类型不支持");
        }
    }

    private void handlerSingle(ChatMessage chatMessage) {
        TextMessage textMessage = (TextMessage) chatMessage;
        String receiver = textMessage.getReceiver();
        String sender = textMessage.getSender();
        log.info("收到{}, 发送给{}的消息: {}",sender,receiver,textMessage);
        Channel channel = session.getSession(receiver);
        if (channel != null){
            channel.writeAndFlush(textMessage);
        }else{
            kafkaService.handleMessage(chatMessage);
        }
    }



    private void handlerGroup(ChannelHandlerContext ctx, ChatMessage chatMessage) {

    }


}
