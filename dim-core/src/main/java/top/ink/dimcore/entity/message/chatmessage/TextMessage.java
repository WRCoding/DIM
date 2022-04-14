package top.ink.dimcore.entity.message.chatmessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ink.dimcore.constant.ContentType;

/**
 * desc: TextMessage
 *
 * @author ink
 * date:2022-02-27 20:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextMessage extends ChatMessage {

    /**
     * 内容
     */
    private String content;


    public TextMessage() {
        super();
        this.setContentType();
    }

    @Override
    public void setContentType() {
        this.contentType = ContentType.TEXT.type();
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "msgSeq='" + msgSeq + '\'' +
                ", type=" + type +
                ", msgType=" + msgType +
                ", contentType=" + contentType +
                ", content='" + content + '\'' +
                '}';
    }
}
