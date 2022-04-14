package top.ink.dimgateway.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.ink.dimgateway.entity.common.BaseEntity;


/**
 * @author 林北
 * @description 群组信息
 * @date 2021-12-04 11:02
 */
@Data
@TableName("ink_group_info")
public class GroupInfo extends BaseEntity {

    private String groupId;

    private String groupName;

    /** 群主 */
    private String groupOwner;

    /** 群聊创建人,跟群主概念不同,群主可变,创建人不可变,最开始群主是创建人 */
    private String groupCreator;

}
