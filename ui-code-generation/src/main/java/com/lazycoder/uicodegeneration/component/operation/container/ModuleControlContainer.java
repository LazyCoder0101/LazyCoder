package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.ModuleControl;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.typeset.module.ModuleTypeContainer;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FormatOpratingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.ModuleTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleControlContainerModel;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

public class ModuleControlContainer extends AbstractFormatContainer {

    /**
     *
     */
    private static final long serialVersionUID = -4295431847007760024L;

    private ModuleTypeOperatingContainerParam moduleTypeContainerParam;

    public ModuleControlContainer() {
        super();
        // TODO Auto-generated constructor stub
        init(false, true, "模块控制", ModuleTypeContainer.THE_PROPORTION);
    }

    /**
     * 还原
     *
     * @param model
     * @param moduleTypeContainerParam
     * @param codeControlPane
     */
    public void restore(ModuleControlContainerModel model, ModuleTypeOperatingContainerParam moduleTypeContainerParam,
                        AbstractCodeControlPane codeControlPane) {
        this.moduleTypeContainerParam = moduleTypeContainerParam;
        FormatOpratingContainerParam codeGenerationalOpratingContainerParam = new FormatOpratingContainerParam();
        codeGenerationalOpratingContainerParam.setFormatControlPane(moduleTypeContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam.setCodeControlPane(codeControlPane);
        codeGenerationalOpratingContainerParam.setModule(moduleTypeContainerParam.getModule());
        codeGenerationalOpratingContainerParam.setModuleInfo(moduleTypeContainerParam.getModuleInfo());
        codeGenerationalOpratingContainerParam.setCodeSerialNumber(0);
        PathFind pathFind = new PathFind(MarkElementName.MODULE_CONTROL, PathFind.FORMAT_TYPE);
        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);
        codeGenerationalOpratingContainerParam.setFormatControlPane(moduleTypeContainerParam.getFormatControlPane());

        restoreContent(model, codeGenerationalOpratingContainerParam);
        setAppropriateSize();
    }

    /**
     * 新建
     *
     * @param moduleTypeContainerParam
     * @param codeControlPane
     * @return
     */
    public void build(ModuleTypeOperatingContainerParam moduleTypeContainerParam,
                      AbstractCodeControlPane codeControlPane) {
        this.moduleTypeContainerParam = moduleTypeContainerParam;

        ModuleControl moduleControl = SysService.MODULE_CONTROL_SERVICE.getModuleControl(moduleTypeContainerParam.getModule().getModuleId());
        if (moduleControl != null) {
            FormatOpratingContainerParam codeGenerationalOpratingContainerParam = new FormatOpratingContainerParam();
            codeGenerationalOpratingContainerParam
                    .setFormatControlPane(moduleTypeContainerParam.getFormatControlPane());
            codeGenerationalOpratingContainerParam.setCodeControlPane(codeControlPane);
            codeGenerationalOpratingContainerParam.setModule(moduleTypeContainerParam.getModule());
            codeGenerationalOpratingContainerParam.setModuleInfo(moduleTypeContainerParam.getModuleInfo());
            codeGenerationalOpratingContainerParam.setCodeSerialNumber(0);
            PathFind pathFind = new PathFind(MarkElementName.MODULE_CONTROL, PathFind.FORMAT_TYPE);
            codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);
            codeGenerationalOpratingContainerParam
                    .setControlStatementFormat(moduleControl.getDefaultControlStatementFormat());

            generateOperationalContent(codeGenerationalOpratingContainerParam);

            codeGenerationalOpratingContainerParam.setControlStatementFormat(null);
            setAppropriateSize();
        }
    }

    @Override
    public ArrayList<CodeShowPane> getCodePaneList() {
        // TODO Auto-generated method stub
        return CodeGenerationFrameHolder.codeShowPanel.getModulePaneList(moduleTypeContainerParam.getModule().getModuleId());
    }

    @Override
    public ArrayList<CodeFormatFlagParam> getCodePaneRelatedInfoList() {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>();
        ArrayList<CodeShowPane> codeShowPaneList = CodeGenerationFrameHolder.codeShowPanel.getModulePaneList(moduleTypeContainerParam.getModule().getModuleId());
        for (CodeShowPane codeShowPane : codeShowPaneList) {
            list.add(codeShowPane.getCodeFormatFlagParam());
        }
        return list;
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getModulePictureFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleTypeContainerParam.getModule().getModuleId());
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getModuleNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleTypeContainerParam.getModule().getModuleId());
        return file;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        ModuleControlContainerModel theModel = (ModuleControlContainerModel) model;
        super.setParam(theModel);
    }

    @Override
    public ModuleControlContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        ModuleControlContainerModel model = new ModuleControlContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.MODULE_CONTROL;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return moduleTypeContainerParam.getModule().getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return moduleTypeContainerParam.getModule().getModuleName();
    }

    @Override
    public String getModuleId() {
        return moduleTypeContainerParam.getModule().getModuleId();
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer() {
        return null;
    }

    @Override
    public AbstractFormatContainer getFormatContainer() {
        return this;
    }

    @Override
    public Dimension getRequiredDimension() {
        return super.getRequiredDimension();
    }

}
