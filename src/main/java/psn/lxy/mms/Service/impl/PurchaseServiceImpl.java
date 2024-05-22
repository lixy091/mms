package psn.lxy.mms.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import psn.lxy.mms.DAO.PurchaseMapper;
import psn.lxy.mms.POJO.PurchaseList;
import psn.lxy.mms.POJO.PurchaseTable;
import psn.lxy.mms.Service.PurchaseService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;

    private final Random random = new Random();

    private final Snowflake snowflake = new Snowflake();
    @Override
    @Transactional
    public Map<String, Object> createPurchaseTable(Map<String, Integer> purchaseMap, Float purchaseCycle, String purchaseTime, Long orderId) {
        Map<String , Object> resMap = new HashMap<>();
        Long purchaseId = snowflake.nextId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PurchaseTable purchaseTable = new PurchaseTable();
        Date purchaseDate = null;
        String state;
        try {
            purchaseDate = sdf.parse(purchaseTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        state = purchaseDate.after(new Date()) ? "未开始" : "采购中";

        purchaseTable.setPurchaseId(purchaseId);
        purchaseTable.setPurchaseCycle(purchaseCycle);
        purchaseTable.setPurchaseTime(purchaseDate);
        purchaseTable.setState(state);
        purchaseTable.setOrderId(orderId);

        if (purchaseMapper.insertPurchaseTable(purchaseTable) == 1L && purchaseMapper.insertPurchaseList(purchaseId , purchaseMap) == 1L){
            resMap.put("result" , "添加成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "添加失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> getPurchaseTable(Long purchaseId) {
        Map<String , Object> resMap = new HashMap<>();
        PurchaseTable purchaseTable = purchaseMapper.getPurchaseTable(purchaseId);
        if (purchaseTable != null){
            resMap.put("produceTable" , tableToMapAndUpdate(purchaseTable));
            resMap.put("result" , "查询成功");
        }else {
            resMap.put("result" , "未查询到该采购订单信息");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> getPurchaseList(Long purchaseId) {
        Map<String , Object> resMap = new HashMap<>();
        Map<String , Integer> resList = new HashMap<>();
        for (PurchaseList purchaseList : purchaseMapper.getPurchaseList(purchaseId)) {
            resList.put(purchaseList.getMaterialName() , purchaseList.getNumber());
        }
        resMap.put("result" , resList);
        resMap.put("massage" , "查询成功");
        return resMap;
    }

    @Override
    public Map<String, Object> getAllPurchaseTable() {
        Map<String , Object> resMap = new HashMap<>();
        List<Map<String , Object>> tables = new ArrayList<>();
        for (PurchaseTable purchaseTable : purchaseMapper.getAllPurchaseTable()) {
            tables.add(tableToMapAndUpdate(purchaseTable));
        }
        resMap.put("result" , tables);
        resMap.put("massage" , "查询成功");
        return resMap;
    }

    @Override
    public Map<String, Object> getPurchaseTableByLike(String keyword) {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , purchaseMapper.getPurchaseTableByLike(keyword));
        resMap.put("result-code" , 1);
        resMap.put("message" , "获取成功");
        return resMap;
    }

    @Override
    @Transactional
    public Map<String, Object> deletePurchaseTableByBatch(List<Long> ids) {
        Map<String , Object> resMap = new HashMap<>();
        if (purchaseMapper.deletePurchaseTableByBatch(ids) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , 1);
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , 0);
        }
        return resMap;
    }

    @Override
    @Transactional
    public Map<String, Object> deletePurchaseTableById(Long id) {
        Map<String , Object> resMap = new HashMap<>();
        if (purchaseMapper.deletePurchaseTableById(id) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , 1);
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , 0);
        }
        return resMap;
    }

    public Map<String , Object> tableToMapAndUpdate(PurchaseTable purchaseTable){
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(purchaseTable.getPurchaseTime());
        int hour = (int) purchaseTable.getPurchaseCycle().floatValue();
        int minute = (int) ((purchaseTable.getPurchaseCycle() - hour) * 60);
        c.add(Calendar.HOUR_OF_DAY , hour);
        c.add(Calendar.MINUTE , minute);
        Date finishTime = c.getTime();
        String state = purchaseTable.getPurchaseTime().after(now) ? "未开始" : now.after(finishTime) ? "已结束" : "采购中";

        purchaseMapper.updatePurchaseState(purchaseTable.getPurchaseId() , state);
        purchaseTable.setState(state);
        Map<String, Object> beanMap = BeanUtil.beanToMap(purchaseTable);
        beanMap.put("purchaseTime" , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(purchaseTable.getPurchaseTime()));
        return beanMap;
    }
}
