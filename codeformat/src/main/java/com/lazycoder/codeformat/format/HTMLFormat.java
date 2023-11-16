package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

@Slf4j
public class HTMLFormat implements FormatInterface {

    @Override
    public String formatter(String fromSource) {
        // TODO Auto-generated method stub
        String html = fromSource;
        if (fromSource != null) {
            try {
//				Document doc = Jsoup.parseBodyFragment(html);
//				html = doc.body().html();
                Document doc = Jsoup.parse(html, "", Parser.htmlParser());
                html = doc.toString();
            } catch (Exception e) {
//                e.printStackTrace();
                html = fromSource;
                log.error("HTML代码格式化出错————" + e.getMessage());
            }
        }
        return html;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

}
