package com.lazycoder.uicodegeneration.generalframe.codeshown;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.CPPFormat;
import com.lazycoder.codeformat.format.CSFormat;
import com.lazycoder.codeformat.format.CSSFormat;
import com.lazycoder.codeformat.format.HTMLFormat;
import com.lazycoder.codeformat.format.JSPFormat;
import com.lazycoder.codeformat.format.JavaFormat;
import com.lazycoder.codeformat.format.JavaScriptFormat;
import com.lazycoder.codeformat.format.JsonFormat;
import com.lazycoder.codeformat.format.PHPFormat;
import com.lazycoder.codeformat.format.PerlFormat;
import com.lazycoder.codeformat.format.PythonFormat;
import com.lazycoder.codeformat.format.RubyFormat;
import com.lazycoder.codeformat.format.SQLFormat;
import com.lazycoder.codeformat.format.VBFormat;
import com.lazycoder.codeformat.format.VerilogFormat;
import com.lazycoder.codeformat.format.XMLFormat;
import com.lazycoder.codeformat.format.coolformat.CoolFormatResponse;
import com.lazycoder.codeformat.format.coolformat.DoFormatter;
import com.lazycoder.lazycodercommon.vo.CoolFormatLanguageAttribute;
import com.lazycoder.lazycodercommon.vo.ProgramingLanguage;
import com.lazycoder.service.service.SysService;
import com.lazycoder.utils.JsonUtil;
import java.util.HashMap;
import java.util.Map;

public class CodeFormatService {

    /**
     * 代码格式化方式
     */
    private int formatStyle = CODE_FORMAT_SERVICE_STYPE;

    private static final int NOT = 0;//不进行代码格式化

    private static final int CODE_FORMAT_SERVICE_STYPE = 1;//通过代码格式化服务来进行代码格式化

    private static final int COOL_FORMAT_STYPE = 2;//通过 CoolFormat 来进行代码格式化

    private int coolFormatCode = CoolFormatLanguageAttribute.UNKNOWN.getCoolFormatDictionaryValue();
    /**
     * 对应的代码格式化类
     */
    private FormatInterface format = null;

    public CodeFormatService(String suffix) {
        init(suffix);
    }

    protected void init(String suffix) {
        formatStyle = CODE_FORMAT_SERVICE_STYPE;
        ProgramingLanguage programingLanguage = ProgramingLanguage.getCorrespondingtProgramingLanguage(suffix);
        //查一下有没有对应的代码格式化类
        if (programingLanguage == ProgramingLanguage.C) {
            format = new CPPFormat();

        } else if (programingLanguage == ProgramingLanguage.JAVA) {
            format = new JavaFormat();

        } else if (programingLanguage == ProgramingLanguage.HTML) {
            format = new HTMLFormat();

        } else if (programingLanguage == ProgramingLanguage.CSS) {
            format = new CSSFormat();

        } else if (programingLanguage == ProgramingLanguage.JAVASCRIPT) {
            format = new JavaScriptFormat();

        } else if (programingLanguage == ProgramingLanguage.CSHARP) {
            format = new CSFormat();

        } else if (programingLanguage == ProgramingLanguage.PHP) {
            format = new PHPFormat();

        } else if (programingLanguage == ProgramingLanguage.PYTHON) {
            format = new PythonFormat();

        } else if (programingLanguage == ProgramingLanguage.XML) {
            format = new XMLFormat();

        } else if (programingLanguage == ProgramingLanguage.JSP) {
            format = new JSPFormat();

        } else if (programingLanguage == ProgramingLanguage.VB) {
            format = new VBFormat();

        } else if (programingLanguage == ProgramingLanguage.JSON) {
            format = new JsonFormat();

        } else if (programingLanguage == ProgramingLanguage.SQL) {
            format = new SQLFormat();

        } else if (programingLanguage == ProgramingLanguage.PERL) {
            format = new PerlFormat();

        } else if (programingLanguage == ProgramingLanguage.RUBY) {
            format = new RubyFormat();

        } else if (programingLanguage == ProgramingLanguage.VERILOG) {
            format = new VerilogFormat();

        } else {
            //没有对应的代码格式化类，看看能不能用CoolFormat格式化
            int returnCoolFormatCode = CoolFormatLanguageAttribute.getCoolFormatDictionaryValueBy(suffix);
            if (returnCoolFormatCode == CoolFormatLanguageAttribute.UNKNOWN.getCoolFormatDictionaryValue()) {//无法判断其对应的编程语言类型
                this.formatStyle = NOT;
            } else {//可以用coolFormat来进行格式化
                this.coolFormatCode = returnCoolFormatCode;
                this.formatStyle = COOL_FORMAT_STYPE;
            }
        }
    }

    public String formatter(String fromCode) {
        String formatCode = fromCode;
        if (this.formatStyle == CODE_FORMAT_SERVICE_STYPE) {
            if (this.format != null) {
                formatCode = this.format.formatter(fromCode);
            }

        } else if (this.formatStyle == COOL_FORMAT_STYPE) {
            CoolFormatResponse temp = null;
            try {
                temp = DoFormatter
                        .formatter(this.coolFormatCode, fromCode);
            } catch (Exception e) {
                //e.printStackTrace();
                temp = new CoolFormatResponse();
                temp.setSuccess(false);

                Map<String, Object> error = new HashMap<String, Object>();
                error.put("异常信息",e.getMessage());
                error.put("异常位置",e.getStackTrace());
                error.put("异常原因",e.getCause());
                error.put("异常异常的本地化信息",e.getLocalizedMessage());
                error.put("异常类型",e.getClass());

                SysService.SYS_SERVICE_SERVICE.log_error("CodeFormatService调用coolformat错误：" + JsonUtil.getJsonStr(error));
            }
            if (temp.isSuccess() == true) {//调用coolFormat成功
                formatCode = temp.getFormatContent();
//            } else {//调用coolFormat失败
            }
//        } else if (this.formatStyle == NOT) {
//            formatCode = fromCode;
        }
        return formatCode;
    }

}
