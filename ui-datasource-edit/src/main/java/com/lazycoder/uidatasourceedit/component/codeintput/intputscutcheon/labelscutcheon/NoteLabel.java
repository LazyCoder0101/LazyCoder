package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;

public class NoteLabel extends MyButton implements LabelComponentIntetface {

	/**
	 * 注释组件
	 */
	private static final long serialVersionUID = -5375759916135372257L;
	private static ImageIcon noteIcon = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator + "base_code_content_edit" + File.separator
					+ "label_element" + File.separator + "note" + File.separator + "note.png");
	private PassingComponentParams passingComponentParams = null;

	public NoteLabel() {
		// TODO Auto-generated constructor stubthis.setFocusPainted(false);
//		Dimension dd = new Dimension(note_icon.getIconWidth() + 70, note_icon.getIconHeight());
		Dimension dd = new Dimension(noteIcon.getIconWidth() + 70, 30);
		this.setMaximumSize(dd);
		this.setMinimumSize(dd);
		this.setPreferredSize(dd);
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.NOTE;
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
//		label_component_intetface.addCorrespondingComponentResponseListener(this);
	}

	@Override
	public void setNavigate(boolean flag) {}

	@Override
	public BaseLableElement property() {
		return null;
	}

}