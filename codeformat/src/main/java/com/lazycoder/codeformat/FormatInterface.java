package com.lazycoder.codeformat;

public interface FormatInterface {

	/**
	 * 代码格式化
	 *
	 * @param fromSource
	 * @return
	 */
	public String formatter(String fromSource);

	/**
	 * 初始化方法（一般直接调用格式化函数，事先在初始化调用以避免第一次调用执行过慢）
	 */
	public void init();

}
