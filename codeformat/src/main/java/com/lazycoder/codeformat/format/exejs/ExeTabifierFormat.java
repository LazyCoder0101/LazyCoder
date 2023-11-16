package com.lazycoder.codeformat.format.exejs;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

public class ExeTabifierFormat extends AbstractExeJs {

	protected final static Invocable FORMAT;

	static {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		scriptEngineManager.registerEngineName("tab",new NashornScriptEngineFactory());
		ScriptEngine engine = scriptEngineManager.getEngineByName("js");

		try {
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + "tabifier.js"));
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMAT = (Invocable) engine;

		try {
			String initSource = "# include <iostream>\r\n" + "int main(){\r\n"
					+ "std::cout << \"Hello, world!\" << std::endl;\r\n" + "return 0;\r\n" + "}";
			FORMAT.invokeFunction("cleanCStyle", new String[]{initSource});
		} catch (NoSuchMethodException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 目前发现，可格式化C和PHP代码
	 *
	 * @param fromSource
	 * @return
	 */
	public static String cpformatter(String fromSource) {
		Object res = "";
		try {
			res = FORMAT.invokeFunction("cleanCStyle", new String[]{fromSource});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toString();
	}

}
