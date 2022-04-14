package top.ink.dimcore.entity.message.systemmessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ink.dimcore.constant.MsgType;

/**
 * desc: InitMessage
 *
 * @author ink
 * date:2022-02-27 20:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InitMessage extends SystemMessage{

    public InitMessage() {
        super();
        this.setMsgType();
    }

    @Override
    public void setMsgType() {
        this.msgType = MsgType.INIT.type();
    }

}
