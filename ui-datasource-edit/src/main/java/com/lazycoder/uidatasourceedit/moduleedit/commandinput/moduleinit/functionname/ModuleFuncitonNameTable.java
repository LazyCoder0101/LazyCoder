package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.functionname;

import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.FunctionNameDataServiceImpl;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uidatasourceedit.component.component.table.functionname.AbstractFunctionTable;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;


public class ModuleFuncitonNameTable extends AbstractFunctionTable
        implements CheckInterface, ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -6449178917706136513L;

    private final FunctionNameDataServiceImpl functionNameDataServiceImpl = SysService.FUNCTION_NAME_DATA_SERVICE;

    /**
     * 获取录入模块的方法名
     *
     * @return
     */
    public ArrayList<FunctionNameData> getFunctionNameDataList() {
        ArrayList<FunctionNameData> list = getFunctionNameDataList(FunctionNameData.MODULE_TYPE);
        for (FunctionNameData variableData : list) {
            variableData.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            variableData.setClassName(DataSourceEditHolder.currentModule.getClassName());
            variableData.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
        }
        return list;
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        if (moduleInfo.getNumOfFunctionName() > 0) {// 此前添加过数据
            List<FunctionNameData> list = functionNameDataServiceImpl
                    .getModuleFunctionNameDataList(DataSourceEditHolder.currentModule.getModuleId());
            displayContents(list);
        }
    }


    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        clear();
    }

    @Override
    public VariableUsageRangeCombobox getFunctionNameUsageRangeCombobox() {
        // TODO Auto-generated method stub
        VariableUsageRangeCombobox comboBox = new VariableUsageRangeCombobox();
        DefaultComboBoxModel<AttributeUsageRange> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisModule);
        comboBoxModel.addElement(AttributeUsageRange.ApplyToAll);
        comboBox.setModel(comboBoxModel);
        return comboBox;
    }

}
