package com.lazycoder.uicodegeneration.component.operation.container.pane;


import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import java.util.ArrayList;

public class FormatOperatingPane extends AbstractOperatingPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 221402681649552609L;

	public FormatOperatingPane(OpratingContainerInterface opratingContainer) {
		super(opratingContainer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateOperationalContent(GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
		// TODO Auto-generated method stub
		codeGenerationalOpratingContainerParam.setPaneType(PathFindCell.FORMAT_TYPE);
		super.generateOperationalContent(codeGenerationalOpratingContainerParam);
	}

	@Override
	public void restoreContent(ArrayList<AbstractOpratingPaneElement> paneElementList,
							   GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
		codeGenerationalOpratingContainerParam.setPaneType(PathFindCell.FORMAT_TYPE);
		super.restoreContent(paneElementList, codeGenerationalOpratingContainerParam);
	}

}
