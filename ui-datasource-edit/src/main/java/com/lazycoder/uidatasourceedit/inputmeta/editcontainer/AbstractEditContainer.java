package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.AbstractCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.AbstractControlCabinet;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.folder.FolderPane;
import lombok.Getter;

public abstract class AbstractEditContainer extends FolderPane implements CheckInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 4485063929773416585L;

	@Getter
	protected int operatingOrdinalNumber = 0;

	/**
	 * 控制层布局面板
	 */
	protected AbstractControlCabinet controlCabinet = null;

	/**
	 * 代码层布局面板
	 */
	protected AbstractCodeCabinet codeCabinet = null;

	/**
	 * 模型
	 */
	protected ContainerModel theModel = new ContainerModel();

	public AbstractEditContainer(int intterTabPadding) {
		super(intterTabPadding);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (controlCabinet.check() == true) {
			if (codeCabinet.check() == false) {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 清空
	 */
	public void clearAndBlankCodePane() {
		theModel.clear();
		if (controlCabinet != null) {
			controlCabinet.clear();
		}
		if (codeCabinet != null) {
			codeCabinet.clear();
			codeCabinet.addCodePane(theModel);
		}
	}

	public void clear() {
		theModel.clear();
		if (controlCabinet != null) {
			controlCabinet.clear();
		}
		if (codeCabinet != null) {
			codeCabinet.clear();
		}
	}

	/**
	 * 添加代码面板
	 */
	public void addCodePane() {
		codeCabinet.addCodePane(theModel);
		CommandCodeControl.updateCodePaneMenu(theModel);
	}

	/**
	 * 删除代码面板
	 */
	public void delCodePane() {
		codeCabinet.delCodePane(theModel);
	}


	public ContainerModel getContainerModel(){
		return theModel;
	}

	public String getShowText() {
		return controlCabinet.getShowText();
	}

}
