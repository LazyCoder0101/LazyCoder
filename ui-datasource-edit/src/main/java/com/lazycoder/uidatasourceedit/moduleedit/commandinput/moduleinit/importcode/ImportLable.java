package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode;

import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import lombok.Getter;


public class ImportLable extends JLabel {

	/**
	 *
	 */
	private static final long serialVersionUID = -510902441421283806L;

	@Getter
	private ImportMarkElement markElement;

	private int ordinal;

	public ImportLable() {
		// TODO Auto-generated constructor stub
		super();
		addMouseListener(adapter);
	}

	private MouseAdapter adapter = new MouseAdapter() {

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			setForeground(Color.BLUE);
			boolean flag1 = ModuleEditPaneHolder.needUseCodeFileEditPane
					.navigateToTheCorrespondingImportMark(markElement, true);
			if (flag1 == false) {
				setToolTipText("ε≡٩(๑>₃<)۶  记得添加对应标记，现在还没有哪个代码文件有添加的");
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			setForeground(Color.BLACK);
//			ModuleEditPaneHolder.moduleFilePutCodePane.navigateToTheCorrespondingImportMark(markElement, false);
			ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToTheCorrespondingImportMark(markElement, false);
		}
	};


	public void setLabelText(int ordinal) {
		setText("引入" + ordinal + "：");
		this.ordinal = ordinal;

		markElement = new ImportMarkElement();
		markElement.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
		markElement.setOrdinal(ordinal);
	}

}
