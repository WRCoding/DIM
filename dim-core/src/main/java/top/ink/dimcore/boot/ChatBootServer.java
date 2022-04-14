package top.ink.dimcore.boot;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.ink.dimcore.handler.MessageChannelInit;

import javax.annotation.Resource;

/**
 * desc: 服务启动器
 *
 * @author ink
 * date:2022-02-28 22:08
 */
@Component
@Slf4j
public class ChatBootServer {

    @Value("${dim.server.netty.port}")
    private Integer nettyPort;

    @Resource
    private MessageChannelInit messageChannelInit;

    private static final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static final NioEventLoopGroup workGroup = new NioEventLoopGroup();

    public void init() throws InterruptedException {
        ChannelFuture channelFuture = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(messageChannelInit).bind(nettyPort).sync();
        channelFuture.addListener( future -> {
            log.info("服务器启动成功！");
        });
    }

}
