package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;

public interface CodeCabinetInterface {

	/**
	 * 还原代码面板内容
	 */
	public void addCodePane(ContainerModel theModel);

	/**
	 * 获取代码的条数
	 *
	 * @return
	 */
	public int getCodeNum();

}
