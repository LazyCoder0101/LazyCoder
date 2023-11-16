package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.NoteButtonForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.NoteMeta;

/**
 * 注释
 *
 * @author admin
 */
public class NoteButton extends NoteButtonForCodeGeneration
		implements CodeGenerationFormatUIComonentInterface, CodeGenerationComponentInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 4836606447959229260L;

	private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

	private NoteControl controlElement = new NoteControl();

	private PathFind pathFind;

	public NoteButton(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
					  NoteControl controlElement) {
		// TODO Auto-generated constructor stub
		this.controlElement = controlElement;
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

		PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
				controlElement, codeGenerationalOpratingContainerParam.getPaneType());
		PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
		pathFindTemp.getPathList().add(pathFindCell);
		this.pathFind = pathFindTemp;

		textArea.setText(controlElement.getNote());
	}

	public NoteButton(GeneralContainerComponentParam codeGenerationalOpratingContainerParam, NoteMeta noteMeta) {
		// TODO Auto-generated constructor stub
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

		this.controlElement = noteMeta.getControlElement();
		this.pathFind = noteMeta.getPathFind();
		textArea.setText(noteMeta.getNote());
	}

	@Override
	public void setParam(FormatStructureModelInterface model) {
		// TODO Auto-generated method stub
		NoteMeta theModel = (NoteMeta) model;
		theModel.setControlElement(controlElement);
		theModel.setPathFind(pathFind);
		theModel.setNote(textArea.getText());
	}

	@Override
	public OpratingContainerInterface getThisOpratingContainer() {
		return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
	}

	@Override
	public NoteMeta getFormatStructureModel() {
		// TODO Auto-generated method stub
		NoteMeta model = new NoteMeta();
		setParam(model);
		return model;
	}

	@Override
	public void updateValue() {
		// TODO Auto-generated method stub
	}

	@Override
	public void delThis() {
		// TODO Auto-generated method stub
		hidePopupPanel();
	}

	@Override
	public NoteControl getControlElement() {
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
		// TODO Auto-generated method stub
		return this.codeGenerationalOpratingContainerParam.getCodeSerialNumber();
	}

	@Override
	public AbstractOperatingPane getOperatingComponentPlacePane() {
		// TODO Auto-generated method stub
		return this.codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
	}

	@Override
	public int getComponentWidth() {
		// TODO Auto-generated method stub
		return buttonWidth;
	}

	@Override
	public void collapseThis() {
		packUpPanel();
	}

}
