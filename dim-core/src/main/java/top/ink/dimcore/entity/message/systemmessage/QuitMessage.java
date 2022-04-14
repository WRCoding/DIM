package top.ink.dimcore.entity.message.systemmessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ink.dimcore.constant.MsgType;

/**
 * desc: QuitMessage
 *
 * @author ink
 * date:2022-02-27 20:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuitMessage extends SystemMessage{

    public QuitMessage() {
        super();
        this.setMsgType();
    }

    @Override
    public void setMsgType() {
        this.msgType = MsgType.QUIT.type();
    }

}
