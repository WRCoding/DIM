package top.ink.dimgateway.entity.common;

import lombok.Data;

/**
 * @author 林北
 * @description 响应实体
 * @date 2021-08-08 10:11
 */
@Data
public class Response<T> {

    private String message;
    private Integer code;
    private T data;

    public Response() {
    }

    public Response(ResponseCode responseCode) {
        this.code = responseCode.Code();
        this.message = responseCode.getMessage();
    }

    public Response(ResponseCode responseCode, T data) {
        this(responseCode);
        this.data = data;
    }

    public Response(Integer code , String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> success(){
        return new Response<>(ResponseCode.SUCCESS,null);
    }

    public static <T> Response<T> success(T data){
        return new Response<>(ResponseCode.SUCCESS,data);
    }

    public static <T> Response<T> error(){
        return new Response<>(ResponseCode.ERROR,null);
    }

    public static <T> Response<T> error(ResponseCode responseCode){
        return new Response<>(responseCode.Code(), responseCode.getMessage());
    }

    public static <T> Response<T> error(ResponseCode responseCode,String message){
        return new Response<>(responseCode.Code(),message);
    }

}
