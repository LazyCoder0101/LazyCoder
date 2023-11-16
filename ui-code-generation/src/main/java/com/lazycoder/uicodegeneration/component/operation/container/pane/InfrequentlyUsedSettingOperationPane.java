package com.lazycoder.uicodegeneration.component.operation.container.pane;


import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.InfrequentlyUsedSettingMeta;
import java.util.ArrayList;

public class InfrequentlyUsedSettingOperationPane extends AbstractOperatingPane {

    /**
     *
     */
    private static final long serialVersionUID = -672956672973688513L;

    private InfrequentlyUsedSettingControl controlElement;

    private GeneralContainerComponentParam thisCodeGenerationalOpratingContainerParam;

    public InfrequentlyUsedSettingOperationPane(OpratingContainerInterface opratingContainer,
                                                GeneralContainerComponentParam codeGenerationalOpratingContainerParam, InfrequentlyUsedSettingControl controlElement) {
        super(opratingContainer);
        this.controlElement = controlElement;

        if (codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane() != null) {
            attrset = codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane().attrset;
            setParagraphAttributes(attrset, false);
        }

        thisCodeGenerationalOpratingContainerParamInit(codeGenerationalOpratingContainerParam);
        generateOperationalContent(thisCodeGenerationalOpratingContainerParam);
    }

    public InfrequentlyUsedSettingOperationPane(OpratingContainerInterface opratingContainer,
                                                GeneralContainerComponentParam codeGenerationalOpratingContainerParam, InfrequentlyUsedSettingMeta meta) {
        super(opratingContainer);
        this.controlElement = meta.getControlElement();
        thisCodeGenerationalOpratingContainerParamInit(codeGenerationalOpratingContainerParam);
        restoreContent(meta.getPaneElementList(),
                thisCodeGenerationalOpratingContainerParam);
    }

    private void thisCodeGenerationalOpratingContainerParamInit(
            GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        thisCodeGenerationalOpratingContainerParam = new GeneralContainerComponentParam();
//		BeanUtils.copyProperties(codeGenerationalOpratingContainerParam,thisCodeGenerationalOpratingContainerParam);

        thisCodeGenerationalOpratingContainerParam
                .setFormatControlPane(codeGenerationalOpratingContainerParam.getFormatControlPane());
        thisCodeGenerationalOpratingContainerParam
                .setThisOpratingContainer(codeGenerationalOpratingContainerParam.getThisOpratingContainer());
        thisCodeGenerationalOpratingContainerParam
                .setControlStatementFormat(controlElement.getControlStatementFormat());
        thisCodeGenerationalOpratingContainerParam
                .setCodeControlPane(codeGenerationalOpratingContainerParam.getCodeControlPane());
        thisCodeGenerationalOpratingContainerParam
                .setCodeSerialNumber(codeGenerationalOpratingContainerParam.getCodeSerialNumber());
        thisCodeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(
                codeGenerationalOpratingContainerParam.getFirstCommandOpratingContainer());
        thisCodeGenerationalOpratingContainerParam
                .setFormatContainer(codeGenerationalOpratingContainerParam.getFormatContainer());
        thisCodeGenerationalOpratingContainerParam.setOperatingComponentPlacePane(
                codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane());
        thisCodeGenerationalOpratingContainerParam
                .setParentPathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        thisCodeGenerationalOpratingContainerParam.setPaneType(codeGenerationalOpratingContainerParam.getPaneType());

        thisCodeGenerationalOpratingContainerParam.setModule(codeGenerationalOpratingContainerParam.getModule());
        thisCodeGenerationalOpratingContainerParam.setModuleInfo(codeGenerationalOpratingContainerParam.getModuleInfo());

        thisCodeGenerationalOpratingContainerParam.setThisCustomFunctionNameHolder(codeGenerationalOpratingContainerParam.getThisCustomFunctionNameHolder());
        thisCodeGenerationalOpratingContainerParam.setThisCustomVariableHolder(codeGenerationalOpratingContainerParam.getThisCustomVariableHolder());

        thisCodeGenerationalOpratingContainerParam.setParentOpratingContainer(codeGenerationalOpratingContainerParam.getThisOpratingContainer());
    }


    public GeneralContainerComponentParam getThisCodeGenerationalOpratingContainerParam() {
        return thisCodeGenerationalOpratingContainerParam;
    }

    public InfrequentlyUsedSettingControl getControlElement() {
        return controlElement;
    }

    @Override
    public void generateOperationalContent(GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        // TODO Auto-generated method stub
        codeGenerationalOpratingContainerParam.setPaneType(thisCodeGenerationalOpratingContainerParam.getPaneType());
        super.generateOperationalContent(codeGenerationalOpratingContainerParam);
    }

    @Override
    public void restoreContent(ArrayList<AbstractOpratingPaneElement> paneElementList,
                               GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        codeGenerationalOpratingContainerParam.setPaneType(thisCodeGenerationalOpratingContainerParam.getPaneType());
        super.restoreContent(paneElementList, codeGenerationalOpratingContainerParam);
    }

}
