package top.ink.dimgateway.entity.message;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ink.dimgateway.entity.common.BaseEntity;

/**
 * desc: 离线消息实体
 *
 * @author ink
 * date:2022-03-30 22:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("chat_offline_message")
public class Message extends BaseEntity {

    private Long msgSeq;
    private String sender;
    private String receiver;
    protected byte type;
    protected byte msgType;
    protected byte contentType;
    private String content;

    @Override
    public String toString() {
        return "Message{" +
                "msgSeq=" + msgSeq +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", type=" + type +
                ", msgType=" + msgType +
                ", contentType=" + contentType +
                ", content='" + content + '\'' +
                '}';
    }
}
