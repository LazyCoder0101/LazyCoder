package com.lazycoder.uicodegeneration.component;

import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AdditionalFormatFunctionNameHolder;
import java.util.ArrayList;


public class CodeGenerationAdditionalFormatFunctionNameHolder {

	public static ArrayList<AdditionalFormatFunctionNameHolder> additionalFormatFunctionNameHolderList = new ArrayList<>();

	/**
	 * 查看有没有添加过这格式变量
	 *
	 * @param additionalSerialNumber
	 * @return
	 */
	public static boolean checkHaveTheFormatMethodNameOrNot(int additionalSerialNumber) {
		boolean flag = false;
		for (AdditionalFormatFunctionNameHolder additionalFormatFunctionNameHolderTemp : additionalFormatFunctionNameHolderList) {
			if (additionalFormatFunctionNameHolderTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
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
	public static AdditionalFormatFunctionNameHolder getAdditionalFormatMethodNameHolder(int additionalSerialNumber) {
		AdditionalFormatFunctionNameHolder theAdditionalFormatFunctionNameHolder = null;
		for (AdditionalFormatFunctionNameHolder additionalFormatVariableHolderTemp : additionalFormatFunctionNameHolderList) {
			if (additionalFormatVariableHolderTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
				theAdditionalFormatFunctionNameHolder = additionalFormatVariableHolderTemp;
				break;
			}
		}
		return theAdditionalFormatFunctionNameHolder;
	}

	public static void delTheAdditionalFormatMethodName(int additionalSerialNumber) {
		AdditionalFormatFunctionNameHolder additionalFormatFunctionNameHolderTemp;
		for (int i = 0; i < additionalFormatFunctionNameHolderList.size(); i++) {
			additionalFormatFunctionNameHolderTemp = additionalFormatFunctionNameHolderList.get(i);
			if (additionalFormatFunctionNameHolderTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
				additionalFormatFunctionNameHolderList.remove(i);
				break;
			}
		}
	}

}
