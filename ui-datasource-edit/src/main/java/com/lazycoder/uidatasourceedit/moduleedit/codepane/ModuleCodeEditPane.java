package com.lazycoder.uidatasourceedit.moduleedit.codepane;

import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.UseModuleFunctionEditPane;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 * @author admin
 */
public class ModuleCodeEditPane extends JSplitPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -3081464131542886094L;
	/**
	 * 高比例
	 *
	 * @param dividerLocation
	 */
	private static final double HEIGHT_PROPORTION = 0.826823;
	/**
	 * 宽比例
	 */
	private final double fileWidthProportion = 0.1164, functionWidthProportion = 0.75;
	/**
	 * 分割比例
	 */
	private double dividerLocation = 0.6;
	private UseModuleFileEditPane useModuleFileEditPane;
	private UseModuleFunctionEditPane useModuleFunctionEditPane;

	public ModuleCodeEditPane(double dividerLocation) {
		this.dividerLocation = dividerLocation;
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 设置分割线方向
		setOneTouchExpandable(false);
		addComponentListener(cAdapter);
		setBorder(BorderFactory.createEmptyBorder());
		setEnabled(true);

		Dimension ScreenDimension = SysUtil.SCREEN_SIZE;

		useModuleFileEditPane = new UseModuleFileEditPane();
		ModuleEditPaneHolder.useModuleFileEditPane = useModuleFileEditPane;
		setLeftComponent(useModuleFileEditPane);
		Dimension fileDimension = new Dimension((int) (fileWidthProportion * ScreenDimension.getWidth()),
				(int) (HEIGHT_PROPORTION * ScreenDimension.getHeight()));
		useModuleFileEditPane.setPreferredSize(fileDimension);
//		useModuleFileEditPane.setMinimumSize(fileDimension);
//		useModuleFileEditPane.setMaximumSize(fileDimension);

		useModuleFunctionEditPane = new UseModuleFunctionEditPane();
		ModuleEditPaneHolder.useModuleFunctionEditPane = useModuleFunctionEditPane;
		setRightComponent(useModuleFunctionEditPane);
		Dimension functionDimension = new Dimension((int) (functionWidthProportion * ScreenDimension.getWidth()),
				(int) (HEIGHT_PROPORTION * ScreenDimension.getHeight()));
		useModuleFunctionEditPane.setPreferredSize(functionDimension);
//		useModuleFunctionEditPane.setMinimumSize(functionDimension);
//		useModuleFunctionEditPane.setMaximumSize(functionDimension);

	}

	public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
		return useModuleFunctionEditPane.getAllEditContainerModel();
	}

	private ComponentAdapter cAdapter = new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			setDividerLocation(dividerLocation);
		}
	};

}
