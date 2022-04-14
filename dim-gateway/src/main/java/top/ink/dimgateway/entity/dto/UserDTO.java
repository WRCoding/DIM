package top.ink.dimgateway.entity.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import top.ink.dimgateway.entity.service.ServiceInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 林北
 * @description 用户信息实体
 * @date 2021-08-07 16:24
 */

@Data
public class UserDTO {

    private String lid;

    private String userName;

    private String avatar;

    private String description;

    private String background;

    private Integer days;

    public static UserDTO copy(Object source){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(source,userDTO);
        return userDTO;
    }

    public static <T> List<UserDTO> copyList(List<T> sourceList){
        List<UserDTO> list = new ArrayList<>();
        sourceList.forEach( source -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(source,userDTO);
            list.add(userDTO);
        });
        return list;
    }
}
