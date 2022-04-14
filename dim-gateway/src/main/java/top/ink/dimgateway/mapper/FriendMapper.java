package top.ink.dimgateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.ink.dimgateway.entity.user.Friend;

import java.util.List;

/**
 * desc: 朋友Mapper
 *
 * @author ink
 * date:2022-03-14 11:10
 */
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {

    /**
     * Description: 获取lid的好友
     * @param lid
     * @return java.util.List<java.lang.String>
     * Author: ink
     * Date: 2022/3/14
    */
    List<String> findFriendsByLid(String lid);
}
