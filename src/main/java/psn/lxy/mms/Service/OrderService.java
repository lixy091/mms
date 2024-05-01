package psn.lxy.mms.Service;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, Object> orderByUser(String userId, String productName, Integer number);

    Map<String, Object> getAllOrder();

    Map<String, Object> getOrderByUser(String userId);

    Map<String, Object> getOrderById(Long id);

    Map<String, Object> deleteOrderById(Long orderId);

    Map<String, Object> deleteOrderByBatch(List<Long> ids);

    Map<String, Object> getOrderByLike(String keyword);
}
