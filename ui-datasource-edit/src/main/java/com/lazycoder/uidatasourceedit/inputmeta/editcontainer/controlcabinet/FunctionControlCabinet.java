package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.MethodsFunctionControlPanel;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import javax.swing.JOptionPane;

public class FunctionControlCabinet extends AbstractControlCabinet {

    /**
     *
     */
    private static final long serialVersionUID = -6705776471433690540L;

    private int typeSerialNumber = 0;

    public FunctionControlCabinet(int typeSerialNumber, ContainerModel theModel, int operatingOrdinalNumber, boolean expanded,
                                  double proportion) {
        super();
        this.typeSerialNumber = typeSerialNumber;
        init(theModel, operatingOrdinalNumber, expanded, proportion);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void uiInit(boolean expanded, double proportion) {
        derectInputPanel = new MethodsFunctionControlPanel();
        hiddenInputPane = new MethodsFunctionControlPanel();
        super.uiInit(expanded, proportion);
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (functionNameTextField.check()==false) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null,
                    "o(´^｀)o   分类\"" + ModuleEditPaneHolder.moduleFunctionEditPane.getTypeName(typeSerialNumber) + "\"中第"
                            + operatingOrdinalNumber + "个功能没写名称",
                    "系统信息", JOptionPane.PLAIN_MESSAGE);
        }
        if (flag == true) {
            if (derectInputPanel.getUseState() == false) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null,
                        "o(´^｀)o   功能分类\"" + ModuleEditPaneHolder.moduleFunctionEditPane.getTypeName(typeSerialNumber)
                                + "\"中第" + operatingOrdinalNumber + "个默认操作没写任何内容",
                        "系统信息", JOptionPane.PLAIN_MESSAGE);
            }
        }
        return flag;
    }

}
