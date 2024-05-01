package psn.lxy.mms.POJO;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class PurchaseTable {
    private Long purchaseId;
    private Float purchaseCycle;
    private Date purchaseTime;
    private String state;
    private Long orderId;

    public PurchaseTable(){}
}
