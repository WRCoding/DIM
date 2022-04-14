package top.ink.dimgateway.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import top.ink.dimgateway.entity.common.BaseEntity;

/**
 * desc: 朋友
 *
 * @author ink
 * date:2022-03-14 11:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("chat_friend")
public class Friend extends BaseEntity {
    /** 添加人Lid */
    private String lid;
    /** 被添加人Lid */
    private String friendLid;
    /** 是否同意 */
    private Integer isAgree = 1;

    public static Friend copy(Object source){
        Friend friend = new Friend();
        BeanUtils.copyProperties(source,friend);
        return friend;
    }
}
