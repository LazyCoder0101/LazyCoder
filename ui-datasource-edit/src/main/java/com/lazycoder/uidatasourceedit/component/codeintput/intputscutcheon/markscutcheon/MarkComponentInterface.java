package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon;

import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.ScucheonComponentInterface;

/**
 * 标签组件都要继承的接口
 */
public interface MarkComponentInterface extends ScucheonComponentInterface {

	/**
	 * 获取属性值
	 *
	 * @return
	 */
	@Override
	public BaseMarkElement property();

}
