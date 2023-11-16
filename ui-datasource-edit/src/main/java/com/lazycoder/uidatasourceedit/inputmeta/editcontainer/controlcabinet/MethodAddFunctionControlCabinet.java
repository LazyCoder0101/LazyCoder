package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.FunctionAddControlInputPane;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MethodAddFunctionControlCabinet extends AbstractControlCabinet {

    /**
     *
     */
    private static final long serialVersionUID = 4532687404862027609L;

    private FunctionAddControl controlElement;

    private FunctionAddParam functionAddParam;

    /**
     * 专门给功能拓展
     *
     * @param theModel
     * @param expanded
     * @param proportion
     * @param controlElement
     * @param functionAddParam
     */
    public MethodAddFunctionControlCabinet(ContainerModel theModel, int operatingOrdinalNumber, boolean expanded, double proportion,
                                           FunctionAddControl controlElement, FunctionAddParam functionAddParam) {
        super();
        this.controlElement = controlElement;
        this.functionAddParam = functionAddParam;
        init(theModel, operatingOrdinalNumber, expanded, proportion);
        ((FunctionAddControlInputPane) derectInputPanel).setParam(controlElement, functionAddParam);
        ((FunctionAddControlInputPane) hiddenInputPane).setParam(controlElement, functionAddParam);

    }

    @Override
    public void uiInit(boolean expanded, double proportion) {
        derectInputPanel = new FunctionAddControlInputPane();
        hiddenInputPane = new FunctionAddControlInputPane();
        super.uiInit(expanded, proportion);
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (functionNameTextField.check() == false) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   第" + (operatingOrdinalNumber + 1) + "个内部方法没写名称", "系统信息",
                    JOptionPane.PLAIN_MESSAGE);
        }
        if (flag == true) {
            if (derectInputPanel.getUseState() == false) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   第" + (operatingOrdinalNumber + 1) + "个内部方法的默认操作没写任何内容", "系统信息",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
        return flag;
    }

    /**
     * 还原内容
     *
     * @param methodAddStorageFormat
     */
    public void restoreContent(MethodAddStorageFormat methodAddStorageFormat) {
        functionNameTextField.setText(methodAddStorageFormat.getShowText());

        ArrayList<BaseElementInterface> derectCodeElementList = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getDefaultControlStatementFormat());
        derectInputPanel.reductionContent(derectCodeElementList);

        ArrayList<BaseElementInterface> hiddenCodeElementList = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getHiddenControlStatementFormat());
        hiddenInputPane.reductionContent(hiddenCodeElementList);

        if (methodAddStorageFormat.getHiddenState() == BaseModel.FALSE_) {
            packUpHiddenPanel();
        }
    }

    /**
     * 删除里面所有组件
     */
    public void deleteComponent() {
        derectInputPanel.deleteAllComponents();
        hiddenInputPane.deleteAllComponents();
    }

}
