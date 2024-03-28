package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.typeset.format.main.MainSetTypeFolder;
import com.lazycoder.uicodegeneration.component.operation.container.component.CodeHiddenControlButton;
import com.lazycoder.uicodegeneration.component.operation.container.component.FormatOpratinglHiddenButton;
import com.lazycoder.uicodegeneration.component.operation.container.component.MainSetCodeHiddenControlButton;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FormatOpratingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.MainSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.MainFormatContainerModel;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainFormatContainer extends AbstractFormatContainer {

    /**
     * 宽度比例
     */
//    protected static final double PROPOTION = 0.29;
    /**
     *
     */
    private static final long serialVersionUID = -8299364698878473271L;
    private MainSetTypeFolder mainSetTypeFolder = null;
    private MainInfo mainInfo;

    /**
     * 新建
     *
     * @param formatOpratingContainerParam
     * @param fileName
     * @param mainInfo
     */
    public MainFormatContainer(FormatOpratingContainerParam formatOpratingContainerParam, String fileName,
                               MainInfo mainInfo) {
        // TODO Auto-generated constructor stub
        super();
        this.mainInfo = mainInfo;
        if (mainInfo.getFormatState() == MainInfo.TRUE_) {
            init(true, AbstractCommandOpratingContainer.PROPOTION);
        }
        if (this.mainInfo.getNumOfSetCodeType() > 0) {
            setInit(true, AbstractCommandOpratingContainer.PROPOTION);
        }
        currentCodeFileName = fileName;
        this.formatOpratingContainerParam = formatOpratingContainerParam;
        generateOperationalContent(formatOpratingContainerParam);

        if (this.mainInfo.getNumOfSetCodeType() > 0) {
            MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam = new MainSetTypeOperatingContainerParam();
            mainSetTypeOperatingContainerParam.setFormatContainer(MainFormatContainer.this);
            mainSetTypeOperatingContainerParam
                    .setFormatControlPane(formatOpratingContainerParam.getFormatControlPane());
            mainSetTypeOperatingContainerParam.setMainInfo(mainInfo);

            mainSetTypeFolder = new MainSetTypeFolder(mainSetTypeOperatingContainerParam);
            setScrollPane.setViewportView(mainSetTypeFolder);
        }
        setAppropriateSize();
    }


    /**
     * 还原
     *
     * @param formatOpratingContainerParam
     * @param model
     */
    public MainFormatContainer(FormatOpratingContainerParam formatOpratingContainerParam,
                               MainFormatContainerModel model) {
        super();
        this.formatOpratingContainerParam = formatOpratingContainerParam;
        this.mainInfo = model.getMainInfo();
        if (mainInfo.getFormatState() == MainInfo.TRUE_) {
            init(true, AbstractCommandOpratingContainer.PROPOTION);
        }
        if (mainInfo.getNumOfSetCodeType() > 0) {
            setInit(true, AbstractCommandOpratingContainer.PROPOTION);
        }

        restoreContent(model, formatOpratingContainerParam);
        if (this.mainInfo.getNumOfSetCodeType() > 0) {
            MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam = new MainSetTypeOperatingContainerParam();
            mainSetTypeOperatingContainerParam.setFormatContainer(MainFormatContainer.this);
            mainSetTypeOperatingContainerParam
                    .setFormatControlPane(formatOpratingContainerParam.getFormatControlPane());
            mainSetTypeOperatingContainerParam.setMainInfo(this.mainInfo);

            mainSetTypeFolder = new MainSetTypeFolder(mainSetTypeOperatingContainerParam,
                    model.getMainSetTypeFolderModel());
            setScrollPane.setViewportView(mainSetTypeFolder.getParentScrollPane());
        }
        setAppropriateSize();
    }

    @Override
    protected FormatOpratinglHiddenButton formatOpratinglHiddenButtonInit() {
        return new FormatOpratinglHiddenButton(true, "格式") {
            @Override
            public void doSomethingWhenMousePressed(boolean expanded) {
                super.doSomethingWhenMousePressed(expanded);
                if (expanded == false) {//收起面板时，收起面板的所有组件
                    formatOperatingPane.collapseThis();
                    if (setState == true) {
                        if (mainSetTypeFolder != null) {
                            mainSetTypeFolder.collapseThis();
                        }
                    }
                }
            }
        };
    }

    @Override
    protected CodeHiddenControlButton setCodeHiddenControlButtonInit() {
        return new MainSetCodeHiddenControlButton(true) {
            @Override
            public void doSomethingWhenMousePressed(boolean expanded) {
                super.doSomethingWhenMousePressed(expanded);
                formatControlPane.collapseThis();
                if (this.isExpanded() == false && mainSetTypeFolder != null) {//收起面板时，收起隐藏面板的所有组件
                    mainSetTypeFolder.collapseThis();
                }
            }

            @Override
            public void doClick() {
                super.doClick();
                formatControlPane.collapseThis();
                if (this.isExpanded() == true && mainSetTypeFolder != null) {
                    mainSetTypeFolder.collapseThis();
                }
            }
        };
    }

    @Override
    public void delThis() {
        super.delThis();
        if (this.setState == true) {
            if (mainSetTypeFolder != null) {
                mainSetTypeFolder.delThis();
            }
        }
    }

    @Override
    public ArrayList<CodeShowPane> getCodePaneList() {
        // TODO Auto-generated method stub
        ArrayList<CodeShowPane> list = new ArrayList<>();
        CodeShowPane deafaultPane = CodeGenerationFrameHolder.codeShowPanel.getMainDefaultCodeShowPane();
        list.add(deafaultPane);
        ArrayList<CodeShowPane> subList = CodeGenerationFrameHolder.codeShowPanel.getMainSubCodeShowPaneList();
        list.addAll(subList);
        return list;
    }

    @Override
    public ArrayList<CodeFormatFlagParam> getCodePaneRelatedInfoList() {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>();
        CodeShowPane deafaultPane = CodeGenerationFrameHolder.codeShowPanel.getMainDefaultCodeShowPane();
        list.add(deafaultPane.getCodeFormatFlagParam());
        ArrayList<CodeShowPane> subList = CodeGenerationFrameHolder.codeShowPanel.getMainSubCodeShowPaneList();
        for (CodeShowPane codeShowPane : subList) {
            list.add(codeShowPane.getCodeFormatFlagParam());
        }
        return list;
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getMainPictureFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName);
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getMainNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName);
        return file;
    }


    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        MainFormatContainerModel theModel = (MainFormatContainerModel) model;
        theModel.setMainInfo(mainInfo);
        if (mainInfo.getNumOfSetCodeType() > 0) {
            theModel.setMainSetTypeFolderModel(mainSetTypeFolder.getFormatStructureModel());
        }
        super.setParam(theModel);
    }

    @Override
    public MainFormatContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        MainFormatContainerModel model = new MainFormatContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.MAIN_FORMAT_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return NotNamed.main.getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return NotNamed.main.getModuleName();
    }

    @Override
    public String getModuleId() {
        return "";
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThis() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        opratingContainerList.addAll(super.getAllOpratingContainerListInThis());
        if (this.setState == true) {
            opratingContainerList.addAll(mainSetTypeFolder.getAllOpratingContainer());
        }
        return opratingContainerList;
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        super.collapseThis();
        if (this.setState == true) {
            mainSetTypeFolder.collapseThis();
        }

    }

    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        super.autoCollapse(currentOpratingContainer);
        if (this.setState == true && currentOpratingContainer != null) {
            if (MarkElementName.MAIN_SET_TYPE_MARK.equals(currentOpratingContainer.getPathFind().getMarkType())) {
                mainSetTypeFolder.autoCollapse(currentOpratingContainer);
            }
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
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        // TODO Auto-generated method stub
        super.delModuleOpratingContainerFromComponent(moduleId);
        if (this.setState == true) {
            mainSetTypeFolder.delModuleOpratingContainerFromComponent(moduleId);
        }
    }


    @Override
    public Dimension getRequiredDimension() {
        int w = getDrawer().getContentWidth(),
                h = 0;

        h = h + HIDDEN_HEIGHT;
        h = h + (int) (getDrawer().getContentHeight() * getDrawer().getRatio());

        if (this.setState) {//加载模块设置相关组件
            h = h + HIDDEN_HEIGHT;
            if (getSetDrawer() != null) {
                h = h + (int) (getSetDrawer().getContentHeight() * getSetDrawer().getRatio());
            }
        }
        return new Dimension(w, h);
    }

    @Override
    public int getRequiredHeight() {
        int h = 0;
        h = h + HIDDEN_HEIGHT;
        h = h + (int) (getDrawer().getContentHeight() * getDrawer().getRatio());

        if (this.setState) {
            h = h + HIDDEN_HEIGHT;
            if (getSetDrawer() != null) {
                h = h + (int) (getSetDrawer().getContentHeight() * getSetDrawer().getRatio());
            }
        }
        return h;
    }


}
