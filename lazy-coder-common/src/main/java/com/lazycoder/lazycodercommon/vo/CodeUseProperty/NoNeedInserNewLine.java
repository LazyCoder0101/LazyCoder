package com.lazycoder.lazycodercommon.vo.CodeUseProperty;

public class NoNeedInserNewLine extends AbstractCodeUseProperty {

    public NoNeedInserNewLine() {
        this.codeUsePropertyType = AbstractCodeUseProperty.NoNeedInserNewLine;
    }

    @Override
    public String getShowText() {
        return "无需插入换行";
    }

}
