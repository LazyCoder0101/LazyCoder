package com.lazycoder.database.model.featureSelectionModel;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

@Data
public class InitFeatureSelectonModel extends CommandFeatureSelectionModel {

	private String moduleId;

	public InitFeatureSelectonModel() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 根据JSONObject还原
	 *
	 * @param initFeatureSelectonModelJSONObject
	 * @return
	 */
	public static InitFeatureSelectonModel restoreByJSONObject(JSONObject initFeatureSelectonModelJSONObject) {
		InitFeatureSelectonModel theInitFeatureSelectonModel = JsonUtil
				.restoreByJSONObject(initFeatureSelectonModelJSONObject, InitFeatureSelectonModel.class);
		return theInitFeatureSelectonModel;
	}

}
