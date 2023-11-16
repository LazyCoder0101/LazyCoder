package com.lazycoder.uidatasourceedit.moduleedit.toolbar.needmodule;

import com.lazycoder.database.model.Module;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.GeneralModuleMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NeedModuleMenuItem extends GeneralModuleMenuItem {

	/**
	 *
	 */
	private static final long serialVersionUID = -1337702850361657811L;

	public NeedModuleMenuItem(Module module) {
		// TODO Auto-generated constructor stub
		super(module);
		addActionListener(actionListener);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
			if (flag == true) {
				if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
//					boolean theFlag = ModuleEditPaneHolder.relatedModuleInfoMenu.calculatedParameters(getModule());
//					if (theFlag==false){
//						setSelected(false);
//					}
				}
			}
		}
	};

}
