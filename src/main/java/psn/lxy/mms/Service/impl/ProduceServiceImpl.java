package psn.lxy.mms.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Service;
import psn.lxy.mms.DAO.ProduceMapper;
import psn.lxy.mms.POJO.ProduceTable;
import psn.lxy.mms.Service.ProduceService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProduceServiceImpl implements ProduceService {

    @Resource
    private ProduceMapper produceMapper;

    private final Random random = new Random();
    private final Snowflake snowflake = new Snowflake();

    @Override
    public Map<String, Object> createProduceTable(String productName, String userId, Float produceCycle, String produceTime, Long orderId) {
        Map<String , Object> resMap = new HashMap<>();
        ProduceTable produceTable = new ProduceTable();
//        String produceId = ((~System.currentTimeMillis()) << 5) + random.nextInt(17) + "";
        Long produceId = snowflake.nextId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date produceDate = null;
        try {
            produceDate = sdf.parse(produceTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String state = produceDate.after(new Date()) ? "未开始" : "正在生产";
        produceTable.setProduceId(produceId);
        produceTable.setProductName(productName);
        produceTable.setUserId(userId);
        produceTable.setProduceCycle(produceCycle);
        produceTable.setProduceTime(produceDate);
        produceTable.setState(state);
        produceTable.setOrderId(orderId);

        if (produceMapper.insertProduceTable(produceTable) == 1L){
            resMap.put("result" , "插入成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "插入失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> getAllProduceTable() {
        Map<String , Object> resMap = new HashMap<>();
        List<Map<String , Object>> tables = new ArrayList<>();
        for (ProduceTable produceTable : produceMapper.getAllProductTable()) {
            tables.add(tableToMapAndUpdate(produceTable));
        }
        resMap.put("result" , tables);
        resMap.put("massage" , "查询成功");
        return resMap;
    }

    @Override
    public Map<String, Object> deleteProduceTableById(Long id) {
        Map<String , Object> resMap = new HashMap<>();
        if (produceMapper.deleteProduceTableById(id) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , 1);
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , 0);
        }
        return resMap;
    }

    @Override
    public Map<String, Object> deleteProduceTableByBatch(List<Long> ids) {
        Map<String , Object> resMap = new HashMap<>();
        if (produceMapper.deleteProduceTableByBatch(ids) != 0){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , 1);
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , 0);
        }
        return resMap;
    }

    @Override
    public Map<String, Object> getProduceTableByLike(String keyword) {
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("result" , produceMapper.getProduceTableByLike(keyword));
        resMap.put("result-code" , 1);
        resMap.put("message" , "获取成功");
        return resMap;
    }

    @Override
    public Map<String, Object> getProduceTableById(Long produceId) {
        Map<String , Object> resMap = new HashMap<>();
        ProduceTable produceTable = produceMapper.getProduceTable(produceId);
        if (produceTable != null){
            resMap.put("produceTable" , tableToMapAndUpdate(produceTable));
            resMap.put("result" , "查询成功");
        }else {
            resMap.put("result" , "未查询到该生产订单信息");
        }
        return resMap;
    }



    public Map<String , Object> tableToMapAndUpdate(ProduceTable produceTable){
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(produceTable.getProduceTime());
        int hour = (int) produceTable.getProduceCycle().floatValue();
        int minute = (int) ((produceTable.getProduceCycle() - hour) * 60);
        c.add(Calendar.HOUR_OF_DAY , hour);
        c.add(Calendar.MINUTE , minute);
        Date finishTime = c.getTime();
        String state = produceTable.getProduceTime().after(now) ? "未开始" : now.after(finishTime) ? "已结束" : "生产中";

        produceMapper.updateProduceState(produceTable.getProduceId(), state);
        produceTable.setState(state);

        Map<String, Object> beanMap = BeanUtil.beanToMap(produceTable);
        beanMap.put("produceTime" , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(produceTable.getProduceTime()));
        return beanMap;
    }
}
