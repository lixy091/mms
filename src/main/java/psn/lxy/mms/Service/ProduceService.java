package psn.lxy.mms.Service;

import java.util.List;
import java.util.Map;

public interface ProduceService {


    Map<String, Object> getProduceTableById(Long produceId);

    Map<String, Object> createProduceTable(String productName, String userId, Float produceCycle, String produceTime, Long orderId);

    Map<String, Object> getAllProduceTable();

    Map<String, Object> deleteProduceTableById(Long id);

    Map<String, Object> deleteProduceTableByBatch(List<Long> ids);

    Map<String, Object> getProduceTableByLike(String keyword);
}
