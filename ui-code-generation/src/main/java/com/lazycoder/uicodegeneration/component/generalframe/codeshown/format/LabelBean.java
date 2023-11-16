package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format;

import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.pathfind.PathFindCell;

public interface LabelBean extends BaseBean {

	/**
	 * 获取标签类型
	 *
	 * @return
	 */
	public String getLabelType();

	/**
	 * 获取该标签的名字
	 *
	 * @return
	 */
	public String getThisName();

	/**
	 * 匹配
	 *
	 * @param opratingLabel 操作层标签
	 */
	public boolean match(BaseLableElement opratingLabel);

	/**
	 * 匹配（该方法只供给代码元素使用）
	 *
	 * @param pathFindCell
	 * @return
	 */
	public boolean match(PathFindCell pathFindCell);

	/**
	 * 设置元素组件的内部值（建议组件用这个，而不是 updateBeanValue(Object updateParam);）
	 *
	 * @param opratingLabel
	 * @param updateParam
	 */
	public void updateBeanValue(BaseLableElement opratingLabel, Object updateParam);

}
