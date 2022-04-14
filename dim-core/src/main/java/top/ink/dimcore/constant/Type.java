package top.ink.dimcore.constant;

/**
 * desc: Type-Enum
 *
 * @author ink
 * date:2022-02-27 20:38
 */
public enum Type {

    /** 聊天 */
    CHAT(0),

    /** 系统 */
    SYSTEM(2),

    ERROR(-1);

    private final byte type;

    Type(int type) {
        this.type = (byte) type;
    }

    public byte type() {
        return type;
    }

    public static Type getType(byte type){
        for (Type value : Type.values()) {
            if (value.type == type){
                return value;
            }
        }
        return ERROR;
    }
}
