package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon;

import com.lazycoder.service.vo.base.BaseElement;

/**
 * 在代码面板和控制面板添加的组件都要继承的基本接口
 */
public interface ScucheonComponentInterface {


	/**
	 * 从面板上删除该组件需要的操作
	 */
	public void deleteFromPanel();

	/**
	 * 获取属性值
	 *
	 * @return
	 */
	public BaseElement property();

}
