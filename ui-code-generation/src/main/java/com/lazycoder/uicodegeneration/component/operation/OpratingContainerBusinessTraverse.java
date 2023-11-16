package com.lazycoder.uicodegeneration.component.operation;

import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import java.util.ArrayList;

public interface OpratingContainerBusinessTraverse extends OperatingPaneBusinessTraverse {

	/**
	 * 获取添加这个功能容器里面所有的功能容器（不包括自身）
	 *
	 * @return
	 */
	public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThis();

	/**
	 * 自动折叠容器组件隐藏面板
	 */
	public void autoCollapse(OpratingContainerInterface currentOpratingContainer);

}
