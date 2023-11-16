package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.typeset.format.additional.AdditionalSetTypeFolder;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.component.FormatTypePane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FormatOpratingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalFormatContainerModel;
import java.io.File;
import java.util.ArrayList;

public class AdditionalFormatContainer extends AbstractFormatContainer {

    /**
     *
     */
    private static final long serialVersionUID = 1379258618180880378L;

    private int additionalSerialNumber = 0;

    private AdditionalSetTypeFolder additionalSetTypeFolder;

    private AdditionalInfo additionalInfo;

    public AdditionalFormatContainer(FormatOpratingContainerParam formatOpratingContainerParam, int additionalSerialNumber,
                                     String fileName, AdditionalInfo additionalInfo) {
        // TODO Auto-generated constructor stub
        super();
        this.additionalInfo = additionalInfo;
        if (additionalInfo.getFormatState() == MainInfo.TRUE_) {
            init(true, true, "格式", AbstractCommandOpratingContainer.PROPOTION);
        } else {
            init(false, true, "格式", AbstractCommandOpratingContainer.PROPOTION);

        }
        this.formatOpratingContainerParam = formatOpratingContainerParam;
        this.additionalSerialNumber = additionalSerialNumber;
        currentCodeFileName = fileName;
        pathFind = formatOpratingContainerParam.getParentPathFind();
        generateOperationalContent(formatOpratingContainerParam);
        if (formatState == true) {
            AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam = new AdditionalSetTypeOperatingContainerParam();
            additionalSetTypeOperatingContainerParam.setFormatContainer(AdditionalFormatContainer.this);
            additionalSetTypeOperatingContainerParam
                    .setFormatControlPane(formatOpratingContainerParam.getFormatControlPane());
            additionalSetTypeOperatingContainerParam.setAdditionalInfo(additionalInfo);

            additionalSetTypeFolder = new AdditionalSetTypeFolder(additionalSetTypeOperatingContainerParam);
            formatTypePane.setInternalComponent(additionalSetTypeFolder.getParentScrollPane());
        }
        setAppropriateSize();
    }


    /**
     * @param formatOpratingContainerParam
     * @param model
     */
    public AdditionalFormatContainer(FormatOpratingContainerParam formatOpratingContainerParam,
                                     AdditionalFormatContainerModel model) {

        super();
        this.formatOpratingContainerParam = formatOpratingContainerParam;
        pathFind = formatOpratingContainerParam.getParentPathFind();
        this.additionalSerialNumber = model.getAdditionalSerialNumber();
        this.additionalInfo = model.getAdditionalInfo();
        if (model.isFormatState() == true) {
            init(true, true, "格式", AbstractCommandOpratingContainer.PROPOTION);
        } else if (model.isFormatState() == false) {
            init(false, true, "格式", AbstractCommandOpratingContainer.PROPOTION);
        }

        restoreContent(model, formatOpratingContainerParam);
        if (formatState == true) {
            AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam = new AdditionalSetTypeOperatingContainerParam();
            additionalSetTypeOperatingContainerParam.setFormatContainer(AdditionalFormatContainer.this);
            additionalSetTypeOperatingContainerParam
                    .setFormatControlPane(formatOpratingContainerParam.getFormatControlPane());
            additionalSetTypeOperatingContainerParam.setAdditionalInfo(additionalInfo);

            additionalSetTypeFolder = new AdditionalSetTypeFolder(additionalSetTypeOperatingContainerParam,
                    model.getAdditionalSetTypeFolderModel());
            formatTypePane.setInternalComponent(additionalSetTypeFolder.getParentScrollPane());
        }
        setAppropriateSize();
    }


    @Override
    public ArrayList<CodeShowPane> getCodePaneList() {
        // TODO Auto-generated method stub
        ArrayList<CodeShowPane> list = new ArrayList<>();
        CodeShowPane deafaultPane = CodeGenerationFrameHolder.codeShowPanel.getAdditionalDeafaultCodePane(additionalSerialNumber,
                getCurrentCodeFileName());
        list.add(deafaultPane);
        ArrayList<CodeShowPane> subList = CodeGenerationFrameHolder.codeShowPanel
                .getAdditionalSubCodePaneList(additionalSerialNumber);
        list.addAll(subList);
        return list;
    }

    @Override
    public ArrayList<CodeFormatFlagParam> getCodePaneRelatedInfoList() {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>();
        CodeShowPane deafaultPane = CodeGenerationFrameHolder.codeShowPanel.getAdditionalDeafaultCodePane(additionalSerialNumber,
                getCurrentCodeFileName());
        list.add(deafaultPane.getCodeFormatFlagParam());
        ArrayList<CodeShowPane> subList = CodeGenerationFrameHolder.codeShowPanel
                .getAdditionalSubCodePaneList(additionalSerialNumber);
        for (CodeShowPane codeShowPane : subList) {
            list.add(codeShowPane.getCodeFormatFlagParam());
        }
        return list;
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getAdditionalPictureFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName, getAdditionalSerialNumber());
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getAdditionalNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName, getAdditionalSerialNumber());
        return file;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        AdditionalFormatContainerModel theModel = (AdditionalFormatContainerModel) model;
        super.setParam(theModel);
        theModel.setAdditionalInfo(additionalInfo);
        if (additionalInfo.getNumOfSetCodeType() > 0) {
            theModel.setAdditionalSetTypeFolderModel(additionalSetTypeFolder.getFormatStructureModel());
        }
        theModel.setAdditionalSerialNumber(additionalSerialNumber);
    }

    @Override
    public AdditionalFormatContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        AdditionalFormatContainerModel model = new AdditionalFormatContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.ADDITIONAL_FORMAT_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return NotNamed.additional.getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return NotNamed.additional.getModuleName();
    }

    @Override
    public String getModuleId() {
        return "";
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return additionalSerialNumber;
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThis() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        opratingContainerList.addAll(super.getAllOpratingContainerListInThis());
        if (formatState == true) {
            opratingContainerList.addAll(additionalSetTypeFolder.getAllOpratingContainer());
        }
        return opratingContainerList;
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        super.collapseThis();
        if (formatState == true) {
            additionalSetTypeFolder.collapseThis();
            formatTypePane.packUpPanel();
        }
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        super.delThis();
        if (formatState == true) {
            additionalSetTypeFolder.delThis();
        }
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        // TODO Auto-generated method stub
        super.delModuleOpratingContainerFromComponent(moduleId);
        if (formatState == true) {
            additionalSetTypeFolder.delModuleOpratingContainerFromComponent(moduleId);
        }
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
    public FormatTypePane generateSettingsButton() {
        FormatTypePane formatTypeBt = new FormatTypePane() {
            @Override
            protected void doSomeThingWhenPackUpPanel() {
                additionalSetTypeFolder.collapseThis();
            }
        };
        return formatTypeBt;
    }

    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        super.autoCollapse(currentOpratingContainer);
        if (formatState == true) {
            if (MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(currentOpratingContainer.getPathFind().getMarkType())) {
                AbstractCommandOpratingContainer firstCommandOpratingContainer = currentOpratingContainer.getFirstCommandOpratingContainer();
                if (firstCommandOpratingContainer != null && firstCommandOpratingContainer instanceof AdditionalSetContainer) {
                    AdditionalSetContainer container = (AdditionalSetContainer) firstCommandOpratingContainer;
                    if (additionalSerialNumber == container.getAdditionalSerialNumber()) {
                        additionalSetTypeFolder.autoCollapse(currentOpratingContainer);
                    }
                }
            } else {
                formatTypePane.packUpPanel();
            }
        }
    }

}
