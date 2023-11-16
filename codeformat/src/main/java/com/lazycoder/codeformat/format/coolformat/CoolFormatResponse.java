package com.lazycoder.codeformat.format.coolformat;

/**
 * 调用CoolFormat的响应
 *
 * @author admin
 */
public class CoolFormatResponse {

	private boolean success = true;

	private String formatContent = "";

	public CoolFormatResponse() {
		// TODO Auto-generated constructor stub
	}

	public CoolFormatResponse(String formatContent) {
		// TODO Auto-generated constructor stub
		this.formatContent = formatContent;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFormatContent() {
		return formatContent;
	}

	public void setFormatContent(String formatContent) {
		this.formatContent = formatContent;
	}

}
