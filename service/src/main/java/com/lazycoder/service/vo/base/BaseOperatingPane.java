package com.lazycoder.service.vo.base;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import java.io.File;
import java.util.ArrayList;
import javax.swing.border.Border;

public interface BaseOperatingPane extends BasePane {

	/**
	 * 更改选项值
	 *
	 * @param option
	 */
	public void updateItems(OptionDataModel option);

	/**
	 * 删除选项
	 *
	 * @param optionId
	 */
	public void delContentChoose(String optionId);

	/**
	 * 该面板对应的图片存放根路径（需要使用该参数的面板要重写此方法）
	 *
	 * @return
	 */
	public File getImageRootPath();

	/**
	 * 该面板对应的文件选择组件的文件存放根路径（需要使用该参数的面板要重写此方法）
	 *
	 * @return
	 */
	public File getFileSelectorRootPath();

	/**
	 * 获取面板上的组件对应的模型
	 *
	 * @return
	 */
	public ArrayList<BaseElementInterface> getTheSpecifiedLabelModels(ArrayList<Class<?>> specifiedClassList);


	/**
	 * 根据代码模型获取内容选择控制组件对应的模型
	 *
	 * @param contentChooseCodeElement
	 * @return
	 */
	public ContentChooseControl getContentChooseControlModel(ContentChooseCodeElement contentChooseCodeElement);

	/**
	 * 让对应的标签组件响应
	 * @param lableElement
	 * @param border
	 */
	public void makeCorrespondingLabelScutcheonRespond(BaseLableElement lableElement, Border border);

}
