package top.ink.dimcore.entity.message.systemmessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ink.dimcore.constant.MsgType;

/**
 * desc: NotifyMessage
 *
 * @author ink
 * date:2022-04-01 22:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NotifyMessage extends SystemMessage {

    private String content;

    public NotifyMessage(String content) {
        super();
        String[] contents = content.split(":");
        setMsgType();
        this.setSender(contents[2]);
        this.setReceiver(contents[1]);
        this.content = contents[0];
    }

    @Override
    public void setMsgType() {
        this.msgType= MsgType.NOTIFY.type();
    }

    @Override
    public String toString() {
        return "NotifyMessage{" +
                "msgSeq=" + msgSeq +
                ", type=" + type +
                ", msgType=" + msgType +
                ", contentType=" + contentType +
                ", content='" + content + '\'' +
                '}';
    }
}
