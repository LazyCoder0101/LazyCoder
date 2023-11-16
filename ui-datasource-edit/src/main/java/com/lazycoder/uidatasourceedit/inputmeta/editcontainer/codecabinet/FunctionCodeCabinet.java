package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.FunctionCodeTier;
import com.lazycoder.uiutils.utils.SysUtil;

import java.util.ArrayList;

public class FunctionCodeCabinet extends AbstractCodeCabinet {

    /**
     *
     */
    private static final long serialVersionUID = 550887218673845306L;

    private int typeSerialNumber;

    /**
     * @param typeSerialNumber
     * @param theModel
     * @param operatingOrdinalNumber
     * @param expanded
     * @param proportion
     */
    public FunctionCodeCabinet(int typeSerialNumber, ContainerModel theModel, int operatingOrdinalNumber, boolean expanded,
                               double proportion) {
        super(expanded, proportion);
        this.operatingOrdinalNumber = operatingOrdinalNumber;
        this.typeSerialNumber = typeSerialNumber;

    }

    /**
     * 获取代码模型列表
     *
     * @return
     */
    public ArrayList<FunctionCodeModel> getCodeModelList() {
        ArrayList<FunctionCodeModel> list = new ArrayList<>();
        FunctionCodeTier temp;
        FunctionCodeModel model;
        if (DataSourceEditHolder.currentModule != null) {
            for (int i = 0; i < getVBox().getComponentCount(); i++) {
                temp = (FunctionCodeTier) getVBox().getComponent(i);
                model = temp.getCodeModel();
                model.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
                model.setOrdinal(operatingOrdinalNumber);
                model.setTypeSerialNumber(this.typeSerialNumber);
                model.setCodeOrdinal(i + 1);
                list.add(model);
            }
        }
        return list;
    }

    @Override
    public void addCodePane(ContainerModel theModel) {
        int currenNum = getCodeNum() + 1;
        //setCodeNum(currenNum);

        FunctionCodeTier functionCodeInputPane = new FunctionCodeTier(typeSerialNumber, this.operatingOrdinalNumber, currenNum, theModel);
        getVBox().add(functionCodeInputPane);

        CommandCodeControl.updateCodePaneMenu(theModel);
        SysUtil.scrollToBottom(scrollPane);
    }

    /**
     * 还原内容
     *
     * @param codeModelList
     * @param theModel
     */
    public void reductionContent(ArrayList<FunctionCodeModel> codeModelList, ContainerModel theModel,
                                 int operatingOrdinal) {
        // TODO Auto-generated method stub
        this.operatingOrdinalNumber = operatingOrdinal;
        FunctionCodeTier functionCodeInputPane;
        if (codeModelList != null) {
            for (FunctionCodeModel temp : codeModelList) {
                //setCodeNum(temp.getCodeOrdinal());
                functionCodeInputPane = new FunctionCodeTier(typeSerialNumber, this.operatingOrdinalNumber, temp.getCodeOrdinal(), theModel);
                functionCodeInputPane.reductionContent(temp);
                getVBox().add(functionCodeInputPane);

            }
        }
//		this.updateUI();
//		this.repaint();
    }

}
