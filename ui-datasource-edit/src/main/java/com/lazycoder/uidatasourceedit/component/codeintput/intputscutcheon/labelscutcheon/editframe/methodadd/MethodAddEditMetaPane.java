package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.methodadd;

import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AbstractEditMetaPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.MethodAddFunctionEditContainer;

public class MethodAddEditMetaPane extends AbstractEditMetaPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 6081142281933197578L;

	private MethodAddFunctionEditContainer methodAddFunctionEditContainer;

	/**
	 * 新建
	 */
	public MethodAddEditMetaPane(int ordinal, FunctionAddControl controlElement, FunctionAddParam functionAddParam) {
		// TODO Auto-generated constructor stub
		methodAddFunctionEditContainer = new MethodAddFunctionEditContainer(ordinal, controlElement, functionAddParam);
		setViewportView(methodAddFunctionEditContainer);
	}

	/**
	 * 还原
	 */
	public MethodAddEditMetaPane(MethodAddStorageFormat methodAddStorageFormat, FunctionAddControl controlElement,
								 FunctionAddParam functionAddParam) {
		// TODO Auto-generated constructor stub
		methodAddFunctionEditContainer = new MethodAddFunctionEditContainer(methodAddStorageFormat, controlElement,
				functionAddParam);
		setViewportView(methodAddFunctionEditContainer);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return methodAddFunctionEditContainer.check();
	}

	@Override
	public String getShowText() {
		// TODO Auto-generated method stub
		return methodAddFunctionEditContainer.getShowText();
	}

	/**
	 * 获取整个模型
	 *
	 * @return
	 */
	@Override
	public MethodAddStorageFormat getOperatingModel() {
		return methodAddFunctionEditContainer.getMethodAddStorageFormat();
	}

	/**
	 * 删除组件
	 */
	public void deleteComponent() {
		methodAddFunctionEditContainer.deleteComponent();
	}

	@Override
	public AbstractEditContainerModel getContainerModel() {
		// TODO Auto-generated method stub
		return methodAddFunctionEditContainer.getContainerModel();
	}

}
