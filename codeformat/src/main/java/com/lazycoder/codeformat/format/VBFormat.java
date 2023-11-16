package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.exejs.AbstractExeJs;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

/**
 * http://www.jsons.cn/csharpformat/
 *
 * @author admin
 */
public class VBFormat extends AbstractExeJs implements FormatInterface {

	protected Invocable format = null;

	public VBFormat() {
		// TODO Auto-generated constructor stub
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		scriptEngineManager.registerEngineName("vb",new NashornScriptEngineFactory());
		ScriptEngine engine = scriptEngineManager.getEngineByName("js");

		try {
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + "vbscriptformat.js"));
			format = (Invocable) engine;

			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String formatter(String fromSource) {
		// TODO Auto-generated method stub
		Object res = "";
		try {
			res = format.invokeFunction("beautify", new String[]{fromSource});
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
		String initSource = "<html>\r\n" + "<head>\r\n" + "<script type=\"text/vbscript\">\r\n"
				+ "function greeting()\r\n" + "i=hour(time)\r\n" + "If i = 10 then\r\n" + "end function\r\n"
				+ "</script>\r\n" + "</head>\r\n" + "<body onload=\"greeting()\">\r\n" + "</body>\r\n" + "</html>";
		formatter(initSource);
	}

}
