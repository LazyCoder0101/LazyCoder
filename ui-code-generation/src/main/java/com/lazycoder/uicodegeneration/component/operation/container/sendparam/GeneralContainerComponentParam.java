package com.lazycoder.uicodegeneration.component.operation.container.sendparam;


import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralOperatingContainerParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralContainerComponentParam extends GeneralOperatingContainerParam {

    /**
     * 定位信息
     */
    private PathFind parentPathFind = null;

    /**
     * 代码语句
     */
    private String controlStatementFormat = null;

    private int codeSerialNumber = 0;

    /**
     * 对应放置语句里的组件的面板
     */
    private AbstractOperatingPane operatingComponentPlacePane;

    /**
     * 组件放置面板类型
     */
    private int paneType = PathFindCell.FORMAT_TYPE;

}
