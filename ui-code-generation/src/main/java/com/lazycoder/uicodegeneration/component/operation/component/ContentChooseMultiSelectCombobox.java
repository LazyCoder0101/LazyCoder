package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.TheOptionModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.ContentChooseMeta;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 内容选择（多选）
 *
 * @author admin
 */
public class ContentChooseMultiSelectCombobox extends MultiSelectComboBox<String>
		implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 1021351469005076414L;

	private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

	private PathFind pathFind;

	private ContentChooseControl controlElement;

	private OptionDataModel option = null;

	/**
	 * 新建
	 *
	 * @param codeGenerationalOpratingContainerParam
	 * @param controlElement
	 */
	public ContentChooseMultiSelectCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
											ContentChooseControl controlElement) {
		// TODO Auto-generated constructor stub
		super();

		this.controlElement = controlElement;
		option = SysService.OPTION_SERVICE.getOptionById(controlElement.getOptionId());
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

		PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
				controlElement, codeGenerationalOpratingContainerParam.getPaneType());
		PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
		pathFindTemp.getPathList().add(pathFindCell);
		this.pathFind = pathFindTemp;

		generateItems();
		setTheSize();
		addPopupMenuListener(listener);

		ArrayList<Integer> list = controlElement.getSelectList();
		for (int i = 0; i < list.size(); i++) {
			Integer selectedTemp = list.get(i);
			if (selectedTemp < option.getValueNum()) {
				addSelectedIndex(selectedTemp);
			} else {
				String text = "有个功能默认选\"" + this.controlElement.getOptionName() + "\"选项第" + (selectedTemp + 1) + "个选项值，可这个选项值被删掉了！	(✪ω✪)";
				String logtext = getClass() + "（添加功能异常）————\"" + this.controlElement.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项值，有个多选组件却默认选第" + (selectedTemp + 1) + "个";
				CodeGenerationFrameHolder.errorLogging(text, logtext);
			}
		}
		updateValue();
	}

	/**
	 * 还原
	 *
	 * @param codeGenerationalOpratingContainerParam
	 * @param contentChooseMeta
	 */
	public ContentChooseMultiSelectCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
											ContentChooseMeta contentChooseMeta) {
		super();

		this.controlElement = contentChooseMeta.getControlElement();
		option = SysService.OPTION_SERVICE.getOptionById(controlElement.getOptionId());
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

		this.pathFind = contentChooseMeta.getPathFind();

		generateItems();
		setTheSize();
		addPopupMenuListener(listener);

		ArrayList<Integer> list = contentChooseMeta.getSelectList();
		for (int i = 0; i < list.size(); i++) {
			Integer selectedTemp = list.get(i);
			if (selectedTemp < option.getValueNum()) {
				addSelectedIndex(selectedTemp);
			} else {
				String text = "有个功能选\"" + this.controlElement.getOptionName() + "\"选项第" + (selectedTemp + 1) + "个选项值，可这个选项值被删掉了！	(✪ω✪)";
				String logtext = getClass() + "（打开文件异常）————\"" + this.controlElement.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项值，有个多选组件却选第" + (selectedTemp + 1) + "个";
				CodeGenerationFrameHolder.errorLogging(text, logtext);
			}
		}
		updateValue();
	}

	private void generateItems() {
		setToolTipText(controlElement.getOptionName());
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		ArrayList<String> list = TheOptionModel.getOptionNameList(option);
		for (int i = 0; i < list.size(); i++) {
			model.addElement(list.get(i));
		}
		setModel(model);
	}

	private PopupMenuListener listener = new PopupMenuListener() {

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			// TODO Auto-generated method stub
			updateValue();
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public OpratingContainerInterface getThisOpratingContainer() {
		return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
	}

	@Override
	public void updateValue() {
		Set<Integer> selects = getSelectedIndexs();
		ArrayList<Integer> list = new ArrayList<>(selects);

		OptionDataModel optionDataModel = SysService.OPTION_SERVICE.getOptionById(controlElement.getOptionId());
		String[] valueList = new String[optionDataModel.getValueListGroupNum()];
		for (int i = 0; i < optionDataModel.getValueListGroupNum(); i++) {
			valueList[i] = TheOptionModel.getValue(optionDataModel, i, list);
		}
		CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), valueList);

	}

	@Override
	public void delThis() {
		// TODO Auto-generated method stub

	}

	@Override
	public ContentChooseControl getControlElement() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public PathFind getPathFind() {
		// TODO Auto-generated method stub
		return this.pathFind;
	}

	@Override
	public int getCodeSerialNumber() {
		return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
	}

	@Override
	public AbstractOperatingPane getOperatingComponentPlacePane() {
		// TODO Auto-generated method stub
		return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
	}

	public void setTheSize() {
		// TODO Auto-generated constructor stub
		Dimension dd = new Dimension(140, 30);
		this.setPreferredSize(dd);
		this.setMinimumSize(dd);
		this.setMaximumSize(dd);
	}

	@Override
	public void setParam(FormatStructureModelInterface model) {
		// TODO Auto-generated method stub
		ContentChooseMeta theModel = (ContentChooseMeta) model;
		theModel.setControlElement(controlElement);
		theModel.setPathFind(this.pathFind);

		Set<Integer> selects = getSelectedIndexs();
		ArrayList<Integer> list = new ArrayList<>(selects);
		theModel.setSelectList(list);

	}

	@Override
	public ContentChooseMeta getFormatStructureModel() {
		// TODO Auto-generated method stub
		ContentChooseMeta model = new ContentChooseMeta();
		setParam(model);
		return model;
	}

	@Override
	public int getComponentWidth() {
		// TODO Auto-generated method stub
		return 140;
	}

	@Override
	public void collapseThis() {}

}
