package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.MainSetControlPane;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import javax.swing.JOptionPane;

public class MainSetControlCabinet extends AbstractControlCabinet {

    /**
     *
     */
    private static final long serialVersionUID = 5524797858832945070L;

    private int typeSerialNumber = 0;

    public MainSetControlCabinet(int typeSerialNumber, ContainerModel theModel, int ordinal, boolean expanded,
                                 double proportion) {
        super();
        this.typeSerialNumber = typeSerialNumber;
        init(theModel, ordinal, expanded, proportion);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        String typeName = FormatEditPaneHolder.mainFormatEditPane.getMainSetTypeName(typeSerialNumber);
        if (functionNameTextField.check()==false) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   必填模板设置那里，设置" + typeSerialNumber + "，也就是\"" + typeName + "\"分类中，第" + operatingOrdinalNumber + "个必填模板设置方法没写名称",
                    "系统信息", JOptionPane.PLAIN_MESSAGE);
        }
        if (flag == true) {
            if (derectInputPanel.getUseState() == false) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   必填模板\"" + typeName + "分类中，第" + operatingOrdinalNumber + "个默认操作没写任何内容",
                        "系统信息", JOptionPane.PLAIN_MESSAGE);
            }
        }
        return flag;
    }

    @Override
    public void uiInit(boolean expanded, double proportion) {
        derectInputPanel = new MainSetControlPane();
        hiddenInputPane = new MainSetControlPane();
        super.uiInit(expanded, proportion);
    }

}
