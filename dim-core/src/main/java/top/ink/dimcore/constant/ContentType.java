package top.ink.dimcore.constant;

/**
 * desc: ContentType-Enum
 *
 * @author ink
 * date:2022-02-27 20:20
 */
public enum ContentType {

    /** 空内容类型 */
    NULL(0),

    /** 文本 */
    TEXT(1),

    /** 图片 */
    IMAGE(2),

    /** 文件 */
    FILE(3);

    private byte type;

    ContentType(int type) {
        this.type = (byte) type;
    }

    public byte type() {
        return type;
    }
}
