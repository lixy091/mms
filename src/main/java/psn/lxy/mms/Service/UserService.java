package psn.lxy.mms.Service;

import psn.lxy.mms.POJO.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> register(User user);

    Map<String, Object> userLogin(String userId, String password);

    Map<String, Object> userAuthorize(String userId , String identity);

    Map<String, Object> getAllUser();

    Map<String, Object> deleteUserById(String userId);

    Map<String, Object> getLikeUser(String keyword);

    Map<String, Object> deleteUserByBatch(List<String> ids);
}
