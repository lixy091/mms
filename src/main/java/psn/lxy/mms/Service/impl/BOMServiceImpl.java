package psn.lxy.mms.Service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import psn.lxy.mms.DAO.BOMMapper;
import psn.lxy.mms.POJO.BOM;
import psn.lxy.mms.Service.BOMService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BOMServiceImpl implements BOMService {

    @Resource
    private BOMMapper bomMapper;

//    @Override
//    public Map<String, Object> getBomTreeByName(String productName) {
//        Map<String , Object> resMap = new HashMap<>();
//        List<BOM> bomTree = bomMapper.getBOMTree(productName);
//        if (bomTree.size() == 1){
//            resMap.put("BOMTree" , bomTree);
//            resMap.put("result" , "查询成功");
//            return resMap;
//        }
//        BOM root = bomTree.get(0);
//        for (int i = 1 ; i < bomTree.size() ; i++){
//            int f = bomTree.get(i).getLeftId() - bomTree.get(i - 1).getLeftId();
//            while ( f > 1){
//                root = root.getParent();
//                f--;
//            }
//            bomTree.get(i).setParent(root);
//            root.getChildTree().add(bomTree.get(i));
//            root = bomTree.get(i);
//        }
//        bomTree.forEach(f -> f.setParent(null));
//        resMap.put("BOMTree" , bomTree.get(0));
//        resMap.put("result" , "查询成功");
//        return resMap;
//    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getBomTreeByName(String productName) {
        Map<String , Object> resMap = new HashMap<>();
        List<BOM> bomList = bomMapper.getBOMTree(productName);
        List<Map<String, Object>> bomTree = bomList.stream().map(this::getMapFromBOM).collect(Collectors.toList());
        if (bomTree.size() == 1){
            resMap.put("BOMTree" , bomTree);
            resMap.put("result" , "查询成功");
            return resMap;
        }
        Map<String , Object> root = bomTree.get(0);
        for (int i = 1 ; i < bomTree.size() ; i++){
            int f = (Integer) bomTree.get(i).get("left") - (Integer) bomTree.get(i - 1).get("left");
            while ( f > 1){
                root = (Map<String, Object>) root.get("parent");
                f--;
            }
            bomTree.get(i).put("parent" , root);
            bomTree.get(i).put("pid" , root.get("id"));
            ((List<Map<String , Object>>)root.get("children")).add(bomTree.get(i));
            root = bomTree.get(i);
        }
        bomTree.forEach(f -> f.remove("parent"));
        resMap.put("BOMTree" , bomTree.get(0));
        resMap.put("result" , "查询成功");
        return resMap;
    }

    @Override
    public Map<String, Object> dropBomTreeByName(String productName) {
        Map<String , Object> resMap = new HashMap<>();
        if (bomMapper.deleteBOMTree(productName) == 1){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteNodeFromBomTree(Integer nodeId) {
        Map<String , Object> resMap = new HashMap<>();
        BOM node = bomMapper.getBOMTreeNodeById(nodeId);
        if (bomMapper.deleteBOMTreeNode(nodeId , node.getLeftId() , node.getRightId() , node.getProductName()) == 1){
            resMap.put("result" , "删除成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "删除失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    @Override
    @Transactional
    public Map<String, Object> addNode(Integer parentId, String materialName, String productName, Integer neededNumber) {
        Map<String , Object> resMap = new HashMap<>();
        BOM bomNode = new BOM(materialName , neededNumber , productName);
        if (parentId == null || parentId.equals(0)){
            bomNode.setLeftId(1);
            bomNode.setRightId(2);
        }else {
            BOM parent = bomMapper.getBOMTreeNodeById(parentId);
            bomNode.setLeftId(parent.getLeftId() + 1);
            bomNode.setRightId(bomNode.getLeftId() + 1);
        }
        if (bomMapper.insertBOMTreeNode(bomNode) == 1){
            resMap.put("result" , "插入成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "插入失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    @Override
    @Transactional
    public Map<String, Object> changeParent(Integer parentId, Integer nodeId) {
        Map<String , Object> resMap = new HashMap<>();
        BOM parent = bomMapper.getBOMTreeNodeById(parentId);
        BOM node = bomMapper.getBOMTreeNodeById(nodeId);
        List<Integer> childIdList = bomMapper.getBOMChildTree(node.getLeftId(), node.getRightId() , node.getProductName()).stream().map(BOM::getId).collect(Collectors.toList());
        int udDifference = childIdList.size() * 2;
        int childDifference;
        List<Integer> udrList;
        List<Integer> udlList;
        if (node.getLeftId() > parent.getLeftId()){
            childDifference =(parent.getLeftId() + 1) -node.getLeftId();
            udrList = bomMapper.getBOMRightNode(parent.getLeftId() , node.getLeftId() , node.getProductName()).stream().map(BOM::getId).collect(Collectors.toList());
            udlList = bomMapper.getBOMLeftNode(parent.getLeftId() , node.getLeftId() , node.getProductName()).stream().map(BOM::getId).collect(Collectors.toList());

        }else {
            udDifference = udDifference * (-1);
            childDifference =(parent.getRightId() - 1) - node.getRightId() ;
            udrList = bomMapper.getBOMRightNode(node.getRightId() , parent.getRightId() , node.getProductName()).stream().map(BOM::getId).collect(Collectors.toList());
            udlList = bomMapper.getBOMLeftNode(node.getRightId() , parent.getRightId() , node.getProductName()).stream().map(BOM::getId).collect(Collectors.toList());
        }

        if (bomMapper.updateBOMTreeSequence(childIdList , udrList , udlList , childDifference , udDifference) == 1){
            resMap.put("result" , "修改成功");
            resMap.put("result-code" , "1");
        }else {
            resMap.put("result" , "修改失败");
            resMap.put("result-code" , "0");
        }
        return resMap;
    }

    private Map<String , Object> getMapFromBOM(BOM bom){
        Map<String , Object> map = new HashMap<>();
        map.put("id" , bom.getId());
        map.put("label" , bom.getMaterialName());
        map.put("children" , bom.getChildTree());
        map.put("parent" , null);
        map.put("pid" , null);
        map.put("number" , bom.getNeededNumber());
        map.put("productName" , bom.getProductName());
        map.put("left" , bom.getLeftId());
        map.put("right" , bom.getRightId());
        return map;
    }
}
