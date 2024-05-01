package psn.lxy.mms.Controller;

import org.springframework.web.bind.annotation.*;
import psn.lxy.mms.POJO.Order;
import psn.lxy.mms.Service.OrderService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    //下单
    @PostMapping
    public Map<String , Object> orderByUser(
            @RequestParam("userId") String userId,
            @RequestParam("productName") String productName,
            @RequestParam("number") Integer number
    ){
        return orderService.orderByUser(userId , productName , number);
    }

    //获取全部订单
    @GetMapping("all")
    public Map<String , Object> getAllOrder(){
        return orderService.getAllOrder();
    }

    //根据用户获取订单
    @GetMapping("user/{userId}")
    public Map<String ,Object> getOrderByUser(
            @PathVariable("userId") String userId
    ){
        return orderService.getOrderByUser(userId);
    }

    //根据订单id获取订单
    @GetMapping("get/{id}")
    public Map<String , Object> getOrderById(
            @PathVariable("id") Long id
    ){
        return orderService.getOrderById(id);
    }

    //根据id删除订单
    @DeleteMapping("delete/{id}")
    public Map<String , Object> deleteOrderById(
            @PathVariable("id") Long orderId
    ){
        return orderService.deleteOrderById(orderId);
    }

    //批量删除订单
    @DeleteMapping("batch/{ids}")
    public Map<String , Object> deleteOrderByBatch(
            @PathVariable("ids") List<Long> ids
    ){
        return orderService.deleteOrderByBatch(ids);
    }

    //模糊查询
    @GetMapping("like/{keyword}")
    public Map<String , Object> getOrderByLike(
            @PathVariable("keyword") String keyword
    ){
        return orderService.getOrderByLike(keyword);
    }
}
