package com.lazycoder.uidatasourceedit.component.filesetting;

import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import com.lazycoder.uiutils.folder.Folder;

import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * 文件设置组件
 *
 * @author admin
 */
public abstract class AbstractFileSettingContainer extends Folder {

	/**
	 *
	 */
	private static final long serialVersionUID = 6442502724482737639L;

	private final int theWidth = 630, hiddenButtonHight = 30, drawerHight = 230;

	protected int ordinal = 0;

	protected JScrollPane scrollPane;

	protected AbstractFileSettingPane panel;

	protected FoldButton hiddenButton;

	protected AbstractFileSettingContainer() {
		// TODO Auto-generated constructor stub
		super();
		setLayout(new FileSettingLayout());
		init();
	}

	public AbstractFileSettingContainer(int ordinal) {
		this();
		this.ordinal = ordinal;
		hiddenButton.setText("文件" + ordinal);
		panel = getFileSettingPane();
		scrollPane.setViewportView(panel);
	}

	/**
	 * 获取对应的文件设置模板
	 *
	 * @return
	 */
	public abstract AbstractFileSettingPane getFileSettingPane();

	private void init() {
		hiddenButton = new FoldButton(true);
		hiddenButton.setUI(new FoldButtonUI());
		hiddenButton.setSize(theWidth, hiddenButtonHight);
		setHiddenButton(hiddenButton);
		add(hiddenButton);

		scrollPane = new JScrollPane();
		scrollPane.setSize(theWidth, drawerHight);

		Drawer drawer = new Drawer(true ? 1 : 0, scrollPane);
		this.setDrawer(drawer);
		add(drawer);
	}

	public int getOrdinal() {
		return ordinal;
	}

	public boolean isExpanded() {
		return hiddenButton.isExpanded();
	}

	public void setExpanded(boolean expanded) {
		hiddenButton.setExpanded(expanded);
	}

	@Override
	public Dimension getRequiredDimension() {
		int w = getDrawer().getContentWidth(),
				h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + hiddenButtonHight;

		return new Dimension(w, h);
	}

	/**
	 * 负责布局面板组件
	 */
	class FileSettingLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return parent.getPreferredSize();
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return parent.getMinimumSize();
		}

		@Override
		public void layoutContainer(Container parent) {
			int h = parent.getHeight();
			getHiddenButton().setBounds(0, 0, theWidth, hiddenButtonHight);
			// 抽屉只显示抽出的比例
			getDrawer().setBounds(0, hiddenButtonHight, theWidth, h - hiddenButtonHight);
		}
	}

}
