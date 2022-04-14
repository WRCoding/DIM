package top.ink.dimgateway.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.ink.dimgateway.entity.common.BaseEntity;


/**
 * @author 林北
 * @description 群组和群成员映射表
 * @date 2021-12-11 11:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ink_group_member")
public class GroupMember extends BaseEntity {

    /** 群组ID */
    private String groupId;

    /** 群成员id */
    private String groupMember;
}
