package com.lazycoder.uicodegeneration.component.operation;

import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import java.util.ArrayList;


/**
 * 遍历使用的业务方法
 *
 * @author admin
 */
public interface BusinessTraverse {

	/**
	 * 收起自身以及里面的组件所有的组件
	 */
	public void collapseThis();

	/**
	 * 删除某个模块所有的代码
	 *
	 * @param moduleId
	 */
	public void delModuleOpratingContainerFromComponent(String moduleId);

	/**
	 * 删掉自己的时候调用
	 */
	public void delThis();

	/**
	 * 获取这个组件里面的所有的功能容器
	 *
	 * @return
	 */
	public ArrayList<OpratingContainerInterface> getAllOpratingContainer();

	/**
	 * 收起隐藏面板
	 * @param currentOpratingContainer
	 */
//	public void autoCollapse(OpratingContainerInterface currentOpratingContainer);

}
