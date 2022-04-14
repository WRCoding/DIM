package top.ink.dimgateway.entity;

import lombok.Data;
import top.ink.dimgateway.entity.dto.UserDTO;
import top.ink.dimgateway.entity.service.ServiceInfo;

/**
 * desc: 登录响应
 *
 * @author ink
 * date:2022-04-05 10:35
 */
@Data
public class LoginRes {
    private UserDTO userDTO;
    private ServiceInfo serviceInfo;

    public LoginRes(UserDTO userDTO, ServiceInfo serviceInfo) {
        this.userDTO = userDTO;
        this.serviceInfo = serviceInfo;
    }
}
