package top.ink.dimgateway.entity.dto;

import lombok.Data;

/**
 * desc: 朋友dto
 *
 * @author ink
 * date:2022-03-14 11:07
 */
@Data
public class FriendDTO {
    /** 添加人Lid */
    private String lid;
    /** 被添加人Lid */
    private String friendLid;
}
