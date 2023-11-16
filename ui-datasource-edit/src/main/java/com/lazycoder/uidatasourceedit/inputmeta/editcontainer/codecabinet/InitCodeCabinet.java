package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.InitCodeTier;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;

public class InitCodeCabinet extends AbstractCodeCabinet implements CodeCabinetInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 7844294955012047029L;

	/**
	 * 操作层序号
	 */
	private int operatingOrdinal = 0;

	/**
	 * 初始化使用
	 *
	 * @param theModel        模型
	 * @param operatingOrdinal 控制层序号
	 * @param expanded         是否展开
	 * @param proportion       宽比例
	 */
	public InitCodeCabinet(ContainerModel theModel, int operatingOrdinal, boolean expanded, double proportion) {
		super(expanded, proportion);
		this.operatingOrdinal = operatingOrdinal;
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<InitCodeModel> getCodeModelList() {
		ArrayList<InitCodeModel> list = new ArrayList<>();
		InitCodeTier temp;
		InitCodeModel tempModel;
		for (int i = 0; i < getVBox().getComponentCount(); i++) {
			temp = (InitCodeTier) getVBox().getComponent(i);
			tempModel = temp.getCodeModel();
			tempModel.setOrdinal(operatingOrdinal);
			tempModel.setCodeOrdinal(i + 1);
			list.add(tempModel);
		}
		return list;
	}

	@Override
	public void addCodePane(ContainerModel theModel) {
		int codeNum = getCodeNum() + 1;
		//setCodeNum(codeNum);

		InitCodeTier initCodeInputPane = new InitCodeTier(this.operatingOrdinal, codeNum, theModel);
		getVBox().add(initCodeInputPane);
		CommandCodeControl.updateCodePaneMenu(theModel);
		SysUtil.scrollToBottom(scrollPane);
	}

	/**
	 * 还原内容
	 *
	 * @param initMetaModel
	 * @param theModel
	 */
	public void reductionContent(InitMetaModel initMetaModel, ContainerModel theModel) {
		// TODO Auto-generated method stub
		if (initMetaModel != null) {
			if (initMetaModel.getOperatingModel() != null) {
				operatingOrdinal = initMetaModel.getOperatingModel().getOrdinal();
			}
			ArrayList<InitCodeModel> codeModelList = initMetaModel.getCodeModelList();
			if (codeModelList != null) {
				InitCodeModel temp;
				InitCodeTier initCodeInputPane;
				if (codeModelList != null) {
					for (int i = 0; i < codeModelList.size(); i++) {
						temp = codeModelList.get(i);
						//setCodeNum(temp.getCodeOrdinal());
						initCodeInputPane = new InitCodeTier(this.operatingOrdinal, temp.getCodeOrdinal(), theModel);
						initCodeInputPane.reductionContent(temp);
						getVBox().add(initCodeInputPane);
					}
				}
				this.updateUI();
				this.repaint();
			}
		}

	}

	/**
	 * 获取该初始化代码的条数
	 *
	 * @return
	 */
	@Override
	public int getCodeNum() {
		return getVBox().getComponentCount();
	}

	/**
	 * 删除代码面板
	 *
	 * @param theModel
	 */
	@Override
	public void delCodePane(ContainerModel theModel) {
		//初始化功能允许一句代码都不写，所以这里重写，大于0即可
		if (getVBox().getComponentCount() > 0) {
			int temp = getVBox().getComponentCount() - 1;
			CommandCodeControl.delLastFunctionCode(theModel);
			getVBox().remove(temp);

		}
		updateUI();
		repaint();
	}

}
