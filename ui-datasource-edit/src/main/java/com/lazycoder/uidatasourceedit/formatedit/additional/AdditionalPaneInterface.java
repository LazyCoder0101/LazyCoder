package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;

public interface AdditionalPaneInterface {

	/**
	 * 显示可选模板内容
	 */
	public void displayAdditionalContent(AdditionalInfo additionalInfo, AdditionalMetaModel additionalMetaModel);

}
