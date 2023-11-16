package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit;

import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class InitPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1694935098479143818L;

	private InitFolderPane initFolderPane;

	/**
	 * Create the panel.
	 */
	public InitPane() {
		setLayout(new BorderLayout());

		initFolderPane = new InitFolderPane();
		ModuleEditPaneHolder.initFolderPane = initFolderPane;
		JScrollPane scrollPane = new JScrollPane(initFolderPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, BorderLayout.CENTER);
	}

	public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
		return initFolderPane.getAllEditContainerModel();
	}

}
