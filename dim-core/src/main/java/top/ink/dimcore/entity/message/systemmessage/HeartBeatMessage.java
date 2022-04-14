package top.ink.dimcore.entity.message.systemmessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import top.ink.dimcore.constant.MsgType;
import top.ink.dimcore.entity.message.Message;

/**
 * desc: 心跳检测消息
 *
 * @author ink
 * date:2022-04-10 09:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class HeartBeatMessage extends SystemMessage{


    public HeartBeatMessage() {
        super();
        this.setMsgType();
    }

    @Override
    public void setMsgType() {
        this.msgType = MsgType.HEARTBEAT.type();
    }

    public static < T extends Message>  HeartBeatMessage beatMessage(T message){
        HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
        heartBeatMessage.setReceiver(message.getSender());
        heartBeatMessage.setSender(message.getReceiver());
        heartBeatMessage.setMsgSeq(System.currentTimeMillis());
        log.info("heartBeatMessage : {}", heartBeatMessage);
        return heartBeatMessage;
    }

    @Override
    public String toString() {
        return "HeartBeatMessage{" +
                "msgSeq=" + msgSeq +
                ", type=" + type +
                ", msgType=" + msgType +
                ", contentType=" + contentType +
                '}';
    }
}
