package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.service.vo.save.AdditionalVariableInputData;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uidatasourceedit.component.component.table.variable.AbstractVariableTable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class AdditionalVariableTable extends AbstractVariableTable implements AdditionalPaneInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -4853757859131285948L;

	private int additionalSerialNumber = 0;

	public AdditionalVariableTable(int additionalSerialNumber) {
		// TODO Auto-generated constructor stub
		super();
		this.additionalSerialNumber = additionalSerialNumber;
	}

	/**
	 * 获取录入的可选模板的变量相关数据
	 * @return
	 */
	public AdditionalVariableInputData getAdditionalVariableInputData(){
		AdditionalVariableInputData additionalVariableInputData = new AdditionalVariableInputData();
		additionalVariableInputData.setAdditionalSerialNumber(additionalSerialNumber);
		ArrayList<VariableData> variableDataList = getVariableDataList(VariableData.ADDITIONAL_TYPE);
		for(VariableData variableData:variableDataList){
			variableData.setAdditionalSerialNumber(additionalSerialNumber);
		}
		additionalVariableInputData.setVariableDataList(variableDataList);
		return additionalVariableInputData;
	}

	@Override
	public VariableUsageRangeCombobox getVariableUsageRangeCombobox() {
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
			List<VariableData> list = SysService.VARIABLE_DATA_SERVICE.getAdditionalVariableDataList(additionalSerialNumber);
			displayContents(list);
		}
	}

}
