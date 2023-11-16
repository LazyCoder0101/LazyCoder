package com.lazycoder.lazycodercommon.vo.CodeUseProperty;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class NeedUseModuleImportCode extends AbstractCodeUseProperty {

    @Getter
    @Setter
    private ArrayList<String> needUseModuleList = new ArrayList<>();

    public NeedUseModuleImportCode() {
        this.codeUsePropertyType = NeedUseModuleImportCodeType;
    }

    @Override
    public String getShowText() {
        return "插入需要使用模块的引入代码";
    }

}
