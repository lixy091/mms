package psn.lxy.mms.Service;

import java.util.List;
import java.util.Map;

public interface PurchaseService {

    Map<String, Object> createPurchaseTable(Map<String, Integer> purchaseMap, Float purchaseCycle, String purchaseTime, Long orderId);

    Map<String, Object> getPurchaseTable(Long purchaseId);

    Map<String, Object> getPurchaseList(Long purchaseId);

    Map<String, Object> getAllPurchaseTable();

    Map<String, Object> getPurchaseTableByLike(String keyword);

    Map<String, Object> deletePurchaseTableByBatch(List<Long> ids);

    Map<String, Object> deletePurchaseTableById(Long id);
}
