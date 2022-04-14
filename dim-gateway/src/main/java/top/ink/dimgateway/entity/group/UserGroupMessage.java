package top.ink.dimgateway.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * desc: 群聊信息用户映射
 * author:ink
 * date:2022-01-16 10:22
 */
@Data
@TableName("ink_user_group_message")
public class UserGroupMessage {

    /** 用户ID */
    private String lid;
    /** 群聊ID */
    private String groupId;
    /** 消息ID */
    private Long msgSeq;

    public static UserGroupMessage instance(String lid, String groupId, Long msgSeq){
        return new UserGroupMessage(lid, groupId, msgSeq);
    }

    private UserGroupMessage(String lid, String groupId, Long msgSeq) {
        this.lid = lid;
        this.groupId = groupId;
        this.msgSeq = msgSeq;
    }
}
