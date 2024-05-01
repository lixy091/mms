package psn.lxy.mms.POJO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Material {
    private Integer materialId;
    private String materialName;
    private String type;
    private Integer inventory;

    public Material(){}
}
