package psn.lxy.mms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import psn.lxy.mms.POJO.ProduceTable;
import psn.lxy.mms.Service.ProduceService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("produce")
public class ProduceController {

    @Resource
    private ProduceService produceService;

    //生成生产订单
    @PostMapping("create")
    public Map<String , Object> createProduceTable(
            @RequestParam("productName") String productName,
            @RequestParam("userId") String userId,
            @RequestParam("produceCycle") Float produceCycle,
            @RequestParam("produceTime") String produceTime,
            @RequestParam("orderId") Long OrderId
            ){
        return produceService.createProduceTable(productName , userId , produceCycle , produceTime , OrderId);
    }

    //查询生产订单
    @GetMapping("get/{produceId}")
    public Map<String , Object> getProduceTableById(
            @PathVariable("produceId") Long produceId
    ){
        return produceService.getProduceTableById(produceId);
    }

    //查询全部生产订单
    @GetMapping("all")
    public Map<String , Object> getAllProduceTable(){
        return produceService.getAllProduceTable();
    }

    //模糊查询
    @GetMapping("like/{keyword}")
    public Map<String , Object> getProduceTableByLike(
            @PathVariable("keyword") String keyword
    ){
        return               produceService.getProduceTableByLike(keyword);
    }

    //批量删除
    @DeleteMapping("batch/{ids}")
    public Map<String , Object> deleteProduceTableByBatch(
            @PathVariable("ids") List<Long> ids
    ){
        return produceService.deleteProduceTableByBatch(ids);
    }

    //根据id删除
    @DeleteMapping("delete/{id}")
    public Map<String , Object> deleteProduceTableById(
            @PathVariable("id") Long id
    ){
        return produceService.deleteProduceTableById(id);
    }
}
