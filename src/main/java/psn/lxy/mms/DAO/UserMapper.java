package psn.lxy.mms.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import psn.lxy.mms.POJO.User;

import java.util.List;

@Mapper
public interface UserMapper {
    long addUser(User user);

    long getUserCount(@Param("userId") String userId);

    User getUserInfoById(@Param("id") String userId);

    long updateUserIdentity(@Param("id") String userId , @Param("identity") String identity);

    List<User> getAllUser();

    long deleteUserById(@Param("id") String userId);

    List<User> getLikeUser(@Param("keyword") String keyword);

    long deleteUserByBatch(@Param("ids") List<String> ids);
}
