package psn.lxy.mms.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;
import psn.lxy.mms.DAO.MaterialMapper;
import psn.lxy.mms.POJO.Material;
import psn.lxy.mms.Service.MaterialService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Resource
    private MaterialMapper materialMapper;

    @Override
    public Map<String, Object> getMaterialInfo(Integer id) {
        return BeanUtil.beanToMap(materialMapper.getMaterialById(id));
    }

    @Override
    public Map<String, Object> saveMaterial(Material material) {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , materialMapper.insertMaterial(material) == 1 ? "添加成功" : "添加失败");
        return resMap;
    }

    @Override
    public Map<String, Object> updateMaterialInventory(Integer id , Integer orderNumber) {
        Map<String , Object> resMap = new HashMap<>();
        Material material = materialMapper.getMaterialById(id);
        if (orderNumber <= material.getInventory()){
            materialMapper.updateMaterialInventory(id , material.getInventory() - orderNumber);
            resMap.put("result" , "库存充足");
            resMap.put("remained" , material.getInventory() - orderNumber);
            resMap.put("needed" , 0);
            resMap.put("result-code" , 1);
        }else {
            materialMapper.updateMaterialInventory(id , 0);
            resMap.put("result" , "库存不足");
            resMap.put("remained" , 0);
            resMap.put("needed" , orderNumber - material.getInventory());
            resMap.put("result-code" , 0);
        }
        return resMap;
    }

    @Override
    public Map<String, Object> deleteMaterialById(Integer id) {
        Map<String , Object> resMap = new HashMap<>();
        if (materialMapper.deleteMaterialById(id) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> getLikeMaterial(String keyword) {
        if ("".equals(keyword)){
            return getAllMaterial();
        }
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , materialMapper.getLikeMaterial(keyword));
        return resMap;
    }

    @Override
    public Map<String, Object> getAllMaterial() {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , materialMapper.getAllMaterial());
        return resMap;
    }

    @Override
    public Map<String, Object> deleteMaterialByBatch(List<Integer> ids) {
        Map<String , Object> resMap = new HashMap<>();
        if (materialMapper.deleteMaterialByBatch(ids) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> getAllProduct() {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , materialMapper.getAllMaterial());
        return resMap;
    }


}
