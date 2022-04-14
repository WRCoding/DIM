package top.ink.dimcore.server.session;

import io.netty.channel.Channel;
import org.springframework.stereotype.Service;
import top.ink.dimcore.entity.message.Message;

import java.util.concurrent.ExecutionException;


/**
 * desc: 会话管理
 *
 * @author ink
 * date:2022-03-06 16:01
 */
@Service
public interface Session {

    /**
     * Description: 注册会话
     * @param channel
     * @param lid
     * return void
     * Author: ink
     * Date: 2022/3/6
    */
    void register(Channel channel, Message lid);

    /**
     * Description: 取消会话
     * @param lid
     * return void
     * Author: ink
     * Date: 2022/3/6
    */
    void unRegister(String lid);

    /**
     * Description: 获取指定会话
     * @param lid
     * @return io.netty.channel.Channel
     * Author: ink
     * Date: 2022/3/20
    */
    Channel getSession(String lid);

    /**
     * Description: 会话是否存在
     * @param lid
     * @return boolean
     * Author: ink
     * Date: 2022/3/20
    */
    boolean exist(String lid);

    /**
     * Description: 提供发送消息的方法
     * @param lid
     * @param param
     * return void
     * Author: ink
     * Date: 2022/3/31
    */
//    void notify(String lid, String param);
}
