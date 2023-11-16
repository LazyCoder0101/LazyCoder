package com.lazycoder.database.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;


public class TheOptionModel extends OptionDataModel {

	/**
	 * 生成选项的名称列表
	 *
	 * @param optionDataModel
	 * @return
	 */
	public static ArrayList<String> getOptionNameList(OptionDataModel optionDataModel) {
		ArrayList<String> optionNameList = JSON.parseObject(optionDataModel.getOptionNameListParam(),
				new TypeReference<ArrayList<String>>() {
				});
		return optionNameList;
	}

	/**
	 * 获取选项值列表
	 *
	 * @param optionDataModel
	 * @param selectGroup
	 * @return
	 */
	public static ArrayList<String> getOptionValueList(OptionDataModel optionDataModel, int selectGroup) {
		ArrayList<ArrayList<String>> optionValueList = JSON.parseObject(optionDataModel.getOptionValueListParam(),
				new TypeReference<ArrayList<ArrayList<String>>>() {
				});
		ArrayList<String> valueList = new ArrayList<>();
		if (optionValueList.size() > selectGroup) {
			valueList = optionValueList.get(selectGroup);
		}
		return valueList;
	}

	/**
	 * 获取选项值
	 *
	 * @param optionDataModel
	 * @param selectGroup
	 * @param selectList
	 * @return
	 */
	public static String getValue(OptionDataModel optionDataModel, int selectGroup, ArrayList<Integer> selectList) {
		StringBuilder out = new StringBuilder("");
		ArrayList<String> valueList = getOptionValueList(optionDataModel, selectGroup);
		if (valueList != null) {
			if (optionDataModel.getOptionType() == OptionDataModel.EXECLUSIVE) {// 单选
				if (selectList.size() > 0) {
					out.append(valueList.get(selectList.get(0)));
				}

			} else if (optionDataModel.getOptionType() == OptionDataModel.MULTIPLE) {
				if (selectList.size() == 1) {
					out.append(valueList.get(selectList.get(0)));

				} else if (selectList.size() > 1) {
					out.append(optionDataModel.getLeftStr() + valueList.get(selectList.get(0))
							+ optionDataModel.getRightStr());
					for (int i = 1; i < selectList.size(); i++) {
						out.append(optionDataModel.getSeparatorStr() + optionDataModel.getLeftStr()
								+ valueList.get(selectList.get(i)) + optionDataModel.getRightStr());
					}
				}
			}
		}
		return out.toString();
	}

}
