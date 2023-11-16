package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.FunctionNameDataServiceImpl;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.service.vo.save.AdditionalFunctionNameInputData;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uidatasourceedit.component.component.table.functionname.AbstractFunctionTable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class AdditionalFuncitonTable extends AbstractFunctionTable implements AdditionalPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = -5910704559030656486L;
    private final FunctionNameDataServiceImpl functionNameDataServiceImpl = SysService.FUNCTION_NAME_DATA_SERVICE;
    private int additionalSerialNumber = 0;

    public AdditionalFuncitonTable(int additionalSerialNumber) {
        // TODO Auto-generated constructor stub
        super();
        this.additionalSerialNumber = additionalSerialNumber;
    }

    /**
     * 获取录入的可选模板的方法名相关数据
     *
     * @return
     */
    public AdditionalFunctionNameInputData getAdditionalFunctionNameInputData() {
        AdditionalFunctionNameInputData additionalFunctionNameInputData = new AdditionalFunctionNameInputData();
        additionalFunctionNameInputData.setAdditionalSerialNumber(additionalSerialNumber);
        ArrayList<FunctionNameData> list = getFunctionNameDataList(FunctionNameData.ADDITIONAL_TYPE);
        for (FunctionNameData functionNameData : list) {
            functionNameData.setAdditionalSerialNumber(additionalSerialNumber);
        }
        additionalFunctionNameInputData.setFunctionNameDataList(list);
        return additionalFunctionNameInputData;
    }

    @Override
    public VariableUsageRangeCombobox getFunctionNameUsageRangeCombobox() {
        // TODO Auto-generated method stub
        VariableUsageRangeCombobox comboBox = new VariableUsageRangeCombobox();
        DefaultComboBoxModel<AttributeUsageRange> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate);
        comboBoxModel.addElement(AttributeUsageRange.ApplyToAll);
        comboBox.setModel(comboBoxModel);
        return comboBox;
    }

    @Override
    public void displayAdditionalContent(AdditionalInfo additionalInfo, AdditionalMetaModel additionalMetaModel) {
        // TODO Auto-generated method stub
        if (additionalInfo.getFunctionNameNum() > 0) {
            List<FunctionNameData> list = functionNameDataServiceImpl.getAdditionalFunctionNameDataList(additionalSerialNumber);
            displayContents(list);
        }
    }

}
