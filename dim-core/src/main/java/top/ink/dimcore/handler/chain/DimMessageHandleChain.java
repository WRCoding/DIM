package top.ink.dimcore.handler.chain;

import io.netty.channel.ChannelHandlerContext;
import top.ink.dimcore.entity.message.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * desc: DIM消息处理链
 *
 * @author ink
 * date:2022-04-14 21:43
 */
public class DimMessageHandleChain {
    private List<DimMessageHandle> dimMessageHandleList = new LinkedList<>();

    public void addDimMessageHandle(DimMessageHandle dimMessageHandle) {
        dimMessageHandleList.add(dimMessageHandle);
    }

    public void handleMessage(ChannelHandlerContext ctx, Message message){
        for (DimMessageHandle dimMessageHandle : dimMessageHandleList) {
            if (dimMessageHandle.handle(ctx, message)){
                break;
            }
        }
    }
}
