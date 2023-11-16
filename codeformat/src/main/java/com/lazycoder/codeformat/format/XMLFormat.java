package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.exejs.ExeVkbeautifyFormat;
import javax.script.ScriptException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

@Slf4j
public class XMLFormat extends ExeVkbeautifyFormat implements FormatInterface {

    public XMLFormat() {
        // TODO Auto-generated constructor stub
        init();
    }

    @Override
    public String formatter(String fromSource) {
        // TODO Auto-generated method stub
        // coolFormat的xml格式化会自动补全代码，改调用js方法
        String rs = "";
        try {
            Document doc = Jsoup.parse(fromSource, "", Parser.xmlParser());
            rs = doc.toString();
        } catch (Exception e) {
            try {
                rs = FORMAT.invokeFunction("xml", new String[]{fromSource, "\t"}).toString();
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                log.error("通过js格式化XML出现NoSuchMethodException异常————" + e1.getMessage());
                rs = fromSource;
//				e1.printStackTrace();
            } catch (ScriptException e2) {
                // TODO Auto-generated catch block
                log.error("通过js格式化XML出现ScriptException异常————" + e2.getMessage());
                rs = fromSource;
//				e2.printStackTrace();
            }
            log.error("通过Jsoup格式化XML出错————" + e.getMessage());
        }
        return rs;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        String initSource = "  <books>\r\n" + "  <book>\r\n" + "  </book>\r\n" + "  </books>";
        formatter(initSource);
    }

}
