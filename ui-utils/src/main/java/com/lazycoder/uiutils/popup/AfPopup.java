package com.lazycoder.uiutils.popup;

/**
 * 摘自 https://edu.csdn.net/course/detail/20810
 */
public class AfPopup {
	// 窗口关闭策略
	public static class ClosingPolicy {

		public boolean autoClose = false; // 自动关闭

		public ClosingPolicy() {
		}

		public ClosingPolicy(boolean autoClose) {
			this.autoClose = autoClose;
		}

		public void setAutoClose(boolean autoClose) {
			this.autoClose = autoClose;
		}

	}
}
