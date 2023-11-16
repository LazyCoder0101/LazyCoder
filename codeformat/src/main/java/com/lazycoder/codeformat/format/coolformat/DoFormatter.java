package com.lazycoder.codeformat.format.coolformat;

import com.lazycoder.lazycodercommon.vo.CoolFormatLanguageAttribute;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * coolformat包部分源码是调用https://github.com/akof1314/CoolFormat
 * 的源码生成的dll文件实现代码格式化，调用dll实现代码格式化的功能源码非作者所写
 *
 * @author admin
 */

public class DoFormatter {

    /**
     * 使用前调用一次，避免第一次调用响应慢
     */
    public static void init() throws Exception {
        String initSource = "# include <iostream>\r\n" + "int main(){\r\n"
                + "std::cout << \"Hello, world!\" << std::endl;\r\n" + "return 0;\r\n" + "}";
        formatter(CoolFormatLanguageAttribute.CPP, initSource);
    }

    /**
     * 使用coolFormat对代码进行格式化
     *
     * @param language
     * @param fromSource
     * @return
     */
    public static CoolFormatResponse formatter(CoolFormatLanguageAttribute language, String fromSource) throws Exception {
        CoolFormatResponse coolFormatResponse = new CoolFormatResponse(fromSource);
        String rs = new String();

        Pointer pszTextIn = new Memory(fromSource.getBytes().length + 10);
        pszTextIn.setString(0, fromSource);
        Pointer pszTextOut = new Memory(fromSource.getBytes().length * 3);// 后面换高版本64位jdk，把这里改成1    fromSource.getBytes().length * 2
        Pointer pszMsgOut = new Memory(1);// 后面换高版本64位jdk
//        Pointer pszTextOut = new Memory(fromSource.getBytes().length + 2000);// 后面换高版本64位jdk，把这里改成1
//        Pointer pszMsgOut = new Memory(5000);// 后面换高版本64位jdk，把这里改成1
        IntByReference nTextOut = new IntByReference();
        IntByReference nMsgOut = new IntByReference();
        long uCodepage = 0;
        Pointer pszEol = null;
        Pointer pszInitIndent = null;

        boolean success = CoolFormatLib.INSTANCE_DLL.DoFormatter(language.getCoolFormatDictionaryValue(), pszTextIn,
                pszTextOut, nTextOut, pszMsgOut, nMsgOut, uCodepage, pszEol, pszInitIndent);
        if (success) {
            coolFormatResponse.setSuccess(true);
            if (language == CoolFormatLanguageAttribute.JAVA) {
                String regex = "(\\s|\\n|\\t|\\r)+\\{";
                rs = pszTextOut.getString(0, "UTF-8").replaceAll(regex, "{");
            } else {
                rs = pszTextOut.getString(0, "UTF-8");
            }
            coolFormatResponse.setFormatContent(rs);
        } else {
            coolFormatResponse.setSuccess(false);
            coolFormatResponse.setFormatContent(fromSource);
        }

        return coolFormatResponse;
    }

    /**
     * 使用coolFormat对代码进行格式化
     *
     * @param coolFormatCode
     * @param fromSource
     * @return
     */
    public static CoolFormatResponse formatter(int coolFormatCode, String fromSource) throws Exception {
        CoolFormatResponse coolFormatResponse = new CoolFormatResponse(fromSource);
        String rs = new String();
        Pointer pszTextIn = new Memory(fromSource.getBytes().length + 1);
        pszTextIn.setString(0, fromSource);
        Pointer pszTextOut = new Memory(fromSource.getBytes().length * 2);// 后面换高版本64位jdk，把这里改成1
        Pointer pszMsgOut = new Memory(1);// 后面换高版本64位jdk，把这里改成1
        IntByReference nTextOut = new IntByReference();
        IntByReference nMsgOut = new IntByReference();
        long uCodepage = 0;
        Pointer pszEol = null;
        Pointer pszInitIndent = null;

        boolean success = CoolFormatLib.INSTANCE_DLL.DoFormatter(coolFormatCode, pszTextIn,
                pszTextOut, nTextOut, pszMsgOut, nMsgOut, uCodepage, pszEol, pszInitIndent);
        if (success) {
            coolFormatResponse.setSuccess(true);
            if (coolFormatCode == CoolFormatLanguageAttribute.JAVA.getCoolFormatDictionaryValue()) {//如果是java
                String regex = "(\\s|\\n|\\t|\\r)+\\{";
                rs = pszTextOut.getString(0, "UTF-8").replaceAll(regex, "{");
            } else {
                rs = pszTextOut.getString(0, "UTF-8");
            }
            coolFormatResponse.setFormatContent(rs);
        } else {
            coolFormatResponse.setSuccess(false);
            coolFormatResponse.setFormatContent(fromSource);
        }
        return coolFormatResponse;
    }


//	public String quickFomatter(String languageType, String content) {
//		boolean javaStyleLike = false;
//		return quickFomatter(languageType, content, javaStyleLike);
//	}

    public String quickFomatter(String languageType, String content, boolean javaStyleLike) {
        String rs = new String();
        long nLanguage = SynLanguage.getNLanguage(languageType);
        Pointer pszTextIn = new Memory(content.getBytes().length + 1);
        pszTextIn.setString(0, content);
        Pointer pszTextOut = new Memory(content.getBytes().length * 2);// 后面换高版本64位jdk，把这里改成1
        Pointer pszMsgOut = new Memory(1);// 后面换高版本64位jdk，把这里改成1
        IntByReference nTextOut = new IntByReference();
        IntByReference nMsgOut = new IntByReference();
        long uCodepage = 0;
        Pointer pszEol = null;
        Pointer pszInitIndent = null;

        boolean success = CoolFormatLib.INSTANCE_DLL.DoFormatter(nLanguage, pszTextIn, pszTextOut, nTextOut, pszMsgOut,
                nMsgOut, uCodepage, pszEol, pszInitIndent);
        if (success) {
            if (javaStyleLike) {
                // String regex = "(\b|\n|\t|\r)+\\{";
                String regex = "(\\s|\\n|\\t|\\r)+\\{";
                rs = pszTextOut.getString(0, "UTF-8").replaceAll(regex, "{");

            } else {
                rs = pszTextOut.getString(0, "UTF-8");
            }

        } else {
            rs = pszMsgOut.getString(0);
        }
        return rs;
    }

    /**
     * 摘自https://blog.csdn.net/Eyuang/article/details/8542434
     *
     * @param str
     * @return
     */
    public static byte[] String2byte(String str) {
        byte[] src = str.getBytes();
        byte[] dest = new byte[src.length + 1];

        System.arraycopy(src, 0, dest, 0, src.length);
        dest[src.length] = 0x00;     // 这句可以没有，因为 new 的时候已经初始化为 0
        return dest;
    }

}
