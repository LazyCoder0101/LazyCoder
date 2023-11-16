package com.lazycoder.service.vo.element.mark;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MarkThanObject {

	/**
	 * 承载标记的对象
	 */
	private HarryingMark harryingMark;

	/**
	 * 匹配特征数量
	 */
	private int numOfMatchingFeatures = 0;

	/**
	 * 匹配值
	 */
	private int matchValue = 0;

	public MarkThanObject() {
		// TODO Auto-generated constructor stub
	}


}
