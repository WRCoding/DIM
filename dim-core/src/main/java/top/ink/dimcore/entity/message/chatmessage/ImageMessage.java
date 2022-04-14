package top.ink.dimcore.entity.message.chatmessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ink.dimcore.constant.ContentType;

/**
 * desc: ImageMessage
 *
 * @author ink
 * date:2022-02-27 20:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageMessage extends ChatMessage{

    /** 图片路径 */
    private String content;

    public ImageMessage() {
        super();
        this.setContentType();
    }

    @Override
    public void setContentType() {
        this.contentType = ContentType.IMAGE.type();
    }

}
