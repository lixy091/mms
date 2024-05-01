package psn.lxy.mms.Service.impl;

import org.springframework.stereotype.Service;
import psn.lxy.mms.DAO.UserMapper;
import psn.lxy.mms.POJO.User;
import psn.lxy.mms.Service.UserService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> register(User user) {
        Map<String , Object> resMap = new HashMap<>();
        if (user.getIdentity() == null){
            user.setIdentity("用户");
        }
        if (userMapper.getUserCount(user.getUserId()) != 0){
            resMap.put("result" , "添加失败，该用户名已存在");
            resMap.put("result_code" , 0);
        }else {
            long resultCode = userMapper.addUser(user);
            resMap.put("result" , resultCode == 1L ? "添加成功" : "添加失败");
            resMap.put("result_code" , resultCode);
        }
        return resMap;
    }

    @Override
    public Map<String, Object> userLogin(String userId, String password) {
        Map<String , Object> resMap = new HashMap<>();
        if (userMapper.getUserCount(userId) != 0){
            User user = userMapper.getUserInfoById(userId);
            if (user.getPassword().equals(password)){
                resMap.put("result_code" , 1);
                resMap.put("message" , "登录成功");
                resMap.put("result" , user);
                return resMap;
            }
        }
        resMap.put("result_code" , 0);
        resMap.put("message" , "用户名或密码错误");
        return resMap;
    }

    @Override
    public Map<String, Object> userAuthorize(String userId , String identity) {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , userMapper.updateUserIdentity(userId , identity) == 1 ? "更新成功" : "更新失败");
        return resMap;

    }

    @Override
    public Map<String, Object> getAllUser() {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , userMapper.getAllUser());
        return resMap;
    }

    @Override
    public Map<String, Object> deleteUserById(String userId) {
        Map<String , Object> resMap = new HashMap<>();
        if (userMapper.getUserCount(userId) == 0){
            resMap.put("result" , "该用户不存在");
            resMap.put("result_code" , 0);
        }else {
            if (userMapper.deleteUserById(userId) != 0){
                resMap.put("result" , "删除成功");
                resMap.put("result_code" , 1);
            }else {
                resMap.put("result" , "删除失败");
                resMap.put("result_code" , 0);
            }
        }

        return resMap;
    }

    @Override
    public Map<String, Object> getLikeUser(String keyword) {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , userMapper.getLikeUser(keyword));
        return resMap;
    }

    @Override
    public Map<String, Object> deleteUserByBatch(List<String> ids) {
        Map<String , Object> resMap = new HashMap<>();
        if (userMapper.deleteUserByBatch(ids) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }
}
