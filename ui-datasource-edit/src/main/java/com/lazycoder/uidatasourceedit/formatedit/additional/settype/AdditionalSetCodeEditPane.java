package com.lazycoder.uidatasourceedit.formatedit.additional.settype;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.EditContainerPane;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalCodeFormatPutPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;


public class AdditionalSetCodeEditPane extends JPanel implements CheckInterface, EditContainerPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -7412530082047240398L;

	private Border defaultborder;

	/**
	 * Create the panel.
	 */
	private JTabbedPane tabbedPane;

	private MyButton addButton, delButton, restoreButton;

	private AdditionalCodeFormatPutPane additionalCodeFormatPutPane;

	private OperatingTipButton operatingTip;

	private int additionalSerialNumber = 0;

	private int currentNum = 0;

	private AdditionalSetCodeEditPane() {
		setLayout(new BorderLayout(0, 0));

		MyToolBar toolBar = new MyToolBar();
		add(toolBar, BorderLayout.NORTH);

		addButton = new MyButton("添加");
		toolBar.add(addButton);
		addButton.addActionListener(listener);

		toolBar.add(Box.createHorizontalStrut(30));

		delButton = new MyButton("删除");
		toolBar.add(delButton);
		delButton.addActionListener(listener);

		toolBar.add(Box.createHorizontalStrut(30));

		restoreButton = new MyButton("还原");
		toolBar.add(restoreButton);
		restoreButton.addActionListener(listener);

		toolBar.add(Box.createHorizontalStrut(30));

		operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
						"懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "可选模板" + File.separator + "可选模板设置")
				.getAbsolutePath());
		toolBar.add(operatingTip);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		defaultborder = tabbedPane.getBorder();

	}

	public AdditionalSetCodeEditPane(AdditionalCodeFormatPutPane additionalCodeFormatPutPane, int additionalSerialNumber) {
		this();
		this.additionalCodeFormatPutPane = additionalCodeFormatPutPane;
		this.additionalSerialNumber = additionalSerialNumber;
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getSource() == addButton) {
				addCategory();
			} else if (arg0.getSource() == delButton) {
				delCategory();
			} else if (arg0.getSource() == restoreButton) {
				restore();
			}
		}
	};

	public void displayAdditionalSetContents(AdditionalInfo additionalInfo) {
		// TODO Auto-generated method stub
		currentNum = 0;
		if (additionalInfo.getNumOfSetCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
			AdditionalSetCategoryEditPane categoryEditPane;
			ArrayList<String> list = SysService.FORMAT_INFO_SERVICE.getSetTypeListParam(additionalInfo);
			if (list != null) {
				for (int a = 0; a < list.size(); a++) {
					currentNum++;
					categoryEditPane = new AdditionalSetCategoryEditPane(additionalSerialNumber, additionalCodeFormatPutPane,
							currentNum);
					categoryEditPane.displayAdditionalSetContents(list.get(a));
					tabbedPane.addTab("设置" + currentNum, categoryEditPane);
				}
			}
		} else if (additionalInfo.getNumOfSetCodeType() == 0) {// 此前没添加过内容

		}
	}

	public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
		ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
		AdditionalSetCategoryEditPane paneTemp;
		for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
			paneTemp = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(i);

			list.addAll(paneTemp.getAllEditContainerModel());
		}
		return list;
	}

	/**
	 * 获取其他操作列表
	 *
	 * @return
	 */
	public List<FormatTypeOperatingModel> getAdditionalSetOperatingModelList() {
		List<FormatTypeOperatingModel> operatingList = new ArrayList<>(), operatingTempList;
		AdditionalSetCategoryEditPane paneTemp;
		for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
			paneTemp = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(i);

			operatingTempList = paneTemp.getOperationParamList();
			operatingList.addAll(operatingTempList);
		}
		return operatingList;
	}

	/**
	 * 获取其他代码模型列表
	 *
	 * @return
	 */
	public List<FormatTypeCodeModel> getAdditionalSetCodeModelList() {
		List<FormatTypeCodeModel> codeList = new ArrayList<>(), codeTempList;
		AdditionalSetCategoryEditPane paneTemp;
		for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
			paneTemp = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(i);

			codeTempList = paneTemp.getCodeParamList();
			codeList.addAll(codeTempList);
		}
		return codeList;
	}

	private void addCategory() {
		currentNum++;
		AdditionalSetCategoryEditPane categoryEditPane = new AdditionalSetCategoryEditPane(additionalSerialNumber,
				additionalCodeFormatPutPane, currentNum);
		tabbedPane.addTab("设置" + currentNum, categoryEditPane);
	}

	private void delCategory() {
		if (tabbedPane.getComponentCount() > 0) {
			currentNum--;
			tabbedPane.remove(tabbedPane.getComponent(tabbedPane.getComponentCount() - 1));
		}
	}

	private void restore() {
		AdditionalInfo additionalInfo = SysService.FORMAT_INFO_SERVICE.getAdditionalInfo(additionalSerialNumber);
		if (additionalInfo == null) {
			LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
		} else if (additionalInfo != null) {
			if (additionalInfo.getNumOfSetCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
				currentNum = 0;
				tabbedPane.removeAll();
				displayAdditionalSetContents(additionalInfo);
			} else {
				LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
			}
		}
	}

	/**
	 * 获取模块类型列表
	 *
	 * @return
	 */
	private ArrayList<String> getAdditionalSetTypeList() {
		ArrayList<String> list = new ArrayList<>();
		AdditionalSetCategoryEditPane additionalSetCategoryEditPane;
		for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
			additionalSetCategoryEditPane = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(i);
			list.add(additionalSetCategoryEditPane.getTheAdditionalSetTypeName());
		}
		return list;
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (checkAdditionalSetCategoryEditPane() == true) {
			AdditionalSetCategoryEditPane additionalSetCategoryEditPane;
			if (tabbedPane.getComponentCount() > 0) {
				for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
					additionalSetCategoryEditPane = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(i);
					if (additionalSetCategoryEditPane.check() == false) {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	private boolean checkAdditionalSetCategoryEditPane() {
		boolean flag = true;
		String typeName1, typeName2;
		AdditionalSetCategoryEditPane paneTemp1, paneTemp2;
		if (tabbedPane.getComponentCount() > 0) {
			for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
				paneTemp1 = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(i);
				typeName1 = paneTemp1.getTheAdditionalSetTypeName();
				for (int a = i + 1; a < tabbedPane.getComponentCount(); a++) {
					paneTemp2 = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(a);
					typeName2 = paneTemp2.getTheAdditionalSetTypeName();
					if (typeName1.equals(typeName2)) {
						flag = false;
						LazyCoderOptionPane.showMessageDialog(null, "其他" + additionalSerialNumber + "设置那里第" + (i + 1) + "个面板和第"
								+ (a + 1) + "个面板分类名一样，\n请检查清楚录入数据无误后再保存", "系统信息", JOptionPane.PLAIN_MESSAGE);
						break;
					}
				}
				if (flag == false) {
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 获取设置总类数量
	 *
	 * @return
	 */
	private int getAdditionalSetNum() {
		return tabbedPane.getComponentCount();
	}

	/**
	 * 获取该分类的方法总数
	 *
	 * @param typeSerialNumber
	 */
	public int getAdditionalSetOpreatingNum(int typeSerialNumber) {
		int additionalSetOpreatingNum = MarkElementName.MARK_NULL;
		if (tabbedPane.getComponentCount() > 0) {
			AdditionalSetCategoryEditPane paneTemp;
			for (int a = 0; a < tabbedPane.getComponentCount(); a++) {
				paneTemp = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(a);
				if (paneTemp.getTypeSerialNumber() == typeSerialNumber) {
					additionalSetOpreatingNum = paneTemp.getAdditionalSetOpreatingNum();
					break;
				}
			}
		}
		return additionalSetOpreatingNum;

	}

	/**
	 * 获取该分类的某个方法的代码数
	 *
	 * @return
	 */
	public int getAdditionalSetCodeNum(int typeSerialNumber, int opreatingSerial) {
		int additionalSetCodeNum = MarkElementName.MARK_NULL;
		if (tabbedPane.getComponentCount() > 0) {
			AdditionalSetCategoryEditPane paneTemp;
			for (int r = 0; r < tabbedPane.getComponentCount(); r++) {
				paneTemp = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(r);
				if (paneTemp.getTypeSerialNumber() == typeSerialNumber) {
					additionalSetCodeNum = paneTemp.getAdditionalSetCodeNum(opreatingSerial);
					break;
				}
			}
		}
		return additionalSetCodeNum;
	}

	public void setAdditonalInfo(AdditionalInfo additionalInfo) {
		additionalInfo.setNumOfSetCodeType(getAdditionalSetNum());// 记录设置代码的分类数量
		additionalInfo.setSetTheTypeOfSetCodeParam(JsonUtil.getJsonStr(getAdditionalSetTypeList()));
	}

	/**
	 * 获取对应分类名
	 *
	 * @param typeSerialNumber
	 * @return
	 */
	public String getTheAdditionalSetTypeName(int typeSerialNumber) {
		String typeName = "";
		if (tabbedPane.getComponentCount() > 0) {
			AdditionalSetCategoryEditPane paneTemp;
			for (int r = 0; r < tabbedPane.getComponentCount(); r++) {
				paneTemp = (AdditionalSetCategoryEditPane) tabbedPane.getComponent(r);
				if (paneTemp.getTypeSerialNumber() == typeSerialNumber) {
					typeName = paneTemp.getTheAdditionalSetTypeName();
					break;
				}
			}
		}
		return typeName;
	}

	@Override
	public void setUIResponse(boolean status) {
		if (status) {
			tabbedPane.setBorder(DataSourceEditHolder.RESPONSE_TRUE_BORDER);
			setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
		} else {
			tabbedPane.setBorder(defaultborder);
			setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
		}
	}

}
