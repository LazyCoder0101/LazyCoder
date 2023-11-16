package com.lazycoder.codeformat.format;

import com.lazycoder.lazycodercommon.vo.CoolFormatLanguageAttribute;
import com.lazycoder.codeformat.format.coolformat.CoolFormatResponse;
import com.lazycoder.codeformat.format.coolformat.DoFormatter;
import com.lazycoder.codeformat.format.exejs.ExeBeautifyJSFormat;

/**
 * C#代码格式化 http://www.jsons.cn/csharpformat/
 *
 * @author admin
 */
public class CSFormat extends ExeBeautifyJSFormat {

	@Override
	public String formatter(String fromSource) {
		// TODO Auto-generated method stub
		String rs = "";
		CoolFormatResponse response = null;
		try {
			response = DoFormatter.formatter(CoolFormatLanguageAttribute.CS, fromSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.isSuccess() == true) {
			rs = response.getFormatContent();
		} else {
			rs = super.formatter(fromSource);
		}
		return rs;
	}

}
