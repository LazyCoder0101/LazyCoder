package com.lazycoder.uidatasourceedit.moduleedit.codepane;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.component.CodeFileEditPaneInterface;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;


public class UseModuleFileEditPane extends JSplitPane implements CodeFileEditPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = 2285098457868870897L;

    /**
     * 分割比例
     */
    private double dividerLocation = 0.5;

    private NeedUseCodeFileEditPane needUseCodeFileEditPane;

    private ModuleFilePutCodePane moduleUseCodeFileEditPane;

    private ComponentAdapter cAdapter = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            setDividerLocation(dividerLocation);
        }
    };

    public UseModuleFileEditPane() {
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 设置分割线方向
        setBorder(BorderFactory.createEmptyBorder());
        setContinuousLayout(true);
        setOneTouchExpandable(true);
        addComponentListener(cAdapter);

        needUseCodeFileEditPane = new NeedUseCodeFileEditPane();
        ModuleEditPaneHolder.needUseCodeFileEditPane = this.needUseCodeFileEditPane;
        setLeftComponent(needUseCodeFileEditPane);

        moduleUseCodeFileEditPane = new ModuleFilePutCodePane();
        ModuleEditPaneHolder.moduleFilePutCodePane = moduleUseCodeFileEditPane;
        setRightComponent(moduleUseCodeFileEditPane);
    }

    /**
     * 获取当前代码格式参数
     *
     * @return
     */
    public ArrayList<CodeFormatFlagParam> getCurrentCodeFormatParam() {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>(),
                list1 = needUseCodeFileEditPane.getCurrentCodeFormatParam(),
                list2 = moduleUseCodeFileEditPane.getCurrentCodeFormatParam(), list3;
        list3 = needUseCodeFileEditPane.sortCodeFormatParamList(list1);

        list.addAll(list2);
        list.addAll(list3);
        return list;
    }

    /**
     * 定位到对应的标志
     */
    @Override
    public boolean navigateToTheCorrespondingMark(ArrayList<CodeFormatFlagParam> list, BaseMarkElement thanMarkElement,
                                                  boolean navigateOrNot) {
        boolean flag = true;
        for (CodeFormatFlagParam temp : list) {
            if (temp.getFormatType() == CodeFormatFlagParam.MODULE_TYPE) {
                if (DataSourceEditHolder.currentModule.getModuleId().equals(temp.getModuleId())) {
                    flag = ModuleEditPaneHolder.moduleFilePutCodePane.navigateToTheCorrespondingMark(list,
                            thanMarkElement, navigateOrNot);
                } else {
                    flag = ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToTheCorrespondingMark(list,
                            thanMarkElement, navigateOrNot);
                }
            } else {
                flag = ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToTheCorrespondingMark(list,
                        thanMarkElement, navigateOrNot);
            }
        }
        return flag;
    }

    /**
     * 定位到方法标记
     *
     * @param navigateOrNot
     * @return
     */
    public boolean navigateToFunctionMark(boolean navigateOrNot) {
        return ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToFunctionMark(navigateOrNot);
    }

}
