package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode;

import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.GeneralImportLable;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ImportLable extends GeneralImportLable {

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

	@Override
	public void setLabelText(int ordinal) {
		super.setLabelText(ordinal);
		markElement = new ImportMarkElement();
		markElement.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
		markElement.setOrdinal(ordinal);
	}

}
