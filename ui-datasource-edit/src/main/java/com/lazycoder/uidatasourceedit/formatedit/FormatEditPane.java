package com.lazycoder.uidatasourceedit.formatedit;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalEditPane;
import com.lazycoder.uidatasourceedit.formatedit.main.MainFormatEditPane;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.ArrayList;


public class FormatEditPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4816500494060769900L;

	/**
	 * Create the panel.
	 */
	private MainFormatEditPane mainEditPane;
	private AdditionalEditPane additionalEditPane;

	public FormatEditPane() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);

		mainEditPane = new MainFormatEditPane();
		FormatEditPaneHolder.mainFormatEditPane = mainEditPane;
		tabbedPane.addTab("必填模板", null, mainEditPane, null);

		additionalEditPane = new AdditionalEditPane();
		FormatEditPaneHolder.additionalEditPane = additionalEditPane;
		tabbedPane.addTab("可选模板", null, additionalEditPane, null);
	}

	/**
	 * 根据codeFormatFlagParam的信息获取对应面板
	 *
	 * @param codeFormatFlagParam
	 * @return
	 */
	public AbstractFormatCodeInputPane getFormatCodeInputPane(CodeFormatFlagParam codeFormatFlagParam) {
		AbstractFormatCodeInputPane formatCodeInputPane = null;
		if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParam.getFormatType()) {
			formatCodeInputPane = FormatEditPaneHolder.mainCodePane.getFormatCodeInputPane(codeFormatFlagParam.getId());
		} else if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
			formatCodeInputPane = additionalEditPane.getFormatCodeInputPane(codeFormatFlagParam.getAdditionalSerialNumber(),codeFormatFlagParam.getId());
		}
		return formatCodeInputPane;
	}

	/**
	 * 重新加载格式代码面板
	 */
	public void reloadFormatCodeInputPane() {
		FormatEditPaneHolder.mainCodePane.reloadFormatCodeInputPane();
		additionalEditPane.reloadFormatCodeInputPane();
	}

	public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
		ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
		list.addAll(mainEditPane.getAllEditContainerModel());
		list.addAll(additionalEditPane.getAllEditContainerModel());
		return list;
	}

}
