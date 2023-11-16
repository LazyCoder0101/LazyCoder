package com.lazycoder.service.vo.element.lable.control.functionadd;


import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MethodAddStorageFormat extends GeneralCommandOperatingModel {

    /**
     * 代码参数
     */
    private String codeStatementFormat;

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

    @JSONField(deserializeUsing = FunctionAddCodeModelDeserializer.class)
    private ArrayList<FunctionAddCodeModel> codeModelList = new ArrayList<>();

}

