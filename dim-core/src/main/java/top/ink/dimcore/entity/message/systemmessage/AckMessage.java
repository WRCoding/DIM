package top.ink.dimcore.entity.message.systemmessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import top.ink.dimcore.constant.MsgType;
import top.ink.dimcore.entity.message.Message;
import top.ink.dimcore.entity.message.chatmessage.ChatMessage;

/**
 * desc: AckMessage
 *
 * @author ink
 * date:2022-02-27 20:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class AckMessage extends SystemMessage{

    public AckMessage() {
        super();
        this.setMsgType();
    }

    @Override
    public void setMsgType() {
        this.msgType = MsgType.ACK.type();
    }

    @Override
    public String toString() {
        return "AckMessage{" +
                "msgSeq=" + msgSeq +
                ", type=" + type +
                ", msgType=" + msgType +
                ", contentType=" + contentType +
                '}';
    }

    public static < T extends Message>  AckMessage ackMessage(T message){
        AckMessage ack = new AckMessage();
        ack.setReceiver(message.getSender());
        ack.setSender(message.getReceiver());
        ack.setMsgSeq(message.getMsgSeq());
        log.info("ack : {}", ack);
        return ack;
    }
}
