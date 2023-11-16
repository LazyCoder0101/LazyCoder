package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.exejs.AbstractExeJs;
import java.io.File;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

/**
 * 调用js文件摘自 https://gitee.com/hellowql/JspFormat 略有删改
 *
 * @author admin
 */
public class JSPFormat extends AbstractExeJs implements FormatInterface {

	private final static String JSP_FOLDER = "jsp";

	protected Invocable format = null;

	public JSPFormat() {
		// TODO Auto-generated constructor stub
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		scriptEngineManager.registerEngineName("jsp",new NashornScriptEngineFactory());
		ScriptEngine engine = scriptEngineManager.getEngineByName("js");

		try {
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + JSP_FOLDER + File.separator + "etag.eclipse.js"));
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + JSP_FOLDER + File.separator + "stack.eclipse.js"));
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + JSP_FOLDER + File.separator + "parse.eclipse.js"));
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + JSP_FOLDER + File.separator + "format.eclipse.js"));
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
			res = format.invokeFunction("jspFormat", new String[]{fromSource});
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
		String initSource = "<jsp:useBean id=\"theBean\" scope=\"request\" class=\"package.class\" />\r\n"
				+ "<HTML>\r\n" + "  <HEAD>\r\n" + "    <TITLE>JavaScript</TITLE>\r\n"
				+ "    <SCRIPT language=\"JavaScript\">\r\n" + "      function foo() {\r\n"
				+ "        <% if (theBean.isDoThis()) { %>\r\n" + "          doThis();\r\n"
				+ "        <% } else { %>\r\n" + "          doThat();\r\n" + "        <% } %>\r\n" + "      }\r\n"
				+ "    </SCRIPT>\r\n" + "  </HEAD>\r\n" + "  <BODY></BODY>\r\n" + "</HTML>";
		formatter(initSource);
	}

}

