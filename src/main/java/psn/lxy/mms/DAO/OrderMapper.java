package psn.lxy.mms.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import psn.lxy.mms.POJO.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    long insertOrder(Order order);

    List<Order> getAllOrder();

    Order getOrderByUser(@Param("userId") String userId);

    Order getOrderById(@Param("id") Long id);

    long deleteOrderById(@Param("id") Long orderId);

    long deleteOrderByBatch(@Param("ids") List<Long> ids);

    List<Order> getOrderByLike(@Param("keyword") String keyword);
}
