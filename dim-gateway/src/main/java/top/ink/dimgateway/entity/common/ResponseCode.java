package top.ink.dimgateway.entity.common;

/**
 * @author 林北
 * @Description 响应码
 * @date 2021-05-23 10:14
 */
public enum ResponseCode {
    /** 成功 */
    SUCCESS(200,"成功"),
    /** 服务器错误 */
    ERROR(500,"服务器出小差啦,请联系服务员"),
    /** 参数为空 */
    PARAM_EMPTY(7001,"参数为空"),
    /** 参数校验失败 */
    PARAM_FAIL(7002,"参数校验失败");


    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer Code() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
