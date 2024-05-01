package psn.lxy.mms.POJO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {
    private Long orderId;
    private String userId;
    private String productName;
    private Integer number;
    private String state;
    private boolean isEnough;

    public Order(){}

    public Order(String userId, String productName, Integer number, String state) {
        this.userId = userId;
        this.productName = productName;
        this.number = number;
        this.state = state;
    }
}
