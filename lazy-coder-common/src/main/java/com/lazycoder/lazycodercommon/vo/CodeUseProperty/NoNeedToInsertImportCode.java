package com.lazycoder.lazycodercommon.vo.CodeUseProperty;

public class NoNeedToInsertImportCode extends AbstractCodeUseProperty {

    public NoNeedToInsertImportCode() {
        this.codeUsePropertyType = AbstractCodeUseProperty.NoNeedToInsertImportCodeType;
    }

    @Override
    public String getShowText() {
        return "无需插入本模块引入代码";
    }

}
