package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;

import javax.swing.JScrollPane;

public abstract class AbstractEditMetaPane extends JScrollPane implements CheckInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 2852690067100738089L;

	/**
	 * 获取操作层模型
	 *
	 * @return
	 */
	public abstract GeneralOperatingModel getOperatingModel();


	/**
	 * 获取对应模型
	 *
	 * @return
	 */
	public abstract AbstractEditContainerModel getContainerModel();

	/**
	 * 获取显示文本
	 *
	 * @return
	 */
	public abstract String getShowText();

}
