package com.lazycoder.uicodegeneration.generalframe.operation.component;

import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.CodeControlPaneBusinessTraverse;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.GeneralCodeControlPaneInterface;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.folder.FolderPane;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import lombok.Getter;
import lombok.Setter;
import org.jb2011.lnf.beautyeye.ch4_scroll.BEScrollPaneUI;

/******
 *
 * @author admin
 *
 */
public abstract class AbstractCodeControlPane extends FolderPane
        implements GeneralCodeControlPaneInterface, CodeControlPaneBusinessTraverse {

    /**
     *
     */
    private static final long serialVersionUID = 5791773657249060168L;

    @Getter
    @Setter
    private PathFind pathFind = null;

    /**
     *
     */
    @Getter
    private AbstractFormatControlPane formatControlPane = null;

    /**
     * 放置该面板的滑动面板，用来设置滑到底部的方法
     */
    @Getter
    @Setter
    protected JScrollPane parentScrollPane = null;

    public AbstractCodeControlPane(AbstractFormatControlPane formatControlPane, int intterTabPadding) {
        super(intterTabPadding);
        this.formatControlPane = formatControlPane;
        // TODO Auto-generated constructor stub
        parentScrollPaneInit();
    }

    protected void parentScrollPaneInit() {
        parentScrollPane = new JScrollPane(this);
        parentScrollPane.setUI(new BEScrollPaneUI());
    }

    public void delCodeControlCommand(Folder delCodeControlCommand) {
        if (delCodeControlCommand.getHiddenButton() != null) {
            delCodeControlCommand.getHiddenButton().removeAll();
        }
        this.remove(delCodeControlCommand);
        tabs.remove(delCodeControlCommand);
        if (getParent() != null) {
            getParent().doLayout();
        }
    }

    /**
     * 滑到底部
     */
    public void scrollToBottom() {
        if (parentScrollPane != null) {
            SysUtil.scrollToBottom(parentScrollPane);
        }
    }


    @Override
    public void setParam(AbstractCodeControlPaneModel model) {
        // TODO Auto-generated method stub
        model.setContainerList(getOperatingContainerListParam());
    }

    /**
     * 把所有功能容器的信息转成参数
     *
     * @return
     */
    protected ArrayList<AbstractOperatingContainerModel> getOperatingContainerListParam() {
        ArrayList<AbstractOperatingContainerModel> list = new ArrayList<>();
        OpratingContainerInterface opratingContainer;
        Component component;
        for (int i = 0; i < getComponentCount(); i++) {
            component = getComponent(i);
            if (component instanceof OpratingContainerInterface) {
                opratingContainer = (OpratingContainerInterface) component;
                list.add(opratingContainer.getFormatStructureModel());
            }
        }
        return list;
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        ArrayList<OpratingContainerInterface> containerList = getAllOpratingContainerListInThisPane();
        for (OpratingContainerInterface container : containerList) {
            opratingContainerList.add(container);
            opratingContainerList.addAll(container.getAllOpratingContainerListInThis());
        }
        return opratingContainerList;
    }

    /**
     * 获取添加在这个面板上的所有的功能容器（不包括容器里面的容器）
     *
     * @return
     */
    public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThisPane() {
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        OpratingContainerInterface opratingContainer;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof OpratingContainerInterface) {
                opratingContainer = (OpratingContainerInterface) getComponent(i);
                opratingContainerList.add(opratingContainer);
            }
        }
        return opratingContainerList;
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        ArrayList<OpratingContainerInterface> containerList = getAllOpratingContainerListInThisPane();
        for (OpratingContainerInterface container : containerList) {
            container.delModuleOpratingContainerFromComponent(moduleId);
        }
    }

    @Override
    public void delThis() {
        ArrayList<OpratingContainerInterface> containerList = getAllOpratingContainerListInThisPane();
        for (OpratingContainerInterface container : containerList) {
            container.delThis();
        }
    }

    @Override
    public void collapseThis() {
        ArrayList<OpratingContainerInterface> containerList = getAllOpratingContainerListInThisPane();
        for (OpratingContainerInterface container : containerList) {
            container.collapseThis();
        }
    }

    /**
     * 收起隐藏面板
     *
     * @param currentOpratingContainer
     */
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
//        if (currentOpratingContainer != null) {
        OpratingContainerInterface opratingContainerTemp;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof OpratingContainerInterface) {
                opratingContainerTemp = (OpratingContainerInterface) getComponent(i);
                opratingContainerTemp.autoCollapse(currentOpratingContainer);
            }
        }
//        }
    }


}
