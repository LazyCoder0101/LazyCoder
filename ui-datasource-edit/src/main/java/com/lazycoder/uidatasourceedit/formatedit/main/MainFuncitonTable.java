package com.lazycoder.uidatasourceedit.formatedit.main;

import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.FunctionNameDataServiceImpl;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uidatasourceedit.component.component.table.functionname.AbstractFunctionTable;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class MainFuncitonTable extends AbstractFunctionTable implements CheckInterface, MainPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = 6796839450732930148L;

    private final FunctionNameDataServiceImpl functionNameDataServiceImpl = SysService.FUNCTION_NAME_DATA_SERVICE;

    public ArrayList<FunctionNameData> getFunctionNameDataList() {
        return getFunctionNameDataList(FunctionNameData.MAIN_TYPE);
    }

    @Override
    public VariableUsageRangeCombobox getFunctionNameUsageRangeCombobox() {
        // TODO Auto-generated method stub
        VariableUsageRangeCombobox comboBox = new VariableUsageRangeCombobox();
        DefaultComboBoxModel<AttributeUsageRange> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToAddedTemplates);
        comboBoxModel.addElement(AttributeUsageRange.ApplyToAll);
        comboBox.setModel(comboBoxModel);
        return comboBox;
    }

    @Override
    public void displayMainContent(MainInfo mainInfo) {
        // TODO Auto-generated method stub
        List<FunctionNameData> list = functionNameDataServiceImpl.getMainFunctionNameDataList();
        displayContents(list);
    }

}