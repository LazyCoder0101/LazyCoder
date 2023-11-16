package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.element.lable.control.functionadd.FunctionAddCodeModel;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.MethodAddFunctionCodeTier;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;

public class MethodAddFunctionCodeCabinet extends AbstractCodeCabinet {

    /**
     *
     */
    private static final long serialVersionUID = 1085381985032771779L;

    private int operatingOrdinalNumber=0;

    public MethodAddFunctionCodeCabinet(int operatingOrdinalNumber, ContainerModel theModel, boolean expanded, double proportion) {
        super(expanded, proportion);
        this.operatingOrdinalNumber = operatingOrdinalNumber;
    }

    /**
     * 还原内容
     *
     * @param methodAddStorageFormat
     */
    public void restoreContent(ContainerModel theModel, MethodAddStorageFormat methodAddStorageFormat) {
//        if (null != methodAddStorageFormat.getCodeStatementFormat()) {
//            if (methodAddStorageFormat.getCodeStatementFormat().equals("") == false) {
//                FunctionAddCodeModel functionAddCodeModel = new FunctionAddCodeModel();
//                functionAddCodeModel.setCodeFormatParam(methodAddStorageFormat.getCodeStatementFormat());
//                functionAddCodeModel.setOrdinal(methodAddStorageFormat.getOrdinal());
//                int ii = getCodeNum() + 1;
//                functionAddCodeModel.setCodeOrdinal(ii);
//                methodAddStorageFormat.getCodeModelList().add(functionAddCodeModel);
//            }
//        }

        MethodAddFunctionCodeTier functionCodeInputPane;
        for (FunctionAddCodeModel functionAddCodeModelTemp : methodAddStorageFormat.getCodeModelList()) {
            functionCodeInputPane = new MethodAddFunctionCodeTier(this.operatingOrdinalNumber, functionAddCodeModelTemp.getCodeOrdinal(), theModel);
            functionCodeInputPane.restoreContent(functionAddCodeModelTemp);
            getVBox().add(functionCodeInputPane);
        }
    }

    @Override
    public void addCodePane(ContainerModel theModel) {
        int currentNum = getCodeNum() + 1;
        //setCodeNum(currentNum);

        MethodAddFunctionCodeTier functionCodeInputPane = new MethodAddFunctionCodeTier(this.operatingOrdinalNumber, currentNum, theModel);
        getVBox().add(functionCodeInputPane);

        CommandCodeControl.updateCodePaneMenu(theModel);
        SysUtil.scrollToBottom(scrollPane);
    }

    /**
     * 删除组件
     */
    public void deleteComponent() {
        MethodAddFunctionCodeTier temp;
        for (int i = 0; i < getVBox().getComponentCount(); i++) {
            temp = (MethodAddFunctionCodeTier) getVBox().getComponent(i);
            temp.deleteComponent();
        }
    }

    /**
     * 获取代码模型列表
     *
     * @return
     */
    public ArrayList<FunctionAddCodeModel> getCodeModelList() {
        ArrayList<FunctionAddCodeModel> list = new ArrayList<>();
        MethodAddFunctionCodeTier tempPane;
        FunctionAddCodeModel temp;
        int index = 0;
        for (int i = 0; i < getVBox().getComponentCount(); i++) {
            index++;
            tempPane = (MethodAddFunctionCodeTier) getVBox().getComponent(i);
            temp = tempPane.getCodeModel();
            temp.setOrdinal(this.operatingOrdinalNumber);
            temp.setCodeOrdinal(index);
            list.add(temp);
        }
        return list;
    }

}
