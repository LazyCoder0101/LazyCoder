package com.lazycoder.uicodegeneration.generalframe.operation;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.ImportCode;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomVariableHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleFormatVariableHolder;
import com.lazycoder.uicodegeneration.component.generalframe.FormatControlPaneLable;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AbstractFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.operation.component.BusinessLogicCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.InitCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AbstractFormatVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractFormatControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.GeneralFormatControlPaneInterface;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;
import lombok.Getter;

public abstract class AbstractFormatControlPane extends JSplitPane
        implements FormatControlPaneInterface, GeneralFormatControlPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1246572042219255006L;

    @Getter
    protected BusinessLogicCodeControlPane businessLogicCodePane;

    @Getter
    protected InitCodeControlPane initCodeControlPane;

    /**
     * 文件名
     */
    @Getter
    protected String fileName = "";

    protected FormatControlPaneLable flagLable;

    @Getter
    protected ArrayList<ModuleRelatedParam> useModuleRelatedParamList = new ArrayList<>();

    /**
     * 默认代码面板有没有添加业务逻辑标记
     */
    @Getter
    protected boolean haveBusinessLogicMarkFlag = true;

    /**
     * 分割比例
     */
    private double dividerLocation = 0.6;

    public AbstractFormatControlPane() {
        // this.dividerLocation = dividerLocation;
        setOrientation(JSplitPane.VERTICAL_SPLIT);// 设置分割线方向
        setOneTouchExpandable(true);
        this.addComponentListener(cAdapter);
        this.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, propertyChangeListener);
        // setBorder(null);

        businessLogicCodePane = new BusinessLogicCodeControlPane(this);
        CodeGenerationFrameHolder.currentAdditiveMethodCodePane = businessLogicCodePane;
        setTopComponent(businessLogicCodePane.getParentScrollPane());

        initCodeControlPane = new InitCodeControlPane(this);
        setBottomComponent(initCodeControlPane.getParentScrollPane());
    }

    protected AbstractFormatContainer restoreContent(AbstractFormatControlPaneModel formatControlPaneModel) {
        this.fileName = formatControlPaneModel.getFileName();
        this.useModuleRelatedParamList = formatControlPaneModel.getUseModuleRelatedParamList();

        AbstractFormatContainer formatContainer = businessLogicCodePane.restoreContent(
                formatControlPaneModel.getBusinessLogicCodeControlPaneModel(),
                formatControlPaneModel.getUseModuleRelatedParamList());// 还原业务逻辑面板
        initCodeControlPane.restoreContent(formatControlPaneModel.getInitCodeControlPaneModel(), formatControlPaneModel.getUseModuleRelatedParamList());
        return formatContainer;
    }

    /**
     * 删除这个模块在当前控制面板对应的功能和相关代码，并查看其它控制面板有没有添加过这个模块，没有的话，把对应的文件夹之类的一并删除
     *
     * @param moduleInfo
     */
    private void delModuleFromAdditional(ModuleInfo moduleInfo) {
        boolean flag = CodeGenerationFrameHolder.codeControlTabbedPane
                .checkIfTheModuleHasBeenAddedExceptFor(moduleInfo.getModuleId(), this);// 检查其他面板有没有添加过这个模块
        if (flag == true) {// 如果有
            // 删除该面板所有的这个模块的方法
            delModuleOpratingContainer(moduleInfo.getModuleId());
        } else {// 其他地方没有添加过这个模块
            delModuleOpratingContainer(moduleInfo.getModuleId());
            if (moduleInfo != null) {
                SourceGenerateFileMethod.delModuleDataInProject(CodeGenerationFrameHolder.projectParentPath,
                        CodeGenerationFrameHolder.projectName, moduleInfo);// 删除对应模块的数据文件
                SourceGenerateFileMethod.delModuleAttachedFileInSourceProject(
                        CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleInfo);// 删除对应项目的附带文件
                CodeGenerationModuleCustomVariableHolder.delModule(moduleInfo.getModuleId());
                CodeGenerationModuleFormatVariableHolder.delModule(moduleInfo.getModuleId());
                CodeGenerationModuleCustomFunctionNameHolder.delModule(moduleInfo.getModuleId());
                CodeGenerationModuleFormatFunctionNameHolder.delModule(moduleInfo.getModuleId());
                if (CodeGenerationFrameHolder.codeShowPanel != null) {
                    CodeGenerationFrameHolder.codeShowPanel.delTheModuleCodeFiles(moduleInfo.getModuleId());
                }
            }
        }
    }

    private PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            whenMovingTheDividing();
        }
    };

    private ComponentAdapter cAdapter = new ComponentAdapter() {

        @Override
        public void componentResized(ComponentEvent e) {
            setDividerLocation(dividerLocation);
        }
    };

    /**
     * 从useModuleList中删除moduleName模块
     *
     * @param moduleId
     */
    private void delFromUseModuleList(String moduleId) {
        ModuleRelatedParam temp;
        for (int i = 0; i < useModuleRelatedParamList.size(); i++) {// 在 list 数组删掉
            temp = useModuleRelatedParamList.get(i);
            if (temp.getModuleInfo().getModuleId().equals(moduleId)) {
                useModuleRelatedParamList.remove(i);
                break;
            }
        }
    }

    public void addInit(ArrayList<ModuleRelatedParam> moduleRelatedParamList) {
        for (ModuleRelatedParam temp : moduleRelatedParamList) {

            if (false == CodeGenerationFrameHolder.codeControlTabbedPane.checkTheModuleHaveOrNot(temp.getModuleInfo())) {//检查有没有添加过这个模块
                CodeGenerationFrameHolder.codeShowPanel.addModule(temp.getModuleInfo().getModuleId());//添加该模块的代码文件

                SourceGenerateFileMethod.copyModuleDataInProject(CodeGenerationFrameHolder.projectParentPath,
                        CodeGenerationFrameHolder.projectName, temp.getModuleInfo());//把模块数据复制过去

                SourceGenerateFileMethod.copyModuleAttachedFileInSourceProject(
                        CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, temp.getModuleInfo());//复制附带文件

                CodeGenerationModuleFormatVariableHolder.addModule(temp.getModuleInfo());//添加模块格式变量
                CodeGenerationModuleCustomVariableHolder.addModule(temp.getModuleInfo());//添加模块自定义变量

                CodeGenerationModuleFormatFunctionNameHolder.addModule(temp.getModuleInfo());//添加模块格式方法名
                CodeGenerationModuleCustomFunctionNameHolder.addModule(temp.getModuleInfo());//添加模块自定义方法名
            }
            initCodeControlPane.addInit(temp);
            this.useModuleRelatedParamList.add(temp);
        }
        CodeGenerationFrameHolder.featureSelectedPane.updateModuleList(this);
    }

    /**
     * 获取某个模块的下一个模块的信息
     *
     * @param moduleId
     * @return 如果返回null，那传参的模块为当前最后一个
     */
    public ModuleInfo getNextModuleInfo(String moduleId) {
        ModuleInfo moduleInfo = null;
        ModuleRelatedParam temp;
        for (int i = 0; i < useModuleRelatedParamList.size(); i++) {
            temp = useModuleRelatedParamList.get(i);
            if (moduleId.equals(temp.getModuleInfo().getModuleId())) {
                if (i == useModuleRelatedParamList.size() - 1) {
                    moduleInfo = null;
                    break;
                } else {
                    moduleInfo = useModuleRelatedParamList.get(i + 1).getModuleInfo();
                    break;
                }
            }
        }
        return moduleInfo;
    }

    /**
     * 获取某个模块的初始化代码的id
     *
     * @param moduleId
     * @return 为空的话，就是没有这模块的初始化代码id
     */
    public Integer getModuleInitCodeSerialNumber(String moduleId) {
        Integer codeSerialNumber = initCodeControlPane.getModuleInitCodeSerialNumber(moduleId);
        return codeSerialNumber;
    }

    public int getAdditionalSerialNumber() {
        return 0;
    }

    public abstract FormatControlPaneLable getFormatControlPaneLable();

    @Override
    public abstract CodeShowPane getDefaultPane();

    /**
     * 保存项目文件
     */
    public abstract void saveProjectFile();

    @Override
    public void setParam(AbstractFormatControlPaneModel model) {
        // TODO Auto-generated method stub
        AbstractFormatControlPaneModel theModel = (AbstractFormatControlPaneModel) model;
        theModel.setFileName(this.fileName);
        theModel.setUseModuleRelatedParamList(useModuleRelatedParamList);
        theModel.setBusinessLogicCodeControlPaneModel(businessLogicCodePane.getFormatStructureModel());
        theModel.setInitCodeControlPaneModel(initCodeControlPane.getFormatStructureModel());
        theModel.setHaveBusinessLogicMarkFlag(haveBusinessLogicMarkFlag);
    }


    /**
     * 获取其对应的存储格式变量类
     *
     * @return
     */
    public abstract AbstractFormatVariableHolder getFormatVariableHolder();

    /**
     * 获取其对应的方法名列表
     *
     * @return
     */
    public abstract AbstractFormatFunctionNameHolder getFormatFunctionNameHolder();

    /**
     * 获取对应自定义类
     *
     * @return
     */
    public abstract CustomVariableHolder getThisCustomVariableHolder();

    /**
     * 获取对应方法名
     *
     * @return
     */
    public abstract CustomFunctionNameHolder getThisCustomFunctionNameHolder();

    /**
     * 检查有没有添加过该模块
     *
     * @param moduleId
     */
    public boolean checkIfTheModuleHasBeenAddedExceptFor(String moduleId) {
        boolean flag = false;
        for (ModuleRelatedParam moduleRelatedParam : useModuleRelatedParamList) {
            if (moduleRelatedParam.getModuleInfo().getModuleId().equals(moduleId)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 删掉某个模块的所有代码
     *
     * @param moduleId
     */
    private void delModuleOpratingContainer(String moduleId) {
        businessLogicCodePane.delModuleOpratingContainer(moduleId);
        initCodeControlPane.delModuleOpratingContainer(moduleId);
    }

    /**
     * 获取所有的操作功能组件
     *
     * @return
     */
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        ArrayList<OpratingContainerInterface> opratingContainerInterfaceList = new ArrayList<OpratingContainerInterface>();
        opratingContainerInterfaceList.addAll(initCodeControlPane.getAllOpratingContainer());
        opratingContainerInterfaceList.addAll(businessLogicCodePane.getAllOpratingContainer());
        return opratingContainerInterfaceList;
    }

    public void collapseThis() {
        // TODO Auto-generated method stub
        this.removeComponentListener(cAdapter);
        this.removePropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, propertyChangeListener);

        businessLogicCodePane.collapseThis();
        initCodeControlPane.collapseThis();

        this.addComponentListener(cAdapter);
        this.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, propertyChangeListener);
    }

    /**
     * 自动收起隐藏面板
     *
     * @param currentOpratingContainer
     */
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        // TODO Auto-generated method stub
        if (currentOpratingContainer != null) {
            this.removeComponentListener(cAdapter);
            this.removePropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, propertyChangeListener);

            businessLogicCodePane.autoCollapse(currentOpratingContainer);
            initCodeControlPane.autoCollapse(currentOpratingContainer);

            this.addComponentListener(cAdapter);
            this.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, propertyChangeListener);

        }
    }

    /**
     * 获取自身所使用的模块的所有代码面板
     */
    @Override
    public ArrayList<CodeShowPane> getModuleCodePaneList() {
        // TODO Auto-generated method stub
        ArrayList<CodeShowPane> list = new ArrayList<CodeShowPane>(), tempList;
        for (ModuleRelatedParam moduleRelatedParam : useModuleRelatedParamList) {
            tempList = CodeGenerationFrameHolder.codeShowPanel.getModulePaneList(moduleRelatedParam.getModuleInfo().getModuleId());
            list.addAll(tempList);
        }
        return list;
    }

    /**
     * 删除模块
     *
     * @param moduleList
     */
    public void delModuleList(ArrayList<ModuleRelatedParam> moduleList) {
        if (moduleList != null) {
            for (ModuleRelatedParam moduleRelatedParam : moduleList) {
                delModuleFromAdditional(moduleRelatedParam.getModuleInfo());
                delImportCode(moduleRelatedParam.getModuleInfo().getModuleId());
                delFromUseModuleList(moduleRelatedParam.getModule().getModuleId());
            }
        }
        CodeGenerationFrameHolder.featureSelectedPane.updateModuleList(this);
    }

    /**
     * 删除moduleName模块所有的引入代码
     *
     * @param moduleId
     */
    private void delImportCode(String moduleId) {
        ModuleInfo moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(moduleId);
        if (moduleInfo != null) {
            if (moduleInfo.getNumOfImport() > 0) {// 此前添加过内容，显示之前编辑过的内容
                List<ImportCode> list = SysService.IMPORT_CODE_SERVICE.getImportCodeList(moduleId);
                ImportMarkElement markElement;
                for (ImportCode importCode : list) {
                    markElement = new ImportMarkElement();
                    importCode.setModuleId(moduleId);
                    markElement.setOrdinal(importCode.getOrdinal());
                    delImportCode(markElement, importCode);
                }
            }
        }
    }

    /**
     * 删除自身对应的代码面板某个模块的所有引入代码（删除模块时调用）
     *
     * @param thanImportMarkElement
     * @param importCode
     */
    private void delImportCode(ImportMarkElement thanImportMarkElement, ImportCode importCode) {
        CodeShowPane codeShowPane = getDefaultPane();
        if (codeShowPane != null) {// 默认面板，内部的引入代码必定都是对应函数格式的，删除模块时，全部删除即可
            codeShowPane.delImportCode(thanImportMarkElement, importCode);

            ArrayList<CodeShowPane> codePaneList = getSubCodePaneList();
            if (codePaneList != null) {// 必填模板格式的子代码面板，内部的引入代码必定都是必填模板格式的，删除模块时，全部删除即可
                if (this instanceof MainFormatControlPane) {
                    for (CodeShowPane temp : codePaneList) {
                        temp.delImportCode(thanImportMarkElement, importCode);
                    }
                } else if (this instanceof AdditionalFormatControlPane) {
                    AdditionalFormatControlPane formatControlPane = (AdditionalFormatControlPane) this;
                    int additionalSerialNumber = formatControlPane.getAdditionalSerialNumber();
                    boolean flag = CodeGenerationFrameHolder.codeControlTabbedPane
                            .checkIfTheModuleHasBeenAddedExceptFor(importCode.getModuleId(), formatControlPane, additionalSerialNumber);// 检查除了当前这个格式面板以外，还有没有同样为additionalSerialNumber的格式，也添加了这个模块
                    if (flag == false) {
                        for (CodeShowPane temp : codePaneList) {
                            temp.delImportCode(thanImportMarkElement, importCode);
                        }
                    }
                }
            }

            codePaneList = getModuleCodePaneList();
            if (codePaneList != null) {
                boolean flag = true;
                CodeFormatFlagParam codeFormatFlagParam;
                String moduleId = "";
                for (CodeShowPane temp : codePaneList) {
                    codeFormatFlagParam = temp.getCodeFormatFlagParam();
                    if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
                        if (moduleId.equals(codeFormatFlagParam.getModuleId()) == false) {
                            moduleId = codeFormatFlagParam.getModuleId();
                            flag = CodeGenerationFrameHolder.codeControlTabbedPane
                                    .checkIfTheModuleHasBeenAddedExceptFor(moduleId, this);// 查看别的格式有没有添加这个模块
                        }

                        if (flag == false) {// 如果别的格式都没添加这个模块,那可以确定，该模块文件里面要删的引入代码都是这个格式面板的，直接删掉对应的引入代码
                            temp.delImportCode(thanImportMarkElement, importCode);
//					} else {// 可选模板格式有添加这个模块的话，不需要删除该代码
                        }
                    }
                }
            }
        }
    }

    /**
     * 当移动面板分割线时的操作
     */
    protected void whenMovingTheDividing() {
        businessLogicCodePane.collapseThis();
        initCodeControlPane.collapseThis();
    }

    public ModuleRelatedParam getModuleRelatedParam(String moduleId) {
        ModuleRelatedParam moduleRelatedParam = null;
        for (ModuleRelatedParam moduleRelatedParamTemp : useModuleRelatedParamList) {
            if (moduleRelatedParamTemp.getModule().getModuleId().equals(moduleId)) {
                moduleRelatedParam = moduleRelatedParamTemp;
                break;
            }
        }
        return moduleRelatedParam;
    }

    /**
     * 设置变量组件里面不需要用户选择的值（仅在第一次创建代码生成界面的时候使用）
     */
    public void setNoUserSelectionIsRequiredValue() {
        ArrayList<OpratingContainerInterface> thisAllOpratingContainer = this.getAllOpratingContainer();
        for (OpratingContainerInterface opratingContainer : thisAllOpratingContainer) {
            opratingContainer.setNoUserSelectionIsRequiredValue();
        }
    }

}
