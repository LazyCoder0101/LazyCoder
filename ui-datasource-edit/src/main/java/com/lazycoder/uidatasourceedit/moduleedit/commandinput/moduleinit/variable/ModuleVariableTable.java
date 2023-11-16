package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.variable;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uidatasourceedit.component.component.table.variable.AbstractVariableTable;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class ModuleVariableTable extends AbstractVariableTable
        implements CheckInterface, ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -6449178917706136513L;

    /**
     * 获取录入的模块变量数据
     *
     * @return
     */
    public ArrayList<VariableData> getVariableDataList() {
        ArrayList<VariableData> list = getVariableDataList(VariableData.MODULE_TYPE);
        for (VariableData variableData : list) {
            variableData.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        }
        return list;
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        if (moduleInfo.getNumOfVariable() > 0) {// 此前添加过数据
            List<VariableData> list = SysService.VARIABLE_DATA_SERVICE
                    .getModuleVariableDataList(DataSourceEditHolder.currentModule.getModuleId());
            displayContents(list);
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        clear();
    }

    @Override
    public VariableUsageRangeCombobox getVariableUsageRangeCombobox() {
        // TODO Auto-generated method stub
        VariableUsageRangeCombobox comboBox = new VariableUsageRangeCombobox();
        DefaultComboBoxModel<AttributeUsageRange> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisModule);
        comboBoxModel.addElement(AttributeUsageRange.ModulesRequiredForThisModule);
        comboBoxModel.addElement(AttributeUsageRange.ThisModuleAndRequiredModules);
        comboBoxModel.addElement(AttributeUsageRange.ApplyToAll);
        comboBox.setModel(comboBoxModel);
        return comboBox;
    }

}
