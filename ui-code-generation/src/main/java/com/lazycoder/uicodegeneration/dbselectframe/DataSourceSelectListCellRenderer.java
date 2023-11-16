package com.lazycoder.uicodegeneration.dbselectframe;

import com.lazycoder.lazycodercommon.vo.DataSourceInfo;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;


public class DataSourceSelectListCellRenderer extends JPanel implements ListCellRenderer<DataSourceInfo> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4204553734907542685L;
	private final Font font = new Font("微软雅黑", Font.PLAIN, 16);
	private final Color friendContainerBackColor = new Color(252, 240, 193);
	private final ImageIcon noSelectedIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
			+ "DataSourceSelect" + File.separator + "未选中.png"),
			selectedIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
					+ "DataSourceSelect" + File.separator + "选中.png");
	private JLabel dataSourceLabel, putPathLabel;

//	private LineBorder border = new LineBorder(Color.DARK_GRAY);

	private DataSourceSelectPane dataSourceSelectPane = null;

	private DataSourceSelectFrame dataSourceSelectFrame = null;

	private DataSourceSelectListCellRenderer() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		dataSourceLabel = new JLabel();
		dataSourceLabel.setIcon(noSelectedIcon);
		dataSourceLabel.setFont(font);
		dataSourceLabel.setBounds(10, 0, 180, 40);
		this.add(dataSourceLabel);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(190, 0, 190, 40);
		this.add(separator);

		putPathLabel = new JLabel();
		putPathLabel.setFont(font);
		putPathLabel.setBounds(200, 0, 300, 40);
		this.add(putPathLabel);

//		setBorder(border);
		setPreferredSize(new Dimension(500, 40));
	}

	public DataSourceSelectListCellRenderer(DataSourceSelectPane dataSourceSelectPane,
											DataSourceSelectFrame dataSourceSelectFrame) {
		this();
		this.dataSourceSelectFrame = dataSourceSelectFrame;
		this.dataSourceSelectPane = dataSourceSelectPane;
	}

	private void setTooTip(DataSourceInfo dataSourceInfo) {
		String text = "";

		HtmlPar dbSourceNamePar = new HtmlPar();
		dbSourceNamePar.addText("数据源名称：",false);
		dbSourceNamePar.addText(dataSourceInfo.getDataSourceName(),true);

		HtmlPar pathPar = new HtmlPar();
		pathPar.addText("系统路径：",false);
		pathPar.addText(dataSourceInfo.getPutPath(),true);

		HtmlPar authorPar = new HtmlPar();
		authorPar.addText("作者信息：",false);
		authorPar.addText(dataSourceInfo.getAuthorInfo(),true);

		HtmlPar annotationPar = new HtmlPar();
		annotationPar.addText("备注：",false);
		annotationPar.addText(dataSourceInfo.getDataSourceAnnotation(),true);

		HtmlPar plPar = new HtmlPar();
		plPar.addText("编程语言：",false);
		plPar.addText( dataSourceInfo.getUseProgramingLanguage().getLanguageName(),true);

		if (dataSourceInfo.isEnabledState() == true) {
			HTMLText htmlText = new HTMLText();
			htmlText.addPar(dbSourceNamePar);
			htmlText.addPar(pathPar);
			htmlText.addPar(authorPar);
			htmlText.addPar(annotationPar);
			htmlText.addPar(plPar);
			text = htmlText.getHtmlContent();

		} else {
			HtmlPar noFinishPar = new HtmlPar();
			noFinishPar.addColorText("该数据源尚未编辑完成",HtmlPar.RED,4,true);

			HTMLText htmlText = new HTMLText();
			htmlText.addPar(noFinishPar);
			htmlText.addPar(dbSourceNamePar);
			htmlText.addPar(pathPar);
			htmlText.addPar(authorPar);
			htmlText.addPar(annotationPar);
			htmlText.addPar(plPar);
			text = htmlText.getHtmlContent();

		}
		setToolTipText(text);
	}

	@Override
	public JPanel getListCellRendererComponent(JList<? extends DataSourceInfo> list, DataSourceInfo value, int index,
											   boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		DataSourceInfo temp = value;
		dataSourceLabel.setText(temp.getDataSourceName());
		putPathLabel.setText(temp.getPutPath());
		setTooTip(temp);
		if (temp.isEnabledState() == true) {
			if (isSelected) {
				dataSourceLabel.setIcon(selectedIcon);
				setBackground(friendContainerBackColor);
				repaint();

				dataSourceSelectPane.setSelectedDataSourceInfo(temp);
				dataSourceSelectFrame.setSelectedDataSourceName(temp.getDataSourceName());
			} else {
				dataSourceLabel.setIcon(noSelectedIcon);

				setBackground(dataSourceSelectPane.getBackground());
				repaint();
			}
		} else if (temp.isEnabledState() == false) {
			setEnabled(false);
			dataSourceLabel.setIcon(noSelectedIcon);
			setBackground(Color.LIGHT_GRAY);
		}
		return this;
	}

}