package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.TextFieldForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.TextInputMeta;
import com.lazycoder.utils.RealNumberUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 文本输入（文本框）
 *
 * @author admin
 */
public class TextInputTextField extends TextFieldForCodeGeneration
		implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 3648031400787520324L;

	private int componentWidth = 0;

	private PathFind pathFind;

	private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

	private TextInputControl controlElement = new TextInputControl();


	public TextInputTextField(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
							  TextInputControl controlElement) {
		// TODO Auto-generated constructor stub
		this.controlElement = controlElement;
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

		PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
				controlElement, codeGenerationalOpratingContainerParam.getPaneType());
		PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
		pathFindTemp.getPathList().add(pathFindCell);
		this.pathFind = pathFindTemp;

		setTip();
		getDocument().addDocumentListener(documentListener);
		addFocusListener(focusListener);

		setText(controlElement.getDefaultValue());
		setTheSize();
//		updateValue();
	}

	public TextInputTextField(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
							  TextInputMeta textInputMeta) {
		this.controlElement = textInputMeta.getControlElement();
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
		this.pathFind = textInputMeta.getPathFind();

		setText(textInputMeta.getInputContent());
		setTip();
		getDocument().addDocumentListener(documentListener);
		addFocusListener(focusListener);

		setTheSize();
	}

	private void setTip(){
		StringBuilder out = new StringBuilder();
		if (TextInputControl.NOT_LIMIT !=controlElement.getInputLimit()){
			out.append("这个输入框");
			if (TextInputControl.ONLY_INTEGER ==controlElement.getInputLimit()){
				out.append("只能填整数");
			}else if(TextInputControl.ONLY_FLOAT ==controlElement.getInputLimit()){
				out.append("只能填小数");
			}
			if ("".equals(controlElement.getMinValue().trim())==false){
				out.append("，最小填"+controlElement.getMinValue().trim());
			}
			if ("".equals(controlElement.getMaxValue().trim())==false){
				out.append("，最大填"+controlElement.getMaxValue().trim());
			}
			setToolTipText(out.toString());
		}
	}


	private void setTheSize() {
		Dimension dd = new Dimension(200, 30);
		if (TextInputControl.SHORT_SIZE == controlElement.getTextFieldShowSize()) {
			dd = new Dimension(80, 30);
			componentWidth = 80;
		} else if (TextInputControl.MIDDLE_SIZE == controlElement.getTextFieldShowSize()) {
			dd = new Dimension(160, 30);
			componentWidth = 160;
		} else if (TextInputControl.LONG_SIZE == controlElement.getTextFieldShowSize()) {
			dd = new Dimension(300, 30);
			componentWidth = 300;
		}
		setPreferredSize(dd);
		setMaximumSize(dd);
		setMinimumSize(dd);
	}

	@Override
	public OpratingContainerInterface getThisOpratingContainer() {
		return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
	}

	private FocusListener focusListener = new FocusListener() {

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			check();
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
		}
	};
	private DocumentListener documentListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateValue();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateValue();

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateValue();
		}
	};

	private void check() {
		String text = getText();
		if (TextInputControl.ONLY_INTEGER == controlElement.getInputLimit()) {
			boolean flag = RealNumberUtil.isInteger(text.trim());
			if (flag == true) {
				boolean maxFlag = RealNumberUtil.isInteger(controlElement.getMaxValue().trim()),
						minFlag = RealNumberUtil.isInteger(controlElement.getMinValue().trim());

				int num = RealNumberUtil.convertedToInteger(text.trim());

				if (maxFlag == true && minFlag == true) {
					int max = RealNumberUtil.convertedToInteger(controlElement.getMaxValue().trim()),
							min = RealNumberUtil.convertedToInteger(controlElement.getMinValue().trim());
					if (num > max) {
						LazyCoderOptionPane.showMessageDialog(null, "(◕ᴗ◕✿)  亲，输入数字不能大于" + max + "喔！");
					}
					if (num < min) {
						LazyCoderOptionPane.showMessageDialog(null, "(๑¯∀¯๑)  亲，填写的数字不能比" + min + "小啦！");
					}

				} else if (maxFlag == true && minFlag == false) {
					int max = RealNumberUtil.convertedToInteger(controlElement.getMaxValue().trim());
					if (num > max) {
						LazyCoderOptionPane.showMessageDialog(null, "(´◔‸◔`)   亲，输入数字不能大于" + max + "喔！");
					}

				} else if (maxFlag == false && minFlag == true) {
					int min = RealNumberUtil.convertedToInteger(controlElement.getMinValue().trim());
					if (num < min) {
						LazyCoderOptionPane.showMessageDialog(null, "o(*￣3￣)o  桥豆麻袋，这里写的内容是不能比" + min + "小的！ 用户桑！！！");
					}
				}

			} else {
				LazyCoderOptionPane.showMessageDialog(null, "(づ｡◕ᴗᴗ◕｡)づ 重要的话说三遍，\n 填整数！\n 填整数！！\n 填整数！！！");
			}

		} else if (TextInputControl.ONLY_FLOAT == controlElement.getInputLimit()) {
			boolean flag = RealNumberUtil.isDouble(text.trim());
			if (flag == true) {
				boolean maxFlag = RealNumberUtil.isDouble(controlElement.getMaxValue().trim()),
						minFlag = RealNumberUtil.isDouble(controlElement.getMinValue().trim());

				double num = RealNumberUtil.convertedToDouble(text.trim());

				if (maxFlag == true && minFlag == true) {
					double max = RealNumberUtil.convertedToDouble(controlElement.getMaxValue().trim()),
							min = RealNumberUtil.convertedToDouble(controlElement.getMinValue().trim());
					if (num > max) {
						LazyCoderOptionPane.showMessageDialog(null, "｡◕ᴗ◕｡   亲，输入数字不能大于" + max + "喔！");
					}
					if (num < min) {
						LazyCoderOptionPane.showMessageDialog(null, "_φ(❐_❐✧    桥豆麻袋，这里写的内容是不能比" + min + "小的！ 用户桑");
					}

				} else if (maxFlag == true && minFlag == false) {
					double max = RealNumberUtil.convertedToDouble(controlElement.getMaxValue().trim());
					if (num > max) {
						LazyCoderOptionPane.showMessageDialog(null, "ᕙ༼◕ ᴥ ◕༽ᕗ    矮油，这里填写的内容不能比" + max + "大啦！");
					}

				} else if (maxFlag == false && minFlag == true) {
					double min = RealNumberUtil.convertedToDouble(controlElement.getMinValue().trim());
					if (num < min) {
						LazyCoderOptionPane.showMessageDialog(null, "|(￣3￣)|  亲，输入数字不能小于" + min + "喔！");
					}
				}

			} else {
				LazyCoderOptionPane.showMessageDialog(null, "(๑‾᷅^‾᷅๑)  能不能走点心，\n写小数！\n写小数！\n写小数！");
			}

		}
	}

	@Override
	public void updateValue() {
		// TODO Auto-generated method stub
		CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), getText());
	}

	@Override
	public void delThis() {
		// TODO Auto-generated method stub

	}

	@Override
	public TextInputControl getControlElement() {
		return controlElement;
	}

	@Override
	public PathFind getPathFind() {
		return pathFind;
	}

	public void setPathFind(PathFind pathFind) {
		this.pathFind = pathFind;
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

	@Override
	public void setParam(FormatStructureModelInterface model) {
		// TODO Auto-generated method stub
		TextInputMeta theModel = (TextInputMeta) model;
		theModel.setControlElement(controlElement);
		theModel.setPathFind(pathFind);
		theModel.setInputContent(getText());
	}

	@Override
	public TextInputMeta getFormatStructureModel() {
		// TODO Auto-generated method stub
		TextInputMeta model = new TextInputMeta();
		setParam(model);
		return model;
	}

	@Override
	public int getComponentWidth() {
		// TODO Auto-generated method stub
		return componentWidth;
	}

	@Override
	public void collapseThis() {

	}

}
