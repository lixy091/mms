package psn.lxy.mms.POJO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String userId;
    private String userName;
    private String password;
    private String identity;

    private User(){}
}
