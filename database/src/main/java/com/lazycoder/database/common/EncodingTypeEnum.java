package com.lazycoder.database.common;

/**
 * 编码格式
 */
public enum EncodingTypeEnum {

	GBK(1, "GBK", "GBK"),

	UTF8(2, "UTF8", "UTF-8"),

	GB2312(3, "GB2312", "GB2312"),

	GB18030(4, "GB18030", "GB18030"),

	BIG5(5, "BIG5", "Big5");

	/**
	 * 对应系统字典值
	 */
	private final int dictionaryValue;

	private final String encodingDetectParam;

	private final String commonsIoParam;

	/**
	 * @param dictionaryValue     字典值
	 * @param encodingDetectParam encodingDetect这个jar包对应的传参
	 * @param commonsioParam      commons_io这个jar包对应的传参
	 */
	private EncodingTypeEnum(int dictionaryValue, String encodingDetectParam, String commonsioParam) {
		this.dictionaryValue = dictionaryValue;
		this.encodingDetectParam = encodingDetectParam;
		this.commonsIoParam = commonsioParam;
	}

	/**
	 * 根据字典值返回对应枚举类型
	 *
	 * @param dictionaryValue
	 * @return
	 */
	public static EncodingTypeEnum getEncodingTypeBy(int dictionaryValue) {
		EncodingTypeEnum encodingType = null;
		for (EncodingTypeEnum temp : EncodingTypeEnum.values()) {
			if (temp.getDictionaryValue() == dictionaryValue) {
				encodingType = temp;
			}
		}
		return encodingType;
	}

	/**
	 * 根据encodingDetect返回的参数得到对应编码格式枚举
	 *
	 * @param encodingDetectParam
	 * @return
	 */
	public static EncodingTypeEnum getEncodingTypeBy(String encodingDetectParam) {
		EncodingTypeEnum encodingType = null;
		for (EncodingTypeEnum temp : EncodingTypeEnum.values()) {
			if (temp.getEncodingDetectParam() == encodingDetectParam) {
				encodingType = temp;
			}
		}
		return encodingType;
	}

	public int getDictionaryValue() {
		return dictionaryValue;
	}

	public String getEncodingDetectParam() {
		return encodingDetectParam;
	}

	public String getCommonsIoParam() {
		return commonsIoParam;
	}

}
