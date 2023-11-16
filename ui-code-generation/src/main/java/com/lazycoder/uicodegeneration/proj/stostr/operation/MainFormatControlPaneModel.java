package com.lazycoder.uicodegeneration.proj.stostr.operation;


import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.format.MainOperating;
import com.lazycoder.database.model.format.deserializer.MainOperatingDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.MainCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.MainFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.MainCustomFunctionNameHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.MainCustomVariableHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.MainFormatFunctionNameHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.MainFormatVariableHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.MainCustomVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.MainFormatVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractFormatControlPaneModel;
import lombok.Data;

@Data
public class MainFormatControlPaneModel extends AbstractFormatControlPaneModel {

    /**
     * 自带的变量，初始化时都放这里
     */
    @JSONField(deserializeUsing = MainFormatVariableHolderDeserializer.class)
    private MainFormatVariableHolder formatVariableHolder = null;

    @JSONField(deserializeUsing = MainFormatFunctionNameHolderDeserializer.class)
    private MainFormatFunctionNameHolder formatFunctionNameHolder = null;

    @JSONField(deserializeUsing = MainCustomVariableHolderDeserializer.class)
    private MainCustomVariableHolder customVariableHolder = null;

    @JSONField(deserializeUsing = MainCustomFunctionNameHolderDeserializer.class)
    private MainCustomFunctionNameHolder customFunctionNameHolder = null;

    @JSONField(deserializeUsing = MainOperatingDeserializer.class)
    private MainOperating operating = null;

    public MainFormatControlPaneModel() {
        // TODO Auto-generated constructor stub
        super(MAIN_FORMAT_CONTROL_PANE);
    }

}
