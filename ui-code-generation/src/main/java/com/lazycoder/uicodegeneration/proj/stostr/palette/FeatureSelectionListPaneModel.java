package com.lazycoder.uicodegeneration.proj.stostr.palette;

import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.ModuleInfo;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeatureSelectionListPaneModel implements DataFormatType {

	private int type = MAIN_TYPE;

	/**
	 * 可选模板序号
	 */
	private int additionalSerialNumber = 0;

	/**
	 * 显示名称
	 */
	private String showTabName = "";

	private ArrayList<ModuleInfo> moduleInfoList = new ArrayList<>();

}
