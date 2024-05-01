package psn.lxy.mms.Controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import psn.lxy.mms.POJO.Material;
import psn.lxy.mms.Service.MaterialService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/material")
public class MaterialController {

    @Resource
    private MaterialService materialService;

    //根据id查询
    @GetMapping("/get/{id}")
    public Map<String , Object> getMaterialInfo(
            @PathVariable("id") Integer id
    ){
        return materialService.getMaterialInfo(id);
    }

    //添加
    @PostMapping("/add")
    public Map<String , Object> saveMaterial(
            @RequestBody Material material
            ){
        return materialService.saveMaterial(material);
    }

    //减少库存
    @PutMapping("/update/{id}/{inventory}")
    public Map<String , Object> updateMaterialInventory(
            @PathVariable("id") Integer id ,
            @PathVariable("inventory") Integer orderNumber
    ){
        return materialService.updateMaterialInventory(id , orderNumber);
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Map<String , Object> deleteMaterialById(
            @PathVariable("id") Integer id
    ){
        return materialService.deleteMaterialById(id);
    }

    //模糊查询
    @GetMapping("like/{keyword}")
    public Map<String , Object> getLikeMaterial(
            @PathVariable("keyword") String keyword
    ){
        return materialService.getLikeMaterial(keyword);
    }

    //获取全部物料
    @GetMapping("all")
    public Map<String , Object> getAllMaterial(
    ){
        return materialService.getAllMaterial();
    }

    //批量删除
    @DeleteMapping("batch/{ids}")
    public Map<String , Object> deleteMaterialByBatch(
            @PathVariable("ids") List<Integer> ids
    ){
        return materialService.deleteMaterialByBatch(ids);
    }

    //获取成品
    @GetMapping("product")
    public Map<String , Object> getAllProduct(){
        return materialService.getAllProduct();
    }
}
