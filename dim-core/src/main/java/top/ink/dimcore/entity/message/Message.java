package top.ink.dimcore.entity.message;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * desc: message
 * @author ink
 * date:2022-02-22 23:21
 */
@EqualsAndHashCode(callSuper = false)
@Data
public abstract class Message {
    /** 序列号 */
    protected Long msgSeq;

    /** 类型type：Chat,System */
    protected byte type;

    /** 消息类型 */
    protected byte msgType;

    /** 内容类型 */
    protected byte contentType;

    /**
     * 发送方
     */
    private String sender;

    /**
     * 接收方
     */
    private String receiver;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Description: 设置seqId
     * @param msgSeq
     * return void
     * Author: ink
     * Date: 2022/3/6
    */
    public void setMsgSeq(Long msgSeq){
        this.msgSeq = msgSeq;
    }

    /**
     * Description: 返回seqId
     * @return byte
     * Author: ink
     * Date: 2022/3/6
     */
    public Long getMsgSeq(){
        return this.msgSeq;
    }

    /**
     * Description: 返回类型
     * Author: ink
     * Date: 2022/2/27
    */
    public abstract void setType();


    /**
     * Description: 返回类型
     * @return byte
     * Author: ink
     * Date: 2022/2/27
    */
    public byte getType(){
        return type;
    }

    /**
     * Description: 设置消息类型
     * @return byte
     * Author: ink
     * Date: 2022/2/27
     */
    public abstract void setMsgType();

    /**
     * Description: 返回消息类型
     * @return byte
     * Author: ink
     * Date: 2022/2/27
    */
    public byte getMsgType(){
        return msgType;
    }

    /**
     * Description: 设置内容类型
     * @return byte
     * Author: ink
     * Date: 2022/2/27
     */
    public abstract void setContentType();

    /**
     * Description: 返回内容类型
     * @return byte
     * Author: ink
     * Date: 2022/2/27
     */
    public byte getContentType(){
        return contentType;
    }



}
