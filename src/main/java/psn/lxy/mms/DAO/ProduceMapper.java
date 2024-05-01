package psn.lxy.mms.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import psn.lxy.mms.POJO.ProduceTable;

import java.util.List;

@Mapper
public interface ProduceMapper {

    long insertProduceTable(ProduceTable produceTable);

    ProduceTable getProduceTable(@Param("id") Long produceId);

    long updateProduceState(@Param("id") Long produceId , @Param("state") String state);

    List<ProduceTable> getAllProductTable();

    ProduceTable getProductTableByOrder(@Param("orderId") Long orderId);

    long deleteProduceTableById(@Param("id") Long id);

    long deleteProduceTableByBatch(@Param("ids") List<Long> ids);

    List<ProduceTable> getProduceTableByLike(@Param("keyword") String keyword);
}
