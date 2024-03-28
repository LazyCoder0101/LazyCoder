package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode;

import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import org.apache.commons.lang3.StringUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ImportCodeFolderHiddenButton extends FoldButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -7822164353880179167L;

	/**
	 * 是否隐藏面板
	 *
	 * @param expanded
	 */
	public ImportCodeFolderHiddenButton(boolean expanded) {
		super(expanded);
		// TODO Auto-generated constructor stub
		setText("引入代码");
		setUI(new FoldButtonUI());
		addMouseListener(mouseAdapter);
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			if (ModuleEditPaneHolder.moduleFilePutCodePane!=null){
				ArrayList<String> fileList = ModuleEditPaneHolder.moduleFilePutCodePane.getModuleFormatFileNameList();
				if (fileList.size()>0){
					String fileText = StringUtils.join(fileList, "、");
					setToolTipText("在生成代码过程中，\n" +
							"添加”初始化代码“、”模块设置编辑“、”功能编辑“里面的功能，\n对应的代码时，"+
							"如果代码不是写到 "+fileText+" 这些文件里面，\n"+
							"系统会将在这里录入的代码添加到代码文件对应位置");
				}else {
					setToolTipText("在生成代码过程中，\n" +
							"添加”初始化代码“、”模块设置编辑“、”功能编辑“里面的功能时，系统会将在这里录入的代码添加到代码对应位置");
				}
			}else {
				setToolTipText("在生成代码过程中，\n" +
						"添加”初始化代码“、”模块设置编辑“、”功能编辑“里面的功能时，系统会将在这里录入的代码添加到代码对应位置");
			}
		}
	};

}
