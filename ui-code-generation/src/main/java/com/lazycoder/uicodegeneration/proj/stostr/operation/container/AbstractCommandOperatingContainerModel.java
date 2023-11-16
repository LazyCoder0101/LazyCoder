package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.PathFindDeserializer;
import com.lazycoder.uicodegeneration.proj.deserializer.OperatingPaneElementDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import java.util.ArrayList;
import lombok.Data;

/**
 * 命令模型
 *
 * @author admin
 */
@Data
public abstract class AbstractCommandOperatingContainerModel extends AbstractOperatingContainerModel {


    public final static String FUNCTION_OPERATING_CONTAINER = "functionOperatingContainer";

    public final static String MODULE_SET_OPERATING_CONTAINER = "moduleSetOperatingContainer";

    public final static String INIT_OPERATING_CONTAINER = "initOperatingContainer";

    public final static String FUNCTION_ADD_INTERNAL_OPERATING_CONTAINER = "functionAddInternalOperatingContainer";

    public final static String MAIN_SET_OPERATING_CONTAINER = "mainSetOperatingContainer";

    public final static String ADDITIONAL_SER_OPERATING_CONTAINER = "additionalSetOperatingContainer";

    public final static String ADDITIONAL_FUNCTION_OPERATING_CONTAINER = "additionalFunctionOperatingContainer";
    /**
     * 隐藏使用状态
     */
    protected boolean hiddenState = true;

    private boolean canBeDelOrNot = false;

    private String commandOperatingContainerModelType;

    private int codeSerialNumber = 0;

    @JSONField(deserializeUsing = PathFindDeserializer.class)
    private PathFind pathFind;

    /**
     * 隐藏面板
     */
    @JSONField(deserializeUsing = OperatingPaneElementDeserializer.class)
    private ArrayList<AbstractOpratingPaneElement> hiddenPaneElementList = new ArrayList<>();


    public AbstractCommandOperatingContainerModel() {
        // TODO Auto-generated constructor stub
        super(COMMAND_CONTAINER_TYPE);
    }

    public AbstractCommandOperatingContainerModel(String commandOperatingContainerModelType) {
        this();
        this.commandOperatingContainerModelType = commandOperatingContainerModelType;
    }

    /**
     * 获取命令类型
     *
     * @param elementJSONObject
     * @return
     */
    public static String getCommandType(JSONObject elementJSONObject) {
        return elementJSONObject.getString("commandOperatingContainerModelType");
    }

}
