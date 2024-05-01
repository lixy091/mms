package psn.lxy.mms.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import psn.lxy.mms.POJO.Material;
import psn.lxy.mms.POJO.PurchaseList;
import psn.lxy.mms.POJO.PurchaseTable;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseMapper {

    long insertPurchaseTable(PurchaseTable purchaseTable);

    long insertPurchaseList(@Param("id") Long purchaseId , @Param("map") Map<String , Integer> map);

    long insertPurchaseListByList(@Param("list")List<Material> list , @Param("id") Long orderId);

    PurchaseTable getPurchaseTable(@Param("id") Long purchaseId);

    long updatePurchaseState(@Param("id") Long purchaseId , @Param("state") String state);

    List<PurchaseList> getPurchaseList(@Param("id") Long purchaseId);

    List<PurchaseTable> getAllPurchaseTable();

    PurchaseTable getPurchaseTableByOrderId(@Param("orderId") Long orderId);

    List<PurchaseTable> getPurchaseTableByLike(@Param("keyword") String keyword);

    long deletePurchaseTableByBatch(@Param("ids") List<Long> ids);

    long deletePurchaseTableById(@Param("id") Long id);
}
