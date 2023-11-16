package com.lazycoder.uidatasourceedit.moduleedit.commandinput;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.function.ModuleFunctionEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.InitPane;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleset.ModuleSetCodeEditPane;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


public class UseModuleFunctionEditPane extends JTabbedPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 9022479016863745004L;

	private InitPane moduleInitPane;
	private ModuleSetCodeEditPane moduleSetCodePane;
	private ModuleFunctionEditPane moduleFunctionEditPane;

	public UseModuleFunctionEditPane() {
		moduleInitPane = new InitPane();
		JScrollPane ModuleInitScroPane = new JScrollPane(moduleInitPane);
		addTab("初始化编辑", null, ModuleInitScroPane, null);

		moduleSetCodePane = new ModuleSetCodeEditPane();
		ModuleEditPaneHolder.moduleSetCodeEditPane = moduleSetCodePane;
		JScrollPane moduleSetScroPane = new JScrollPane(moduleSetCodePane);
//		moduleSetScroPane.setBorder(null);// 无边框，更贴合容器
//		moduleSetScroPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		addTab("模块设置编辑", null, moduleSetScroPane, null);

		moduleFunctionEditPane = new ModuleFunctionEditPane();
		ModuleEditPaneHolder.moduleFunctionEditPane = moduleFunctionEditPane;
		JScrollPane moduleFunctionScrollPane = new JScrollPane(moduleFunctionEditPane);
//		moduleFunctionScrollPane.setBorder(null);// 无边框，更贴合容器
//		moduleFunctionScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		addTab("功能编辑", null, moduleFunctionScrollPane, null);

	}


	/**
	 * 获取所有代码编辑面板的模型
	 *
	 * @return
	 */
	public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
		ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
		list.addAll(moduleInitPane.getAllEditContainerModel());
		list.addAll(moduleSetCodePane.getAllEditContainerModel());
		list.addAll(moduleFunctionEditPane.getAllEditContainerModel());
		return list;
	}

	/**
	 * 删除多余的文件（保存时调用）
	 */
	public void delRedundantFiles() {
		ArrayList<AbstractEditContainerModel> modelList = getAllEditContainerModel();
		File needFileFolder = DatabaseFileStructure.getModuleNeedFileFolder(SysFileStructure.getDataFileFolder(),
				GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), DataSourceEditHolder.currentModule.getModuleId());
		ArrayList<File> fsfileList = DataSourceEditHolder.getCorrespondingModelFileSelectFolderList(needFileFolder, modelList);
		DataSourceEditHolder.delRedundantFiles(fsfileList, needFileFolder);

		//删除多余的图片组件模型对应的文件夹
		File pictureFolder = DatabaseFileStructure.getModulePictureFolder(SysFileStructure.getDataFileFolder(),
				GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),DataSourceEditHolder.currentModule.getModuleId());
		ArrayList<File> psfileList = DataSourceEditHolder.getCorrespondingModelFilePictureList(pictureFolder, modelList);
		DataSourceEditHolder.delRedundantFiles(psfileList, pictureFolder);
	}

}
