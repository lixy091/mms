package psn.lxy.mms.Controller;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import psn.lxy.mms.POJO.User;
import psn.lxy.mms.Service.UserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //用户注册
    @PostMapping("register")
    public Map<String ,Object> register(
            @RequestBody User user
    ){
        return userService.register(user);
    }

    //用户登录
    @PostMapping("login")
    public Map<String , Object> userLogin(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "password") String password
    ){
        return userService.userLogin(userId , password);
    }

    //用户授权
    @PutMapping("authorize")
    public Map<String , Object> userAuthorize(
            @RequestParam("userId") String userId ,
            @RequestParam("identity") String identity
    ){
        return userService.userAuthorize(userId , identity);
    }

    //查询所有用户
    @GetMapping("all")
    public Map<String , Object> getAllUser(){
        return userService.getAllUser();
    }

    //根据id删除用户
    @DeleteMapping("delete/{userId}")
    public Map<String , Object> deleteUserById(
            @PathVariable("userId") String userId
    ){
        return userService.deleteUserById(userId);
    }

    //用户模糊查询
    @GetMapping("get/{keyword}")
    public Map<String , Object> getLikeUser(
            @PathVariable("keyword") String keyword
    ){
        return userService.getLikeUser(keyword);
    }

    //用户批量删除
    @DeleteMapping("batch/{ids}")
    public Map<String , Object> deleteUserByBatch(
            @PathVariable("ids") List<String> ids
    ){
        return userService.deleteUserByBatch(ids);
    }
}
