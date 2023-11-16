package com.lazycoder.uicodegeneration.generalframe;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.CodeFormatFlagParamArrayListDeserializer;
import com.lazycoder.uicodegeneration.component.generalframe.FormatControlPaneLable;
import com.lazycoder.uicodegeneration.component.generalframe.FormatControlPaneLableListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.AdditionalFormatFunctionNameListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.ModuleCustomFunctionNameListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.ModuleFormatFunctionNameListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AdditionalFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.tool.FileRecord;
import com.lazycoder.uicodegeneration.generalframe.tool.FileRecordDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.AdditionalFormatVariableListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.ModuleCustomVariableListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.ModuleFormatVariableListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AdditionalFormatVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.ModuleCustomVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.ModuleFormatVariableHolder;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CodeGenerationFrameModel {

    private String proID;//项目id

    private String useUserDBID;//使用的数据库id

    @JSONField(name = "moduleFormatVariableList", deserializeUsing = ModuleFormatVariableListDeserializer.class)
    private ArrayList<ModuleFormatVariableHolder> moduleFormatVariableList = new ArrayList<>();

    @JSONField(name = "moduleCustomVariableList", deserializeUsing = ModuleCustomVariableListDeserializer.class)
    private ArrayList<ModuleCustomVariableHolder> moduleCustomVariableList = new ArrayList<>();

    @JSONField(name = "additionalFormatVariableList", deserializeUsing = AdditionalFormatVariableListDeserializer.class)
    private ArrayList<AdditionalFormatVariableHolder> additionalFormatVariableList = new ArrayList<>();

    @JSONField(name = "additionalFormatFunctionNameHolderList", deserializeUsing = AdditionalFormatFunctionNameListDeserializer.class)
    private ArrayList<AdditionalFormatFunctionNameHolder> additionalFormatFunctionNameHolderList = new ArrayList<>();

    @JSONField(name = "moduleCustomFunctionNameHolderList", deserializeUsing = ModuleCustomFunctionNameListDeserializer.class)
    private ArrayList<ModuleCustomFunctionNameHolder> moduleCustomFunctionNameHolderList = new ArrayList<>();

    @JSONField(name = "moduleFormatFunctionNameHolderList", deserializeUsing = ModuleFormatFunctionNameListDeserializer.class)
    private ArrayList<ModuleFormatFunctionNameHolder> moduleFormatFunctionNameHolderList = new ArrayList<>();

    @JSONField(deserializeUsing = FileRecordDeserializer.class)
    private ArrayList<FileRecord> fileRecordList = new ArrayList<>();

    /**
     * 格式控制列表列表
     */
    @JSONField(deserializeUsing = FormatControlPaneLableListDeserializer.class)
    private ArrayList<FormatControlPaneLable> formatControlPaneLableList = new ArrayList<>();

    /**
     * 代码面板信息列表
     */
    @JSONField(deserializeUsing = CodeFormatFlagParamArrayListDeserializer.class)
    private ArrayList<CodeFormatFlagParam> codeFormatFlagParamList = new ArrayList<>();

    /**
     * 生成这个项目所使用的客户端，对应的客户端版本（最后点击生成代码的时候写入，这里只是给个初始值）
     */
    private String proClientVersion = System.getProperty("clientVersion");

    /**
     * 生成这个项目所使用的数据源，对应的客户端版本（最后点击生成代码的时候写入，这里只是给个初始值）
     */
    private String dataSourceClientVersion = System.getProperty("clientVersion");

    /**
     * 代码id
     */
    private int codeSerialNumber = 0;

    /**
     * 功能拓展id
     */
    private int functionAddBeanSerialNumber = 0;

    /**
     * 方法名id
     */
    private int functionNameSerialNumber = 0;

    /**
     * 变量名id
     */
    private int variableSerialNumber = 0;

}
