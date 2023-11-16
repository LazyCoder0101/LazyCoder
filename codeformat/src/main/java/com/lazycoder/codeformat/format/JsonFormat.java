package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.exejs.ExeVkbeautifyFormat;

import javax.script.ScriptException;

public class JsonFormat extends ExeVkbeautifyFormat implements FormatInterface {

	public JsonFormat() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public String formatter(String fromSource) {
		// TODO Auto-generated method stub
		Object res = "";
		try {
			res = FORMAT.invokeFunction("json", new String[]{fromSource, "\t"});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toString();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String initSource = "{\"username\":\"abc\",\"id\":5}";
		formatter(initSource);
	}

}
