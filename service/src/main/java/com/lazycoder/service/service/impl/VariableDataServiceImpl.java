package com.lazycoder.service.service.impl;

import com.lazycoder.database.dao.VariableDataMapper;
import com.lazycoder.database.model.VariableData;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "VariableDataServiceImpl")
public class VariableDataServiceImpl {

	@Autowired
	private VariableDataMapper dao;

	/**
	 * 保存多个模块变量
	 */
	public void saveModuleVariableList(List<VariableData> variableDataList, String moduleId) {
		dao.delVariableList(moduleId, 0, VariableData.MODULE_TYPE);
		if (variableDataList.size() > 0) {
			dao.addVariableList(variableDataList);
		}
	}

	public void saveAdditionalVariableList(List<VariableData> variableDataList, int additionalSerialNumber) {
		dao.delVariableList("", additionalSerialNumber, VariableData.ADDITIONAL_TYPE);
		if (variableDataList.size() > 0) {
			dao.addVariableList(variableDataList);
		}
	}

	public void saveMainVariableList(List<VariableData> variableDataList) {
		dao.delVariableList(null, 0, VariableData.MAIN_TYPE);
		if (variableDataList.size() > 0) {
			dao.addVariableList(variableDataList);
		}
	}

	/**
	 * 获取模块变量列表
	 *
	 * @param moduleId
	 * @return
	 */
	public List<VariableData> getModuleVariableDataList(String moduleId) {
		return dao.getVariableDataList(moduleId, 0, VariableData.MODULE_TYPE);
	}

	/**
	 * 获取其他变量
	 *
	 * @param additionalSerialNumber
	 * @return
	 */
	public List<VariableData> getAdditionalVariableDataList(int additionalSerialNumber) {
		return dao.getVariableDataList(null, additionalSerialNumber, VariableData.ADDITIONAL_TYPE);
	}

	/**
	 * 获取主变量
	 *
	 * @return
	 */
	public List<VariableData> getMainVariableDataList() {
		return dao.getVariableDataList("", 0, VariableData.MAIN_TYPE);
	}


	public void delDataOfModule(String moduleId) {
		dao.delVariableList(moduleId, 0, VariableData.MODULE_TYPE);
	}


}
