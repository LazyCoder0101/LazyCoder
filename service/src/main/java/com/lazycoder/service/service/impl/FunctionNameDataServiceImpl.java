package com.lazycoder.service.service.impl;

import com.lazycoder.database.dao.FunctionNameDataMapper;
import com.lazycoder.database.model.FunctionNameData;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "FunctionNameDataServiceImpl")
public class FunctionNameDataServiceImpl {

	@Autowired
	private FunctionNameDataMapper dao;

	/**
	 * 保存多个模块变量
	 */
	public void saveModuleFunctionNameList(List<FunctionNameData> functionNameDataList, String moduleId) {
		dao.delFunctionNameDataList(moduleId, 0, FunctionNameData.MODULE_TYPE);
		if (functionNameDataList.size() > 0) {
			dao.addFunctionNameDataList(functionNameDataList);
		}
	}

	public void saveAdditionalFunctionNameList(List<FunctionNameData> functionNameDataList, int additionalSerialNumber) {
		dao.delFunctionNameDataList(null, additionalSerialNumber, FunctionNameData.ADDITIONAL_TYPE);
		if (functionNameDataList.size() > 0) {
			dao.addFunctionNameDataList(functionNameDataList);
		}
	}

	public void saveMainFunctionNameList(List<FunctionNameData> functionNameDataList) {
		dao.delFunctionNameDataList(null, 0, FunctionNameData.MAIN_TYPE);
		if (functionNameDataList.size() > 0) {
			dao.addFunctionNameDataList(functionNameDataList);
		}
	}

	/**
	 * 获取模块变量列表
	 *
	 * @param moduleId
	 * @return
	 */
	public List<FunctionNameData> getModuleFunctionNameDataList(String moduleId) {
		return dao.getFunctionNameDataList(moduleId, 0, FunctionNameData.MODULE_TYPE);
	}

	/**
	 * 获取其他变量
	 *
	 * @param additionalSerialNumber
	 * @return
	 */
	public List<FunctionNameData> getAdditionalFunctionNameDataList(int additionalSerialNumber) {
		return dao.getFunctionNameDataList(null, additionalSerialNumber, FunctionNameData.ADDITIONAL_TYPE);
	}

	/**
	 * 获取主变量
	 *
	 * @return
	 */
	public List<FunctionNameData> getMainFunctionNameDataList() {
		return dao.getFunctionNameDataList(null, 0, FunctionNameData.MAIN_TYPE);
	}

	public void delDataOfModule(String moduleId) {
		dao.delFunctionNameDataList(moduleId, 0, FunctionNameData.MODULE_TYPE);
	}


}
