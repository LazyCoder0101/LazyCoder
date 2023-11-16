package com.lazycoder.service.vo.datasourceedit.format;

import com.lazycoder.database.model.general.format.GenerlFormatOperatingModel;
import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import lombok.Data;


/**
 * 格式代码控制模型
 *
 * @author Administrator
 */
@Data
public class FormatModel extends AbstractEditContainerModel {

	/**
	 * 格式控制面板
	 */
	private FormatControlPaneInterface controlPane;

	/**
	 * 默认格式代码面板
	 */
	private CorrespondingFormatCodePaneInterface defaultFormatCodePane = null;

	/**
	 * 次格式代码文件列表
	 */
	private LinkedHashMap<String, CorrespondingFormatCodePaneInterface> subFormatCodePaneList = new LinkedHashMap<>();

	/**
	 * 新建
	 */
	public FormatModel() {
		// TODO Auto-generated constructor stub
		super();
	}

	public FormatModel(GenerlFormatOperatingModel formatOperatingModel) {
		this();
		reductionContent(formatOperatingModel);
	}

	@Override
	public ArrayList<BaseCodePane> getCodePaneList() {
		// TODO Auto-generated method stub
		ArrayList<BaseCodePane> codePanelist = new ArrayList<>();
		if (defaultFormatCodePane != null) {
			codePanelist.add(defaultFormatCodePane);
		}
		for (String key : subFormatCodePaneList.keySet()) {
			codePanelist.add(subFormatCodePaneList.get(key));
		}
		return codePanelist;
	}

	@Override
	public ArrayList<BaseOperatingPane> getAllControlPaneList() {
		// TODO Auto-generated method stub
		ArrayList<BaseOperatingPane> list = new ArrayList<BaseOperatingPane>();
		list.add(controlPane);
		list.addAll(super.getAllControlPaneList());
		return list;
	}

}
