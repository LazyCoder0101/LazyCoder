package com.lazycoder.lazycodercommon.vo;

import lombok.Getter;

public enum CoolFormatLanguageAttribute {

    ACTIONSCRIPT(0, new String[]{".as", ".mx"}),

    ADA(1, new String[]{".ada", ".ads", ".adb"}),

    ASM(2, new String[]{".asm"}), // 汇编

    ASP(3, new String[]{".asp"}),

    AUTOHOTKEY(4, new String[]{".ahk", ".ia", ".scriptlet", ".hkml"}),

    AUTOIT(5, new String[]{".au3"}),

    BATCH(6, new String[]{".bat", ".cmd", ".nt"}),

    COBOL(7, new String[]{".cbl", ".cbd", ".cdb", ".cdc", ".cob"}),

    CPP(8, new String[]{".c", ".cpp", ".h", ".cc", ".cxx", ".hxx", ".hpp"}), // C++

    CS(9, new String[]{".cs"}), // C#

    CSS(10, new String[]{".css"}),

    D(11, new String[]{".d"}),

    FORTRAN(12, new String[]{".f", ".for", ".f90", ".f95", ".f2k"}),

    HASKELL(13, new String[]{".hs", ".lhs", ".las"}),

    HTML(14, new String[]{".html", ".htm", ".shtml", ".shtm", ".xhtml"}),

    INI(15, new String[]{".yml", ".properties", ".conf", ".config", ".ini"}),

    JAVA(16, new String[]{".java"}),

    JAVASCRIPT(17, new String[]{".js"}),

    JSON(18, new String[]{".json"}),

    JSP(19, new String[]{".jsp"}),

    LISP(20, new String[]{".lsp", ".lisp"}),

    LUA(21, new String[]{".lua"}),

    NORMALTEXT(22, new String[]{".txt"}),

    OBJECTIVEC(23, new String[]{".h", ".m", ".mm"}),

    PASCAL(24, new String[]{".dpr", ".dpk", ".pas", ".dfm", ".inc", ".pp"}),

    PERL(25, new String[]{".pl", ".pm", ".plx"}),

    PHP(26, new String[]{".php", ".php3", ".php4", ".php5", ".phtm"}),

    PYTHON(27, new String[]{".py", ".py3", ".pyw"}),

    RUBY(28, new String[]{".rb", ".rbw"}),

    SQL(29, new String[]{".sql"}),

    VB(30, new String[]{".vb", ".bas", ".frm", ".cls", ".ctl", ".pag", ".dsr", ".dob", ".vbs", ".dsm", ".vbp", ".vbg", ".mak", ".vbw"}),

    VERILOG(31, new String[]{".v", ".vl", ".vmd"}),

    VHDL(32, new String[]{".vhd", ".vhdl", ".vho"}),

    XML(33, new String[]{".xml", ".xsl", ".svg", ".xul", ".xsd", ".dtd", ".xslt", ".axl", ".xrc", ".rdf"}),

    UNKNOWN(34, new String[]{});// 未知编程语言类型

    /**
     * coolFormat对应值
     */
    @Getter
    private final int coolFormatDictionaryValue;

    /**
     * 对应编程语言后缀格式
     */
    @Getter
    private final String[] suffixList;


    private CoolFormatLanguageAttribute(int coolFormatDictionaryValue, String[] suffixList) {
        // TODO Auto-generated constructor stub
        this.coolFormatDictionaryValue = coolFormatDictionaryValue;
        this.suffixList = suffixList;
    }

    /**
     * 根据后缀获取 coolFormat 的字典值
     *
     * @param suffix
     * @return
     */
    public static int getCoolFormatDictionaryValueBy(String suffix) {
        int value = UNKNOWN.coolFormatDictionaryValue;
        boolean flag = false;
        for (CoolFormatLanguageAttribute temp : CoolFormatLanguageAttribute.values()) {
            String[] theSuffixList = temp.suffixList;
            for (String suffixTemp : theSuffixList) {
                if (suffixTemp.equals(suffix)) {
                    value = temp.coolFormatDictionaryValue;
                    flag = true;
                    break;
                }
            }
            if (flag == true) {
                break;
            }
        }
        return value;
    }


}
