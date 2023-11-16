package com.lazycoder.uicodegeneration.component.operation.container.sendparam;

import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NeedFirstOpratingContainerParam {

    private BaseMarkElement thanMarkElement = null;

    private CodeShowPane theCodePane = null;

    private String codeStatementParam = "";

    private ArrayList<AbstractCodeUseProperty> codeUsePropertyList = new ArrayList<>();

}
