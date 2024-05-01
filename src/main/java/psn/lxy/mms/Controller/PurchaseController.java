package psn.lxy.mms.Controller;


import org.springframework.web.bind.annotation.*;
import psn.lxy.mms.Service.PurchaseService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    @Resource
    private PurchaseService purchaseService;

    //生成采购计划
    @PostMapping("create")
    public Map<String , Object> createPurchaseTable(
            @RequestBody Map<String , Integer> purchaseMap ,
            @RequestParam("cycle") Float purchaseCycle ,
            @RequestParam("time") String purchaseTime ,
            @RequestParam(value = "orderId" , required = false) Long orderId
            ){
        return purchaseService.createPurchaseTable(purchaseMap , purchaseCycle , purchaseTime , orderId);
    }

    //查询采购计划
    @GetMapping("get/{purchaseId}")
    public Map<String ,Object> getPurchaseTable(
            @PathVariable("purchaseId") Long purchaseId
    ){
        return purchaseService.getPurchaseTable(purchaseId);
    }

    //查询采购清单
    @GetMapping("list/{purchaseId}")
    public Map<String , Object> getPurchaseList(
            @PathVariable("purchaseId") Long purchaseId
    ){
        return purchaseService.getPurchaseList(purchaseId);
    }

    //获取全部采购计划
    @GetMapping("all")
    public Map<String , Object> getAllPurchaseTable(){
        return purchaseService.getAllPurchaseTable();
    }

    //模糊查询
    @GetMapping("like/{keyword}")
    public Map<String , Object> getPurchaseTableByLike(
            @PathVariable("keyword") String keyword
    ){
        return purchaseService.getPurchaseTableByLike(keyword);
    }

    //批量删除
    @DeleteMapping("batch/{ids}")
    public Map<String , Object> deletePurchaseTableByBatch(
            @PathVariable("ids") List<Long> ids
    ){
        return purchaseService.deletePurchaseTableByBatch(ids);
    }

    //根据id删除
    @DeleteMapping("delete/{id}")
    public Map<String , Object> deletePurchaseTableById(
            @PathVariable("id") Long id
    ){
        return purchaseService.deletePurchaseTableById(id);
    }

}
