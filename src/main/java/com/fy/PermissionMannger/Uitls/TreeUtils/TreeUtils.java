package com.fy.PermissionMannger.Uitls.TreeUtils;

import com.fy.PermissionMannger.Entity.BaseTreeGrid;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @author fangyan
 * @create 2019/5/10
 * @since 1.0.0
 */
public class TreeUtils {
    /**
     * 格式化list为树形list
     * @param list
     * @param flag true 表示全部展开，其他 表示不展开
     * @return
     */
    public static <T extends BaseTreeGrid> List<T> formatTree(List<T> list, Boolean flag) {

        List<T> nodeList = new ArrayList<T>();
        for(T node1 : list){
            boolean mark = false;
            for(T node2 : list){
                if(node1.getParentId()!=null && node1.getParentId().equals(node2.getId())){
                    node2.setLeaf(false);
                    mark = true;
                    if(node2.getChildren() == null) {
                        node2.setChildren(new ArrayList<BaseTreeGrid>());
                    }
                    node2.getChildren().add(node1);
                    if (flag) {
                        //默认已经全部展开
                    } else{
                        node2.setExpanded(false);
                    }
                    break;
                }
            }
            if(!mark){
                nodeList.add(node1);
                if (flag) {
                    //默认已经全部展开
                } else{
                    node1.setExpanded(false);
                }
            }
        }
        return nodeList;
    }
}
