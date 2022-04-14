package top.ink.dimcore.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ink.dimcore.protocol.MessageFrameDecoder;
import top.ink.dimcore.protocol.codec.MessageCodec;


import javax.annotation.Resource;

/**
 * desc: 消息处理器
 *
 * @author ink
 * date:2022-02-28 22:16
 */
@Component
@Slf4j
public class MessageChannelInit extends ChannelInitializer<NioSocketChannel> {

    @Resource
    private ChatMessageHandler chatMessageHandler;

    @Resource
    private MessageFrameDecoder messageFrameDecoder;

    @Resource
    private MessageCodec messageCodec;

    @Resource
    private SystemMessageHandler systemMessageHandler;

    LoggingHandler LOGGING_HANDLER = new LoggingHandler();

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        nioSocketChannel.pipeline().addLast(new MessageFrameDecoder());
        nioSocketChannel.pipeline().addLast(new MessageCodec());
        nioSocketChannel.pipeline().addLast(LOGGING_HANDLER);
        nioSocketChannel.pipeline().addLast(new IdleStateHandler(11, 0, 0));
        nioSocketChannel.pipeline().addLast(new DimChannelHandler());
        nioSocketChannel.pipeline().addLast(chatMessageHandler);
        nioSocketChannel.pipeline().addLast(systemMessageHandler);
    }
}
