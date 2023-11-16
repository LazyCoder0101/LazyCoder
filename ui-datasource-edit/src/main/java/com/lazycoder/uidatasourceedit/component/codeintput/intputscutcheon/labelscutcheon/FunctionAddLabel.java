package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;

public abstract class FunctionAddLabel extends MyButton implements LabelComponentIntetface {

	/**
	 *
	 */
	private static final long serialVersionUID = 6383806182052499173L;
	/**
	 * 方法组件
	 */

	protected static ImageIcon moreIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
			+ "FunctionOperationComponent" + File.separator + "FunctionAdd" + File.separator + "more.png");
	private PassingComponentParams passingComponentParams = null;

	public FunctionAddLabel() {
		super(moreIcon);
		Dimension dd = new Dimension(110, 30);
		this.setMaximumSize(dd);
		this.setPreferredSize(dd);
		this.setMinimumSize(dd);
	}


	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}


	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.FUNCTION_ADD;
	}

	@Override
	public PassingComponentParams getPassingComponentParams() {
		// TODO Auto-generated method stub
		return passingComponentParams;
	}

	@Override
	public void setPassingComponentParams(PassingComponentParams passingComponentParams) {
		// TODO Auto-generated method stub
		this.passingComponentParams = passingComponentParams;
		LabelComponentIntetface.addCorrespondingComponentResponseListener(this);
	}

	@Override
	public void setNavigate(boolean flag) {}

}