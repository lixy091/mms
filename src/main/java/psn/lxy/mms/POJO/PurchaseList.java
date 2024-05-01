package psn.lxy.mms.POJO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseList {
    private Long purchaseId;
    private String materialName;
    private Integer number;
}
