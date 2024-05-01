package psn.lxy.mms.Service;

import psn.lxy.mms.POJO.BOM;

import java.util.Map;

public interface BOMService {
    Map<String, Object> getBomTreeByName(String productName);

    Map<String, Object> dropBomTreeByName(String productName);

    Map<String, Object> deleteNodeFromBomTree(Integer nodeId);

    Map<String, Object> changeParent(Integer parentId, Integer nodeId);

    Map<String, Object> addNode(Integer parentId, String materialName, String productName, Integer neededNumber);
}
