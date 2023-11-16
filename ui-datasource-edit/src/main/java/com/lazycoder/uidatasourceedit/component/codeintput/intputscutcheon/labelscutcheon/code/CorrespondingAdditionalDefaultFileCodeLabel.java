package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.element.lable.code.CorrespondingAdditionalDefaultFileCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CorrespondingAdditionalDefaultFileLabel;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CorrespondingAdditionalDefaultFileCodeLabel extends CorrespondingAdditionalDefaultFileLabel implements CodeLabelInterface, BaseModel {


	private CorrespondingAdditionalDefaultFileCodeElement codeElement = new CorrespondingAdditionalDefaultFileCodeElement();


	/**
	 * 新建
	 */
	public CorrespondingAdditionalDefaultFileCodeLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param elementJSONObject
	 */
	public CorrespondingAdditionalDefaultFileCodeLabel(JSONObject elementJSONObject) {
		// TODO Auto-generated constructor stub
		this.codeElement = JsonUtil.restoreByJSONObject(elementJSONObject,CorrespondingAdditionalDefaultFileCodeElement.class);
		init(codeElement.getThisName());
	}

	/**
	 * 还原
	 *
	 * @param codeElement
	 */
	public CorrespondingAdditionalDefaultFileCodeLabel(CorrespondingAdditionalDefaultFileCodeElement codeElement) {
		// TODO Auto-generated constructor stub
		this.codeElement = codeElement;
		init(codeElement.getThisName());
	}

	private void init(String name) {
		setText(name);
		setName(name);
		setUI(new ControlLabelButtonUI());
		addActionListener(listener);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String[] str={"是","否"};
			String selectedStr="是";
			if (FALSE_ ==codeElement.getHaveSuffixOrNot()){
				selectedStr="否";
			}
			Object out = LazyCoderOptionPane.showInputDialog(null, "是否需要填写后缀？", "更改组件属性", 1, null, str, selectedStr);
			if ("是".equals(out)){
				codeElement.setHaveSuffixOrNot(TRUE_);
			}else if("否".equals(out)){
				codeElement.setHaveSuffixOrNot(FALSE_);
			}
		}
	};

	@Override
	public void setName(String name) {
		super.setName(name);
		codeElement.setThisName(name);
	}

	@Override
	public CorrespondingAdditionalDefaultFileCodeElement property() {
		// TODO Auto-generated method stub
		return codeElement;
	}

	@Override
	public void setNavigate(boolean flag) {}

}
