package com.lazycoder.uicodegeneration.component;

import com.lazycoder.uicodegeneration.generalframe.variable.holder.AdditionalFormatVariableHolder;
import java.util.ArrayList;

public class CodeGenerationAdditionalFormatVariableHolder {

	public static ArrayList<AdditionalFormatVariableHolder> additionalFormatVariableList = new ArrayList<>();

	/**
	 * 查看有没有添加过这格式变量
	 *
	 * @param additionalSerialNumber
	 * @return
	 */
	public static boolean checkHaveTheFormatVariableOrNot(int additionalSerialNumber) {
		boolean flag = false;
		for (AdditionalFormatVariableHolder additionalFormatVariableHolderTemp : additionalFormatVariableList) {
			if (additionalFormatVariableHolderTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 获取所有可选模板格式变量
	 *
	 * @param additionalSerialNumber
	 * @return
	 */
	public static AdditionalFormatVariableHolder getAdditionalFormatVariable(int additionalSerialNumber) {
		AdditionalFormatVariableHolder theAdditionalFormatVariableHolder = null;
		for (AdditionalFormatVariableHolder additionalFormatVariableHolderTemp : additionalFormatVariableList) {
			if (additionalFormatVariableHolderTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
				theAdditionalFormatVariableHolder = additionalFormatVariableHolderTemp;
				break;
			}
		}
		return theAdditionalFormatVariableHolder;
	}

	public static void delTheAdditionalFormatVariable(int additionalSerialNumber) {
		AdditionalFormatVariableHolder additionalFormatVariableHolderTemp;
		for (int i = 0; i < additionalFormatVariableList.size(); i++) {
			additionalFormatVariableHolderTemp = additionalFormatVariableList.get(i);
			if (additionalFormatVariableHolderTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
				additionalFormatVariableList.remove(i);
				break;
			}
		}
	}

}
