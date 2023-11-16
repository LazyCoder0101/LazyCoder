package com.lazycoder.uicodegeneration.component.operation.container.pane;


import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.text.StyleConstants;

public class HiddenCommandOperatingPane extends AbstractCommandOperatingPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -6805532191053859083L;

	/**
	 * 						字体大小			像素
	 */
	public final static int FONT_SIZE = 14,PX=20;

	public HiddenCommandOperatingPane(OpratingContainerInterface opratingContainer) {
		super(opratingContainer);
		StyleConstants.setFontSize(attrset, FONT_SIZE);
		StyleConstants.setForeground(attrset, new Color(121,121,121));
		setParagraphAttributes(attrset, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateOperationalContent(GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
		// TODO Auto-generated method stub
		codeGenerationalOpratingContainerParam.setPaneType(PathFindCell.HIDDEN_TYPE);
		super.generateOperationalContent(codeGenerationalOpratingContainerParam);
	}

	@Override
	public void restoreContent(ArrayList<AbstractOpratingPaneElement> paneElementList,
							   GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
		codeGenerationalOpratingContainerParam.setPaneType(PathFindCell.HIDDEN_TYPE);
		super.restoreContent(paneElementList, codeGenerationalOpratingContainerParam);
	}

}
