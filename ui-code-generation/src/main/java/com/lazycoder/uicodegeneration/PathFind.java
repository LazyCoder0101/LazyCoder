package com.lazycoder.uicodegeneration;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.service.vo.pathfind.PathFindCellDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PathFind {

    /**
     * 格式类型
     */
    public static final int FORMAT_TYPE = 0;

    /**
     * 命令类型
     */
    public static final int COMMAND_TYPE = 1;

    private int metaType = COMMAND_TYPE;

    private String markType;

    @JSONField(deserializeUsing = PathFindCellDeserializer.class)
    private ArrayList<PathFindCell> pathList = new ArrayList<>();

    public PathFind(String markType, int metaType) {
        // TODO Auto-generated constructor stub
        this.markType = markType;
        this.metaType = metaType;
    }

    public PathFind(PathFind parentPathFind) {
        // TODO Auto-generated constructor stub
        this.markType = parentPathFind.getMarkType();
        this.metaType = parentPathFind.getMetaType();
        this.pathList.addAll(parentPathFind.getPathList());
    }

    /**
     * 返回上一层的PathFind
     *
     * @param pathFind
     * @param i        某一层，要最顶层直接写0
     * @return
     */
    public static PathFind getOnALayerOfPathFind(PathFind pathFind, int i) {
        PathFind thePathFind = new PathFind(pathFind.getMarkType(), pathFind.getMetaType());
        if (i > 0) {
            for (int k = 0; k < i; k++) {
                thePathFind.getPathList().add(pathFind.getPathList().get(k));
            }
        }
        return thePathFind;
    }

    /**
     * 比较两者是否处于同一层级
     *
     * @param pathFind1
     * @param pathFind2
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public static boolean isTheComparisonHierarchical(PathFind pathFind1, PathFind pathFind2) {
        boolean flag = true;
        PathFind bigPathFind, smallPathFind;
        if (pathFind1.getMarkType().equals(pathFind2.getMarkType())) {
            if (pathFind1.getPathList().size() > pathFind2.getPathList().size()) {
                bigPathFind = pathFind1;
                smallPathFind = pathFind2;
            } else {
                bigPathFind = pathFind2;
                smallPathFind = pathFind1;
            }

            PathFindCell smallTemp, bigTemp;
            for (int i = 0; i < smallPathFind.pathList.size(); i++) {
                smallTemp = smallPathFind.pathList.get(i);
                bigTemp = bigPathFind.getPathList().get(i);
                if (bigTemp.getCodeSerialNumber() != smallTemp.getCodeSerialNumber()) {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 查看thePathFindList里面是否包含和pathFind属性相同的元素
     *
     * @param thePathFindList
     * @param pathFind
     * @return
     */
    public static boolean checkContain(ArrayList<PathFind> thePathFindList, PathFind pathFind) {
        boolean flag = false;
        for (PathFind temp : thePathFindList) {
            if (same(temp, pathFind)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断两个PathFind是否完全一样
     *
     * @param pathFind1
     * @param pathFind2
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public static boolean same(PathFind pathFind1, PathFind pathFind2) {
        boolean flag = true;
        if (pathFind1.getMarkType().equals(pathFind2.getMarkType())) {
            if (pathFind1.getPathList().size() == pathFind2.getPathList().size()) {
                PathFindCell pathFindCell1, pathFindCell2;
                for (int i = 0; i < pathFind1.pathList.size(); i++) {
                    pathFindCell1 = pathFind1.getPathList().get(i);
                    pathFindCell2 = pathFind2.getPathList().get(i);
                    if (!(pathFindCell1.getPaneType() == pathFindCell2.getPaneType() &&
                            pathFindCell1.getCodeSerialNumber() == pathFindCell2.getCodeSerialNumber() &&
                            pathFindCell1.getOpratingLabel().match(pathFindCell2.getOpratingLabel()))) {
                        flag = false;
                        break;
                    }
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 是否在列表里面
     *
     * @param list
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean whetherInList(ArrayList<PathFind> list) {
        boolean flag = false;
        for (PathFind pathFind : list) {
            if (same(this, pathFind)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    // public BaseMarkElement getMarkElement() {
    // return markElement;
    // }
    //
    // public void setMarkElement(BaseMarkElement markElement) {
    // this.markElement = markElement;
    // }

    /**
     * 是否直接添加或删除 标记使用（查看 functionAddList 数组有无内容）
     *
     * @return true:直接操作 false：否
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean whetherToOperateAddDelOrNotForMark() {
        boolean flag = false;
        if (this.pathList.size() == 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据控制组件的PathFind查看对应功能是不是在第一层，或者是不是格式功能里面的组件
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean isFirstLayerOpratingContainer() {
        boolean flag = false;
        if (pathList.size() == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 能否直接更改 标记使用
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean whetherToOperateUpdateOrNotForMark() {
        boolean flag = false;
        if (this.pathList.size() == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 是否直接操作 格式使用（查看 functionAddList 数组有无内容）
     *
     * @return true:直接操作 false：否
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean whetherToOperateForFormat() {
        boolean flag = false;
        if (this.pathList.size() == 1) {
            flag = true;
        }
        return flag;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(markType + "   ");
        for (PathFindCell pathFindCell : pathList) {
            out.append(pathFindCell.getCodeSerialNumber() + ":" + pathFindCell.getOpratingLabel().getThisName()
                    + "(" + pathFindCell.getOpratingLabel().getLabelType() + ")   ");
        }
        return out.toString();
    }

}
