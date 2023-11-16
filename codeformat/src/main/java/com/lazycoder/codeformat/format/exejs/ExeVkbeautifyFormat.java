package com.lazycoder.codeformat.format.exejs;

import java.io.File;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;


public class ExeVkbeautifyFormat extends AbstractExeJs {

	protected final static Invocable FORMAT;

	static {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		scriptEngineManager.registerEngineName("vk",new NashornScriptEngineFactory());
		ScriptEngine engine = scriptEngineManager.getEngineByName("js");

		try {
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + "vkbeautify.js"));
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMAT = (Invocable) engine;
	}

}

