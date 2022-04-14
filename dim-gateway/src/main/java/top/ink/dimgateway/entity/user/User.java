package top.ink.dimgateway.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ink.dimgateway.entity.common.BaseEntity;

/**
 * desc: 用户信息
 *
 * @author ink
 * date:2022-03-13 10:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("chat_user_info")
public class User extends BaseEntity {

    private String lid;

    private String userName;

    private String password;

    private String avatar;

    private String description;

    private String background;

}