package psn.lxy.mms.Controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import psn.lxy.mms.POJO.BOM;
import psn.lxy.mms.Service.BOMService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("bom")
public class BOMController {

    @Resource
    private BOMService bomService;

    //通过产品名查询该产品的BOM树
    @GetMapping("get/{productName}")
    public Map<String , Object> getBomTreeByName(
            @PathVariable("productName") String productName
    ){
        return bomService.getBomTreeByName(productName);
    }

    //通过产品名删除整棵树
    @DeleteMapping("/drop/{productName}")
    public Map<String , Object> dropBomTreeByName(
            @PathVariable("productName") String productName
    ){
        return bomService.dropBomTreeByName(productName);
    }

    //删除某结点
    @DeleteMapping("/delete/{id}")
    public Map<String , Object> deleteNodeFromBomTree(
            @PathVariable("id") Integer nodeId
    ){
        return bomService.deleteNodeFromBomTree(nodeId);
    }

    //添加结点
    @PostMapping("/addNode")
    public Map<String , Object> insertNode(
            @RequestParam(value = "parentId" , required = false) Integer parentId,
            @RequestParam("materialName") String materialName ,
            @RequestParam("productName") String productName,
            @RequestParam("number") Integer neededNumber
    ){
        return bomService.addNode(parentId , materialName , productName , neededNumber);
    }

    //修改结点父节点
    @PutMapping("/change")
    public Map<String , Object> changeParent(
            @RequestParam("parentId") Integer parentId,
            @RequestParam("nodeId") Integer nodeId
    ){
        return bomService.changeParent(parentId , nodeId);
    }
}
