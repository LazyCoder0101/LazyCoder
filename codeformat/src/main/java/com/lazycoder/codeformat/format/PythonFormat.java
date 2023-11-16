package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;

/**
 * @author admin
 */

/**
 * python这门语言暂时没找到较好的格式化代码，目前直接返回生成内容较为合适
 */
public class PythonFormat implements FormatInterface {

    @Override
    public String formatter(String fromSource) {
        return fromSource;
    }

    @Override
    public void init() {

    }

}
//public class PythonFormat extends ExeBeautifyJSFormat {

//	@Override
//	public String formatter(String fromSource) {
//		// TODO Auto-generated method stub
//		String rs = "";
//		CoolFormatResponse response = null;
//		try {
//			response = DoFormatter.formatter(CoolFormatLanguageAttribute.PYTHON, fromSource);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (response.isSuccess() == true) {
//			rs = response.getFormatContent();
//		} else {
//			rs = super.formatter(fromSource);
//		}
//		return rs;
//	}

//}


