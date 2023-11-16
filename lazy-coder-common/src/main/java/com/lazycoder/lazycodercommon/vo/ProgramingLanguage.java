package com.lazycoder.lazycodercommon.vo;


import lombok.Getter;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

/**
 * 编程语言(以下枚举成员并非都是编程语言)
 *
 * @author admin
 */
public enum ProgramingLanguage {

    C(1, "C语言 / C++", SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS, CoolFormatLanguageAttribute.CPP),
    JAVA(2, "Java", SyntaxConstants.SYNTAX_STYLE_JAVA, CoolFormatLanguageAttribute.JAVA),
    ASM(3, "汇编", SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86, CoolFormatLanguageAttribute.ASM),
    CSHARP(4, "C#", SyntaxConstants.SYNTAX_STYLE_CSHARP, CoolFormatLanguageAttribute.CS),
    PHP(5, "PHP", SyntaxConstants.SYNTAX_STYLE_PHP, CoolFormatLanguageAttribute.PHP),
    PYTHON(6, "Python", SyntaxConstants.SYNTAX_STYLE_PYTHON, CoolFormatLanguageAttribute.PYTHON),
    JAVASCRIPT(7, "JavaScript", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, CoolFormatLanguageAttribute.JAVASCRIPT),
    JSP(8, "JSP", SyntaxConstants.SYNTAX_STYLE_JSP, CoolFormatLanguageAttribute.JSP),
    HTML(9, "HTML", SyntaxConstants.SYNTAX_STYLE_HTML, CoolFormatLanguageAttribute.HTML),
    CSS(10, "CSS", SyntaxConstants.SYNTAX_STYLE_CSS, CoolFormatLanguageAttribute.CSS),
    JSON(11, "json", SyntaxConstants.SYNTAX_STYLE_JSON, CoolFormatLanguageAttribute.JSON),
    XML(12, "xml", SyntaxConstants.SYNTAX_STYLE_XML, CoolFormatLanguageAttribute.XML),
    SQL(13, "SQL", SyntaxConstants.SYNTAX_STYLE_SQL, CoolFormatLanguageAttribute.SQL),
    PERL(14, "Perl", SyntaxConstants.SYNTAX_STYLE_PERL, CoolFormatLanguageAttribute.PERL),
    RUBY(15, "Ruby", SyntaxConstants.SYNTAX_STYLE_RUBY, CoolFormatLanguageAttribute.RUBY),
    VB(16, "VB", SyntaxConstants.SYNTAX_STYLE_VISUAL_BASIC, CoolFormatLanguageAttribute.VB),
    VERILOG(17, "Verilog", SyntaxConstants.SYNTAX_STYLE_JAVA, CoolFormatLanguageAttribute.VERILOG),
//    VHDL(18,"Vhdl",SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86, CoolFormatLanguageAttribute.VHDL),
    PROPERTIES(19, "配置文件", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE, CoolFormatLanguageAttribute.INI),
    ASP(20, "ASP",SyntaxConstants.SYNTAX_STYLE_HTML, CoolFormatLanguageAttribute.ASP),
    ACTIONSCRIPT(21, "ASP",SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT, CoolFormatLanguageAttribute.ACTIONSCRIPT),
    BATCH(22, "ASP",SyntaxConstants.SYNTAX_STYLE_WINDOWS_BATCH, CoolFormatLanguageAttribute.BATCH);

//	VHDL(18,"Vhdl",SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86, LanguageAttribute.VHDL);

    /**
     *  case "ACTIONSCRIPT" : return 0 ;
     *             case "ADA" : return  1 ;
     *             case "AUTOHOTKEY" : return 4 ;
     *             case "AUTOIT" : return 5 ;
     *             case "BATCH" : return 6 ;
     *             case "COBOL" : return 7 ;
     *             case "D" : return 11 ;
     *             case "FORTRAN" : return 12 ;
     *             case "HASKELL" : return 13 ;
     *             case "INI" : return 15 ;
     *             case "LISP" : return 20 ;
     *             case "LUA" : return 21 ;
     *             case "NORMALTEXT" : return 22 ;
     *             case "OBJECTIVEC" : return 23 ;
     *             case "PASCAL" : return 24 ;
     *             case "PERL" : return 25 ;
     *             case "RUBY" : return 28 ;
     *             case "VB" : return 30 ;
     *             case "VERILOG" : return 31 ;
     *             case "VHDL" : return 32 ;
     *             default:return 34 ;
     */

    /**
     * 编程语言显示名称
     */
    @Getter
    private final String languageName;

    /**
     * 对应编程语言后缀格式
     */
    @Getter
    private final String[] suffixList;

    /**
     * 对应系统字典值
     */
    @Getter
    private final int sysDictionaryValue;

    @Getter
    private final String rsyntaxTextAreaEditingStyle;

    /**
     * codeFormat这个jar的对应属性
     */
    @Getter
    private final CoolFormatLanguageAttribute languageAttribute;

    private ProgramingLanguage(int sysDictionaryValue, String languageName,
                               String rsyntaxTextsAreaEditingStyle, CoolFormatLanguageAttribute languageAttribute) {
        // TODO Auto-generated constructor stub
        this.sysDictionaryValue = sysDictionaryValue;
        this.languageName = languageName;
        this.suffixList = languageAttribute.getSuffixList();
        this.rsyntaxTextAreaEditingStyle = rsyntaxTextsAreaEditingStyle;
        this.languageAttribute = languageAttribute;
    }

    /**
     * 根据字典值返回对应编程语言
     *
     * @param sysDictionaryValue
     * @return
     */
    public static ProgramingLanguage getEncodingTypeBy(int sysDictionaryValue) {
        ProgramingLanguage programingLanguage = null;
        for (ProgramingLanguage temp : ProgramingLanguage.values()) {
            if (temp.getSysDictionaryValue() == sysDictionaryValue) {
                programingLanguage = temp;
                break;
            }
        }
        return programingLanguage;
    }

    /**
     * 根据后缀名获取对应的编程语言
     *
     * @param suffix
     * @return
     */
    public static ProgramingLanguage getCorrespondingtProgramingLanguage(String suffix) {
        ProgramingLanguage programingLanguage = null;
        boolean flag = false;
        for (ProgramingLanguage temp : ProgramingLanguage.values()) {
            for (String suffixTemp : temp.suffixList) {
                if (suffix.equals(suffixTemp)) {
                    flag = true;
                    programingLanguage = temp;
                    break;
                }
            }
            if (flag == true) {
                break;
            }
        }
        return programingLanguage;
    }


}
