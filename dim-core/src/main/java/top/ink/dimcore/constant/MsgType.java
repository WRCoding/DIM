package top.ink.dimcore.constant;

/**
 * desc: MsgType-Enum
 *
 * @author ink
 * date:2022-02-27 20:34
 */
public enum MsgType {

    /** 单聊 */
    SINGLE(0),

    /** 群聊 */
    GROUP(2),

    /** 退出 */
    QUIT(4),

    /** 初始化 */
    INIT(6),

    /** ACK */
    ACK(8),

    /** 通知 */
    NOTIFY(10),

    HEARTBEAT(12),

    /** 错误 */
    ERROR(-1);

    private byte type;

    MsgType(int type) {
        this.type = (byte) type;
    }

    public byte type() {
        return type;
    }

    public static MsgType getMsgType(byte type){
        for (MsgType msgType : MsgType.values()) {
            if (msgType.type == type){
                return msgType;
            }
        }
        return null;
    }
}
