package com.lazycoder.uidatasourceedit.formatedit.main;

import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.VariableDataServiceImpl;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uidatasourceedit.component.component.table.variable.AbstractVariableTable;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class MainVariableTable extends AbstractVariableTable implements CheckInterface, MainPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = -7195345825883346770L;

    private final VariableDataServiceImpl variableDataServiceImpl = SysService.VARIABLE_DATA_SERVICE;

    public ArrayList<VariableData> getVariableDataList() {
        ArrayList<VariableData> list = getVariableDataList(VariableData.MAIN_TYPE);
        return list;
    }

    @Override
    public VariableUsageRangeCombobox getVariableUsageRangeCombobox() {
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
        if (mainInfo.getNumOfVariable() > 0) {
            List<VariableData> list = variableDataServiceImpl.getMainVariableDataList();
            displayContents(list);
        }
    }

}
