package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.ScucheonComponentInterface;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.CodeLabelInterface;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.ControlLabelInterface;
import com.lazycoder.uiutils.utils.MyColoursBorder;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 * 标签组件接口
 *
 * @author Administrator
 */
public interface LabelComponentIntetface extends ScucheonComponentInterface {


    /**
     * 获取标签类型
     *
     * @return
     */
    public String getLabelType();

    /**
     * 获取放置面板参数
     */
    public PassingComponentParams getPassingComponentParams();

    /**
     * 设置放置面板参数
     */
    public void setPassingComponentParams(PassingComponentParams passingComponentParams);

    /**
     * 设置该标记是否选中（该UI标签组件如果选中会怎么显示，取消选中又会怎样）
     */
    public void setNavigate(boolean flag);

    @Override
    public com.lazycoder.service.vo.element.lable.BaseLableElement property();

    /**
     * 添加对应组件响应的监听器（鼠标移到 控制/代码面板里面的标签组件，让其对应代码/控制 面板的对应组件样式发生变化以作响应）
     */
    public static void addCorrespondingComponentResponseListener(LabelComponentIntetface labelComponent) {
        JComponent component;
        if (labelComponent != null) {
            if (labelComponent instanceof Component) {
                component = (JComponent) labelComponent;
                PassingComponentParams params = labelComponent.getPassingComponentParams();
                if (params != null) {
                    AbstractEditContainerModel model = params.getGeneralModel();

                    Border defaultBorder = component.getBorder(), respondBorder = new MyColoursBorder(3);
                    if (model != null) {
                        component.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                super.mouseEntered(e);
                                if (labelComponent instanceof CodeLabelInterface) {
                                    ArrayList<BaseOperatingPane> list = model.getAllControlPaneList();
                                    for (BaseOperatingPane operatingPane : list) {
                                        //在这里写让每个控制面板响应的方法
                                        operatingPane.makeCorrespondingLabelScutcheonRespond(labelComponent.property(), respondBorder);
                                    }
                                } else if (labelComponent instanceof ControlLabelInterface) {
                                    ArrayList<BaseCodePane> list = model.getCodePaneList();
                                    for (BaseCodePane codePane : list) {
                                        codePane.makeCorrespondingLabelScutcheonRespond(labelComponent.property(), respondBorder);
                                    }
                                }
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                super.mouseExited(e);
                                if (labelComponent instanceof CodeLabelInterface) {
                                    ArrayList<BaseOperatingPane> list = model.getAllControlPaneList();
                                    for (BaseOperatingPane operatingPane : list) {
                                        //在这里写让每个控制面板响应的方法
                                        operatingPane.makeCorrespondingLabelScutcheonRespond(labelComponent.property(), defaultBorder);
                                    }
                                } else if (labelComponent instanceof ControlLabelInterface) {
                                    ArrayList<BaseCodePane> list = model.getCodePaneList();
                                    for (BaseCodePane codePane : list) {
                                        codePane.makeCorrespondingLabelScutcheonRespond(labelComponent.property(), defaultBorder);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

}
