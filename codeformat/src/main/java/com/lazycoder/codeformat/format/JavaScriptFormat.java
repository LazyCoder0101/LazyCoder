package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.coolformat.DoFormatter;
import com.lazycoder.lazycodercommon.vo.CoolFormatLanguageAttribute;

/**
 * JavaScript代码格式化（下面调用jsbeautify.js方法也可实现格式化）
 *
 * @author admin
 */
public class JavaScriptFormat implements FormatInterface {

    public JavaScriptFormat() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String formatter(String fromSource) {
        // TODO Auto-generated method stub
        String toSource;
        try {
            toSource = DoFormatter.formatter(CoolFormatLanguageAttribute.JAVASCRIPT, fromSource).getFormatContent();
        } catch (Exception e) {
            toSource = fromSource;
        }
        return toSource;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

}
///**
// * https://blog.csdn.net/wx3957156/article/details/38396351
// * 
// * @author admin
// *
// */
//public class JavaScriptFormat extends ExeJs implements FormatInterface {
//
//	protected Invocable format = null;
//
//	public JavaScriptFormat() {
//		// TODO Auto-generated constructor stub
//		ScriptEngineManager mgr = new ScriptEngineManager();
//		ScriptEngine engine = mgr.getEngineByName("javascript");
//		try {
//			engine.eval(readJSFile("js\\jsbeautify.js"));
//			format = (Invocable) engine;
//			init();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public String formatter(String fromSource) {
//		// TODO Auto-generated method stub
//		Object res = "";
//		try {
//			res = format.invokeFunction("js_beautify", new String[] { fromSource, 1 + "", "\t" });// 使用tab字符进行格式化
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ScriptException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return res.toString();
//	}
//
//	@Override
//	public void init() {
//		// TODO Auto-generated method stub
//		String initSource = "if('this_is'==/an_example/){do_something();}else{var a=b?(c%d):e[f];}";
//		formatter(initSource);
//	}
//
//}
