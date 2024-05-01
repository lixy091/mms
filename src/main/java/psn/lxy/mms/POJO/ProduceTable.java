package psn.lxy.mms.POJO;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ProduceTable {
    private Long produceId;
    private String productName;
    private String userId;
    private Float produceCycle;
    private Date produceTime;
    private String state;
    private Long orderId;

    public ProduceTable(){}
}
