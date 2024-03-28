package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralCodePane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.AbstractCodeTier;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.HiddenCodeButton;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.utils.SysUtil;
import javax.swing.JScrollPane;
import java.awt.*;

public abstract class AbstractCodeCabinet extends GeneralCodePane implements CodeCabinetInterface, CheckInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 2016761489301630408L;

	/**
	 * 操作层序号
	 */
	protected int operatingOrdinalNumber = 0;

	public AbstractCodeCabinet(boolean expanded, double proportion) {
		super(expanded, proportion);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 删除代码面板
	 *
	 * @param theModel
	 */
	public void delCodePane(ContainerModel theModel) {
		if (getVBox().getComponentCount() > 1) {
			int temp = getVBox().getComponentCount() - 1;
			CommandCodeControl.delLastFunctionCode(theModel);
			getVBox().remove(temp);

		}
		updateUI();
		repaint();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		AbstractCodeTier codeTier;
		Component temp;
		for (int i = 0; i < getVBox().getComponentCount(); i++) {
			temp = getVBox().getComponent(i);
			if (temp instanceof AbstractCodeTier) {
				codeTier = (AbstractCodeTier) temp;
				if (codeTier.check() == false) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	@Override
	protected HiddenCodeButton generateHiddenCodeButton(boolean expanded) {
		return new HiddenCodeButton(expanded){
			@Override
			public void doSomethingWhenMousePressed(boolean expanded) {
				packUpAllImportCodePane();
			}
		};
	}

	/**
	 * 收起所有代码面板对应的记录引入代码内容的面板
	 */
	public void packUpAllImportCodePane(){
		AbstractCodeTier codeTier;
		Component temp;
		for (int i = 0; i < getVBox().getComponentCount(); i++) {
			temp = getVBox().getComponent(i);
			if (temp instanceof AbstractCodeTier) {
				codeTier = (AbstractCodeTier) temp;
				codeTier.packUpCorrespondingImportCodePane();
			}
		}
	}

	@Override
	public void addCodePane(ContainerModel theModel) {
		// TODO Auto-generated method stub
		if (getParent()!=null&&
				getParent().getParent()!=null&&
				getParent().getParent().getParent()!=null&&
				getParent().getParent().getParent().getParent()!=null&&
				getParent().getParent().getParent().getParent() instanceof JScrollPane){

			JScrollPane scrollPane = (JScrollPane) getParent().getParent().getParent().getParent();
			SysUtil.scrollToBottom(scrollPane);
		}
//		getParent();//container
//		getParent().getParent();//metaPane
//		getParent().getParent().getParent();//box
//		getParent().getParent().getParent().getParent();//滑动面板
	}

	@Override
	public int getCodeNum() {
		return getVBox().getComponentCount();
	}
}
