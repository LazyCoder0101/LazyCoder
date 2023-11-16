package com.lazycoder.uicodegeneration.component;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.ModuleCustomVariableHolder;
import java.util.ArrayList;

public class CodeGenerationModuleCustomVariableHolder {

	public static ArrayList<ModuleCustomVariableHolder> moduleCustomVariableList = new ArrayList<>();

	/**
	 * 添加模块
	 *
	 * @param moduleInfo
	 */
	public static void addModule(ModuleInfo moduleInfo) {
		if (moduleInfo != null) {
			ModuleCustomVariableHolder moduleCustomVariableHolder = new ModuleCustomVariableHolder(
					moduleInfo.getClassName(), moduleInfo.getModuleName(),moduleInfo.getModuleId());
			moduleCustomVariableList.add(moduleCustomVariableHolder);
		}
	}


	public static ModuleCustomVariableHolder getModuleCustomVariableHolder(String moduleId) {
		ModuleCustomVariableHolder moduleCustomVariableHolder = null;
		for (ModuleCustomVariableHolder temp : moduleCustomVariableList) {
			if (temp.getModuleId().equals(moduleId)) {
				moduleCustomVariableHolder = temp;
				break;
			}
		}
		return moduleCustomVariableHolder;
	}

	/**
	 * 获取对应模块的自定义变量
	 *
	 * @param useModuleRelatedParamList
	 * @return
	 */
	public static ArrayList<ModuleCustomVariableHolder> getModuleCustomVariableList(ArrayList<ModuleRelatedParam> useModuleRelatedParamList) {
		ArrayList<ModuleCustomVariableHolder> out = new ArrayList<>(), tempAllVariableList = new ArrayList<>();
		tempAllVariableList.addAll(moduleCustomVariableList);

		ModuleCustomVariableHolder tempCustomVariable;
		for (ModuleRelatedParam moduleRelatedParamTemp : useModuleRelatedParamList) {

			for (int i = 0; i < tempAllVariableList.size(); i++) {
				tempCustomVariable = tempAllVariableList.get(i);
				if (moduleRelatedParamTemp.getModuleInfo().getModuleId().equals(tempCustomVariable.getModuleId())) {
					out.add(tempCustomVariable);
					tempAllVariableList.remove(i);
					break;
				}
			}
		}
		return out;
	}


	public static void delModule(String moduleId) {
		ModuleCustomVariableHolder moduleCustomVariableHolder = null;
		for (int i = 0; i < moduleCustomVariableList.size(); i++) {
			moduleCustomVariableHolder = moduleCustomVariableList.get(i);
			if (moduleCustomVariableHolder.getModuleId().equals(moduleId)) {
				moduleCustomVariableList.remove(i);
				break;
			}
		}
	}

}
