package top.ink.dimcore.entity.message.systemmessage;

import lombok.EqualsAndHashCode;
import top.ink.dimcore.constant.ContentType;
import top.ink.dimcore.constant.MsgType;
import top.ink.dimcore.constant.Type;
import top.ink.dimcore.entity.message.Message;


import java.util.HashMap;
import java.util.Map;

/**
 * desc: SystemMessage
 *
 * @author ink
 * date:2022-02-27 19:59
 */
@EqualsAndHashCode(callSuper = true)
public abstract class SystemMessage extends Message {

    private static final Map<Byte, Class<? extends SystemMessage>> SYSTEM_MESSAGE_CLASSES = new HashMap<>();

    public SystemMessage() {
        this.setContentType();
        this.setType();
    }

    static {
        SYSTEM_MESSAGE_CLASSES.put(MsgType.QUIT.type(), QuitMessage.class);
        SYSTEM_MESSAGE_CLASSES.put(MsgType.INIT.type(), InitMessage.class);
        SYSTEM_MESSAGE_CLASSES.put(MsgType.ACK.type(), AckMessage.class);
        SYSTEM_MESSAGE_CLASSES.put(MsgType.HEARTBEAT.type(), HeartBeatMessage.class);
    }

    public static Class<? extends SystemMessage> getSystemMessageClass(byte messageType) {
        return SYSTEM_MESSAGE_CLASSES.get(messageType);
    }

    @Override
    public void setType() {
        super.type = Type.SYSTEM.type();
    }

    @Override
    public byte getType() {
        return Type.SYSTEM.type();
    }

    @Override
    public byte getContentType() {
        return ContentType.NULL.type();
    }

    @Override
    public void setContentType() {
        this.contentType = (ContentType.NULL.type());
    }
}
