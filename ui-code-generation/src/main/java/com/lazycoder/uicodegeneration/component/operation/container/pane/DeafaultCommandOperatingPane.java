package com.lazycoder.uicodegeneration.component.operation.container.pane;

import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import java.util.ArrayList;

public class DeafaultCommandOperatingPane extends AbstractCommandOperatingPane {

    /**
     *
     */
    private static final long serialVersionUID = 1175924060954606802L;

    /**
     * 						字体大小			像素
     */
    public final static int FONT_SIZE = 12,PX=16;

    public DeafaultCommandOperatingPane(OpratingContainerInterface opratingContainer) {
        // TODO Auto-generated constructor stub
        super(opratingContainer);
//        setUI(new MyFlatTextPaneUI());//重写了个UI类用于处理圆角边框四个圆角位置还有方角的问题，没能成功
    }

    @Override
    public void generateOperationalContent(GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        // TODO Auto-generated method stub
        codeGenerationalOpratingContainerParam.setPaneType(PathFindCell.DEFAULT_TYPE);
        super.generateOperationalContent(codeGenerationalOpratingContainerParam);
    }

    @Override
    public void restoreContent(ArrayList<AbstractOpratingPaneElement> paneElementList,
                               GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        codeGenerationalOpratingContainerParam.setPaneType(PathFindCell.DEFAULT_TYPE);
        super.restoreContent(paneElementList, codeGenerationalOpratingContainerParam);
    }

}
