package com.lazycoder.database.model.general;

public interface CommandCodeModelInterface {

	/**
	 * 查看是否有使用这个代码标签
	 * @param codeLabelId 代码标签的对应id
	 */
	public Integer selectExistCodeLabelIdUsed(String codeLabelId);

}
