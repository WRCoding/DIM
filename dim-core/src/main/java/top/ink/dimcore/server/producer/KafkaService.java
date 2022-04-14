package top.ink.dimcore.server.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import top.ink.dimcore.entity.message.Message;
import top.ink.dimcore.entity.message.chatmessage.ChatMessage;
import top.ink.dimcore.entity.message.chatmessage.TextMessage;
import top.ink.dimcore.entity.message.systemmessage.AckMessage;
import top.ink.dimcore.entity.service.ServiceInfo;
import top.ink.dimcore.server.session.Session;
import top.ink.dimcore.server.session.cache.SessionCache;
import top.ink.dimcore.util.RedisUtil;

import javax.annotation.Resource;

/**
 * desc: Kafka操作类
 *
 * @author ink
 * date:2022-04-06 21:48
 */
@Service
@Slf4j
public class KafkaService {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private Session session;

    @Resource
    private RedisUtil redisUtil;

    private final static String ROUTE_PREFIX = "dim-route:";


    public void handleMessage(Message message){
        String receiver = message.getReceiver();
        if (redisUtil.hasKey(ROUTE_PREFIX + receiver)){
            ServiceInfo serviceInfo = JSON.parseObject(redisUtil.valueGet(ROUTE_PREFIX + receiver), ServiceInfo.class);
            forwardingMessage(serviceInfo, message);
        }else{
            sendMsg2OffLine(message);
        }
    }

    public void sendMsg2OffLine(Message message) {
        kafkaTemplate.send("chat-offline", JSON.toJSONString(message)).addCallback(success -> {
            log.info("成功发送消息到,topic:{} ,partition:{} ,offset:{}", success.getRecordMetadata().topic(),
                    success.getRecordMetadata().partition(), success.getRecordMetadata().offset());
            Channel channel = this.session.getSession(message.getSender());
            channel.writeAndFlush(AckMessage.ackMessage(message));
        }, error -> log.error("消息发送失败: {}", error.getMessage()));
    }

    /**
     * Description: 转发消息到其他服务器
     * @param serviceInfo
     * @param message
     * return void
     * Author: ink
     * Date: 2022/4/6
    */
    public void forwardingMessage(ServiceInfo serviceInfo, Message message) {
        String msg = JSON.toJSONString(message, SerializerFeature.WriteClassName);
        kafkaTemplate.send(serviceInfo.getTopicName(), msg).addCallback(success -> {
            log.info("成功转发消息到,topic:{} ,partition:{} ,offset:{}", success.getRecordMetadata().topic(),
                    success.getRecordMetadata().partition(), success.getRecordMetadata().offset());
        }, error -> log.error("消息发送失败: {}", error.getMessage()));
    }

    @KafkaListener(topics = {"#{register.topicName}"})
    public void consumer(ConsumerRecord<String, String> record){
        log.info("receive: topic:{}, partition:{}, value:{},offset:{}", record.topic(), record.partition(), record.value(),
                record.offset());
        Message message = JSON.parseObject(record.value(), Message.class);
        String receiver = message.getReceiver();
        Channel channel = this.session.getSession(receiver);
        channel.writeAndFlush(message);
//        if (message != null) {
//            log.info("消费Kafka: {}", message);
//            messageMapper.insert(message);
//        }
    }
}
