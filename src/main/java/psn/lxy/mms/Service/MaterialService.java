package psn.lxy.mms.Service;

import psn.lxy.mms.POJO.Material;

import java.util.List;
import java.util.Map;

public interface MaterialService {
    Map<String, Object> getMaterialInfo(Integer id);

    Map<String, Object> saveMaterial(Material material);

    Map<String, Object> updateMaterialInventory(Integer id , Integer orderNumber);

    Map<String, Object> deleteMaterialById(Integer id);

    Map<String, Object> getLikeMaterial(String keyword);

    Map<String, Object> getAllMaterial();

    Map<String, Object> deleteMaterialByBatch(List<Integer> ids);

    Map<String, Object> getAllProduct();
}
