package psn.lxy.mms.DAO;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import psn.lxy.mms.POJO.BOM;

import java.util.List;
import java.util.Map;

@Mapper
public interface BOMMapper {

    List<BOM> getBOMTree(@Param("name") String productName);

    long saveBOMTree(List<BOM> bomTree);

    long deleteBOMTree(@Param("name") String productName);

    long insertBOMTreeNode(BOM bom);

//    long updateBOMTreeSequenceLeaf(@Param("id") Integer id , @Param("left") Integer leftId , @Param("right") Integer right);

    long deleteBOMTreeNode(@Param("id") Integer id , @Param("left") Integer leftId , @Param("right") Integer right , @Param("name") String productName);

//    long moveBOMTreeNode(@Param("parent") Integer parentId , @Param("node") Integer nodeId , @Param("pLeft") Integer parentLeft , @Param("pRight") Integer parentRight , @Param("nLeft") Integer nodeLeft , @Param("nRight") Integer nodeRight);

    long updateBOMTreeSequence(@Param("nodeList") List<Integer> nodeList ,  @Param("udrList") List<Integer> udrList , @Param("udlList") List<Integer> udlList , @Param("nodeDiff") Integer nodeDifference , @Param("udDiff") Integer udDifference);

    List<BOM> getBOMChildTree(@Param("left") Integer leftId , @Param("right") Integer right ,@Param("name") String productName);

    List<BOM> getBOMRightNode(@Param("s") Integer s , @Param("b") Integer b , @Param("name") String productName);

    List<BOM> getBOMLeftNode(@Param("s") Integer s , @Param("b") Integer b , @Param("name") String productName);

    BOM getBOMTreeNodeById(@Param("id") Integer id);

    @MapKey("materialId")
    List<Map<String , Object>> getBOMListWithType(@Param("name") String productName);


}
