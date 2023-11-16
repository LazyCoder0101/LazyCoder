package com.lazycoder.uidatasourceedit.moduleedit.toolbar.nousemodule;

import com.lazycoder.database.model.Module;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.GeneralModuleMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoUseModuleMenuItem extends GeneralModuleMenuItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 3997947288158900027L;


	public NoUseModuleMenuItem(Module module) {
		// TODO Auto-generated constructor stub
		super(module);
		addActionListener(actionListener);
	}

	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
			if (flag == true) {
//				if (ModuleEditPaneHolder.noUseModuleMenu != null) {
//					ModuleEditPaneHolder.noUseModuleMenu.calculatedParameters();
//				}
			}
		}
	};


}
