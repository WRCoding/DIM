package top.ink.dimgateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.ink.dimgateway.entity.LoginRes;
import top.ink.dimgateway.entity.common.Response;
import top.ink.dimgateway.entity.dto.FriendDTO;
import top.ink.dimgateway.entity.dto.LoginDTO;
import top.ink.dimgateway.entity.dto.UserDTO;
import top.ink.dimgateway.entity.service.ServiceInfo;
import top.ink.dimgateway.server.UserService;


import javax.annotation.Resource;
import java.util.List;

/**
 * desc: 用户接口
 * @author ink
 * date:2022-03-05 20:49
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public Response<UserDTO> register(@RequestBody LoginDTO loginDTO){
        return userService.register(loginDTO);
    }

    @PostMapping("/login")
    public Response<LoginRes> login(@RequestBody LoginDTO loginDTO){
        try {
            return userService.login(loginDTO);
        }catch (RuntimeException exception){
            log.error("login occur error: {}", exception.getMessage());
            return Response.error();
        }
    }

    @GetMapping("/search")
    public Response<List<UserDTO>> search(String key){
        return userService.search(key);
    }

    @PostMapping("/addFriend")
    public Response<List<UserDTO>> addFriend(@RequestBody FriendDTO friendDTO){
        return userService.addFriend(friendDTO);
    }

    @GetMapping("/friends")
    public Response<List<UserDTO>> friends(String lid){
        return userService.friends(lid);
    }

    @PostMapping("/uploadFile")
    public Response<UserDTO> uploadFile(MultipartFile file, String lid, boolean flag){
        return userService.uploadFile(file,lid,flag);
    }

    @PostMapping("/update")
    public Response<UserDTO> updateUser(String lid, String name, String desc){
        log.info("lid: {}, name: {}, desc: {}", lid, name, desc);
        return userService.updateUser(lid, name, desc);
    }

    @GetMapping("/reconnect/{lid}")
    public Response<ServiceInfo> reconnect(@PathVariable("lid") String lid){
        return userService.reconnect(lid);
    }

}
