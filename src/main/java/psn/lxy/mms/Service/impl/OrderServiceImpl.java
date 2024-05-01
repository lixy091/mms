package psn.lxy.mms.Service.impl;

import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import psn.lxy.mms.DAO.*;
import psn.lxy.mms.POJO.Material;
import psn.lxy.mms.POJO.Order;
import psn.lxy.mms.POJO.ProduceTable;
import psn.lxy.mms.POJO.PurchaseTable;
import psn.lxy.mms.Service.MaterialService;
import psn.lxy.mms.Service.OrderService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PurchaseMapper purchaseMapper;

    @Resource
    private ProduceMapper produceMapper;

    @Resource
    private BOMMapper bomMapper;

    @Resource
    private MaterialService materialService;
    
    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private ProduceServiceImpl produceService;

    @Resource
    private PurchaseServiceImpl purchaseService;

    private final Snowflake snowflake = new Snowflake();

    @Override
    @Transactional
    public Map<String, Object> orderByUser(String userId, String productName, Integer number) {
        Map<String , Object> resMap = new HashMap<>();
        //获取所需物料清单
        Map<Integer , Integer> mList = new HashMap<>();
        for (Map<String, Object> map : bomMapper.getBOMListWithType(productName)) {
            if ("原料".equals(map.get("type"))){
                 if (mList.containsKey((Integer) map.get("materialId"))){
                     mList.put((Integer) map.get("materialId"), mList.get((Integer) map.get("materialId")) + (Integer) map.get("neededNumber"));
                 }else {
                     mList.put((Integer) map.get("materialId") , (Integer) map.get("neededNumber"));
                 }
            }
        }
        mList.entrySet().forEach(e -> e.setValue(e.getValue() * number));
        //生成订单
        Order order = new Order(userId , productName , number , "待处理");
        order.setOrderId(snowflake.nextId());
        Lock lock = new ReentrantLock();
        Map<Integer , Integer> neededList = new HashMap<>();
        try {
            lock.lock();
            for (Map.Entry<Integer, Integer> entry : mList.entrySet()) {
                Map<String, Object> map = materialService.updateMaterialInventory(entry.getKey(), entry.getValue());
                if ( map.get("result-code").equals(0)){
                    neededList.put(entry.getKey() , (Integer) map.get("needed"));
                }
            }
        }finally {
            lock.unlock();
        }

        order.setEnough(neededList.isEmpty());
        orderMapper.insertOrder(order);
        if (!order.isEnough()){
            List<Material> neededMaterials = materialMapper.getMaterialByIds(new ArrayList<>(mList.keySet()));
            //用Material类的inventory属性存储所需数量，方便存储，不代表库存
            neededMaterials.forEach(m -> m.setInventory(mList.get(m.getMaterialId())));
            purchaseMapper.insertPurchaseListByList(neededMaterials , order.getOrderId());
        }
        resMap.put("result" , order);
        resMap.put("result-code" , 1);

        return resMap;
    }

    @Override
    public Map<String, Object> getAllOrder() {
        Map<String , Object> resMap = new HashMap<>();
        List<Order> allOrder = orderMapper.getAllOrder();
        allOrder.forEach(this::updateOrderState);
        resMap.put("result" , allOrder);
        resMap.put("result-code" , 1);
        resMap.put("message" , "获取成功");
        return resMap;
    }

    @Override
    public Map<String, Object> getOrderByUser(String userId) {
        Map<String , Object> resMap = new HashMap<>();
        Order order = orderMapper.getOrderByUser(userId);
        updateOrderState(order);
        resMap.put("result" , order);
        resMap.put("result-code" , 1);
        resMap.put("message" , "获取成功");
        return resMap;
    }

    @Override
    public Map<String, Object> getOrderById(Long id) {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , orderMapper.getOrderById(id));
        resMap.put("result-code" , 1);
        resMap.put("message" , "获取成功");
        return resMap;
    }

    @Override
    public Map<String, Object> deleteOrderById(Long orderId) {
        Map<String , Object> resMap = new HashMap<>();
        if (orderMapper.deleteOrderById(orderId) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , 1);
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , 0);
        }
        return resMap;
    }

    @Override
    public Map<String, Object> deleteOrderByBatch(List<Long> ids) {
        Map<String , Object> resMap = new HashMap<>();
        if (orderMapper.deleteOrderByBatch(ids) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , 1);
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , 0);
        }
        return resMap;
    }

    @Override
    public Map<String, Object> getOrderByLike(String keyword) {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , orderMapper.getOrderByLike(keyword));
        resMap.put("result-code" , 1);
        resMap.put("message" , "获取成功");
        return resMap;
    }

    private void updateOrderState(Order order){
        ProduceTable produceTable = produceMapper.getProductTableByOrder(order.getOrderId());
        if (produceTable != null){
            Map<String, Object> produceMap = produceService.tableToMapAndUpdate(produceTable);
            order.setState((String) produceMap.get("state"));
            order.setEnough(true);
        }else {
            PurchaseTable purchaseTable = purchaseMapper.getPurchaseTableByOrderId(order.getOrderId());
            if (purchaseTable != null){
                Map<String, Object> purchaseMap = purchaseService.tableToMapAndUpdate(purchaseTable);
                if ("已结束".equals(purchaseMap.get("state"))){
                    order.setEnough(true);
                }
            }
        }
    }
}
