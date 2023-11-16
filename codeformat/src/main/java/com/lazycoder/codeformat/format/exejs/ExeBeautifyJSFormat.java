package com.lazycoder.codeformat.format.exejs;

import com.lazycoder.codeformat.FormatInterface;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

public class ExeBeautifyJSFormat extends AbstractExeJs implements FormatInterface {

	protected final static Invocable FORMAT;

	static {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		scriptEngineManager.registerEngineName("beautify",new NashornScriptEngineFactory());
		ScriptEngine engine = scriptEngineManager.getEngineByName("js");

		try {
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + "beautify.js"));
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMAT = (Invocable) engine;

		try {
			String initSource = "public class HelloWorld {\r\n" + "public static void main(String args[]) {\r\n"
					+ "System.out.println(\"Hello, World!\");\r\n" + "}\r\n" + "}";
			FORMAT.invokeFunction("format", new String[]{initSource});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String formatter(String fromSource) {
		// TODO Auto-generated method stub
		Object res = "";
		try {
			res = FORMAT.invokeFunction("format", new String[]{fromSource});
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
	}

}

