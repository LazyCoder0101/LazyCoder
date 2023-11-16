package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.coolformat.CoolFormatResponse;
import com.lazycoder.codeformat.format.coolformat.DoFormatter;
import com.lazycoder.codeformat.format.exejs.ExeTabifierFormat;
import com.lazycoder.lazycodercommon.vo.CoolFormatLanguageAttribute;

/**
 * C语言代码格式化
 *
 * @author admin
 */
public class CPPFormat extends ExeTabifierFormat implements FormatInterface {

    @Override
    public String formatter(String fromSource) {
        // TODO Auto-generated method stub
        String rs = "";
        CoolFormatResponse response = null;
        try {
            response = DoFormatter.formatter(CoolFormatLanguageAttribute.CPP, fromSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.isSuccess() == true) {
            rs = response.getFormatContent();
        } else {
            rs = cpformatter(fromSource);
        }
        return rs;
//        return cpformatter(fromSource);
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

}
