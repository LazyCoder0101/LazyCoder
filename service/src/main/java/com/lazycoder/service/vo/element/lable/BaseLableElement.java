package com.lazycoder.service.vo.element.lable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import lombok.Data;

/**
 * 基本标签元素
 *
 * @author Administrator
 */
@Data
public abstract class BaseLableElement extends BaseElement {

	@JSONField(ordinal = 2)
	private String labelType;

	@JSONField(ordinal = 3)
	private String thisName;

	public BaseLableElement() {
		// TODO Auto-generated constructor stub
		type = ElementName.LABEL_ELEMENT;
	}

	/**
	 * 查看根据字符串进行JSONObject解析后JSONObject的元素类型
	 *
	 * @param elementJSONObject
	 * @return
	 */
	public static String getLabelType(JSONObject elementJSONObject) {
		return elementJSONObject.getString("labelType");
	}


	/**
	 * 匹配
	 *
	 * @param opratingLabel 操作层标签
	 */
	public boolean match(BaseLableElement opratingLabel) {
		boolean flag = false;
		if (this.thisName.equals(opratingLabel.thisName)) {
			if (labelType.equals(opratingLabel.labelType)) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 匹配（该方法只供给代码元素使用）
	 *
	 * @param pathFindCell
	 * @return
	 */
	public boolean match(PathFindCell pathFindCell) {
		boolean flag = false;
		if (this.match(pathFindCell.getOpratingLabel()) == true) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 控制组件才需要重写，该组件的信息
	 *
	 * @return
	 */
	public BaseLableElement controlComponentInformation() {
		return null;
	}

}
