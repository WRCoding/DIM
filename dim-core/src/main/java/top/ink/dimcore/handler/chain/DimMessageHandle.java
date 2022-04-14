package top.ink.dimcore.handler.chain;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.ink.dimcore.entity.message.Message;
import top.ink.dimcore.entity.message.chatmessage.ChatMessage;
import top.ink.dimcore.server.producer.KafkaService;
import top.ink.dimcore.server.session.Session;
import top.ink.dimcore.util.SpringBeanFactory;

import javax.annotation.Resource;

/**
 * desc: DIM消息处理接口
 *
 * @author ink
 * date:2022-04-14 21:44
 */
public interface DimMessageHandle {

    

    /**
     * Description: 处理消息
     * @param ctx
     * @param message
     * @return boolean
     * Author: ink
     * Date: 2022/4/14
    */
    boolean handle(ChannelHandlerContext ctx, Message message);

    /**
     * Description: 判断消息类型
     * @param message
     * @return boolean
     * Author: ink
     * Date: 2022/4/14
    */
    boolean checkMessageType(Message message);


    /**
     * Description: 发送消息
     * @param message
     * return void
     * Author: ink
     * Date: 2022/4/14
    */
    default void sendMessage(Message message){
        Session session = SpringBeanFactory.getBean(Session.class);
        KafkaService kafkaService = SpringBeanFactory.getBean(KafkaService.class);
        String receiver = message.getReceiver();
        Channel channel = session.getSession(receiver);
        if (channel != null){
            channel.writeAndFlush(message);
        }else{
            kafkaService.handleMessage(message);
        }
    }


}
