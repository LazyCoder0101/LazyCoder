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
 * 该格式化功能调用js文件摘自 https://github.com/g2384/VHDLFormatter (MIT license)
 *
 * @author admin
 */
public class VHDLFormat extends AbstractExeJs implements FormatInterface {

	protected Invocable format = null;

	public VHDLFormat() {
		// TODO Auto-generated constructor stub
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		scriptEngineManager.registerEngineName("vhdl",new NashornScriptEngineFactory());
		ScriptEngine engine = scriptEngineManager.getEngineByName("js");

		try {
//			engine.eval(FileUtil.readFile("F:\\IDEA\\lannong\\codeformat\\src\\main\\resources\\codeFormatJs\\vhdl\\exports.js"));
//			engine.eval(FileUtil.readFile("F:\\IDEA\\lannong\\codeformat\\src\\main\\resources\\codeFormatJs\\vhdl\\VHDLFormatter.js"));
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + "vhdl" + File.separator + "exports.js"));
			engine.eval(readJSFile(CONFIG_FOLDER + File.separator + "vhdl" + File.separator + "VHDLFormatter.js"));

			format = (Invocable) engine;

//			init();
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
			res = format.invokeFunction("vhdlFormat", new String[]{fromSource});
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
		String initSource = "package run_base_pkg is\r\n"
				+ "  signal runner : runner_sync_t := (phase => test_runner_entry,\r\n"
				+ "                                    locks => ((false, false),\r\n"
				+ "                                              (false, false),\r\n"
				+ "                                              (false, false)),\r\n"
				+ "                                    exit_without_errors => false,\r\n"
				+ "                                    exit_simulation => false);\r\n" + "\r\n"
				+ "  shared variable runner_trace_logger : logger_t;\r\n" + "\r\n"
				+ "  procedure set_num_of_test_cases (\r\n" + "    constant new_value : in integer);\r\n"
				+ "end package;";
		formatter(initSource);
	}

}

