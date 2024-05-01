package psn.lxy.mms.POJO;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class BOM {
    private Integer id;
    private String materialName;
    private Integer neededNumber;
    private String productName;
    private Integer leftId;
    private Integer rightId;
    private List<BOM> childTree;

    {
        childTree = new ArrayList<>();
    }

    public BOM(){}

    @Override
    public String toString() {
        return "BOM{" +
                "id=" + id +
                ", materialName='" + materialName + '\'' +
                ", neededNumber=" + neededNumber +
                ", productName='" + productName + '\'' +
                ", leftId=" + leftId +
                ", rightId=" + rightId +
                ", childTree=" + childTree +
                '}';
    }

    public BOM(String materialName, Integer neededNumber, String productName) {
        this.materialName = materialName;
        this.neededNumber = neededNumber;
        this.productName = productName;
    }
}
