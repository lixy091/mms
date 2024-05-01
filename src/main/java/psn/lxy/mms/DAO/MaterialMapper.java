package psn.lxy.mms.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import psn.lxy.mms.POJO.Material;

import java.util.List;

@Mapper
public interface MaterialMapper {

    Material getMaterialById(@Param("id") Integer id);

    long insertMaterial(Material material);

    long deleteMaterialById(@Param("id") Integer id);

    long updateMaterialInventory(@Param("id") Integer id , @Param("number") Integer orderNumber);

    List<Material> getAllMaterial();

    List<Material> getLikeMaterial(@Param("keyword") String keyword);

    long deleteMaterialByBatch(@Param("ids") List<Integer> ids);

    List<Material> getAllProduct();

    List<Material> getMaterialByIds(@Param("ids") List<Integer> ids);
}
