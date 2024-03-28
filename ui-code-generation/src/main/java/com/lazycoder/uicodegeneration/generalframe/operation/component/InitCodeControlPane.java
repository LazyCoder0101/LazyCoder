package com.lazycoder.uicodegeneration.generalframe.operation.component;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.container.InitOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.component.InitCodeControlPaneUI;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.InitOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.InitCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.InitOpratingContainerModel;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;


public class InitCodeControlPane extends AbstractCodeControlPane {

    /**
     *
     */
    private static final long serialVersionUID = -261484743679521764L;

    public InitCodeControlPane(AbstractFormatControlPane formatControlPane) {
        super(formatControlPane, 20);
        // TODO Auto-generated constructor stub
        PathFind pathFind = new PathFind(MarkElementName.INIT_MARK, PathFind.COMMAND_TYPE);
        setPathFind(pathFind);
        setName("initCode");
    }

    @Override
    protected void setFolderPaneUI() {
        setUI(new InitCodeControlPaneUI());
    }

    /**
     * 根据模型还原内容
     *
     * @param codeControlPaneModel
     */
    public void restoreContent(InitCodeControlPaneModel codeControlPaneModel, ArrayList<ModuleRelatedParam> useModuleRelatedParamList) {
        InitOpratingContainer initOpratingContainer;
        InitOperatingContainerParam initOperatingContainerParam;

        ArrayList<AbstractOperatingContainerModel> containerList = codeControlPaneModel.getContainerList();
        InitOpratingContainerModel initOpratingContainerModel;
        for (AbstractOperatingContainerModel containerModel : containerList) {
            if (containerModel instanceof InitOpratingContainerModel) {
                initOpratingContainerModel = (InitOpratingContainerModel) containerModel;

                for (ModuleRelatedParam moduleRelatedParamTemp : useModuleRelatedParamList) {
                    if (initOpratingContainerModel.getModuleId().equals(moduleRelatedParamTemp.getModule().getModuleId())) {
                        initOperatingContainerParam = new InitOperatingContainerParam();
                        initOperatingContainerParam.setModule(moduleRelatedParamTemp.getModule());
                        initOperatingContainerParam.setModuleInfo(moduleRelatedParamTemp.getModuleInfo());
                        initOperatingContainerParam.setCodeControlPane(this);
                        initOperatingContainerParam.setFormatControlPane(getFormatControlPane());

                        initOpratingContainer = new InitOpratingContainer(initOpratingContainerModel,
                                initOperatingContainerParam, true);
                        addContainer(initOpratingContainer);

                        break;
                    }
                }
            }
        }
    }


    public void addInit(ModuleRelatedParam moduleRelatedParam) {
        //只要模块有内容，初始化、设置、模块控制任一写有内容，都添加初始化功能
        if (BaseModel.TRUE_ == moduleRelatedParam.getModule().getEnabledState()) {
            InitOperatingContainerParam initOperatingContainerParam = new InitOperatingContainerParam();
            initOperatingContainerParam.setCodeControlPane(this);
            initOperatingContainerParam.setModule(moduleRelatedParam.getModule());
            initOperatingContainerParam.setModuleInfo(moduleRelatedParam.getModuleInfo());
            initOperatingContainerParam.setFormatControlPane(getFormatControlPane());

            if (moduleRelatedParam.getModuleInfo().getNumOfInit() > 0) {//有写初始化
                InitMetaModel metaModel = SysService.INIT_SERVICE.getDeafaultModuleSetMetaModel(
                        moduleRelatedParam.getModuleInfo().getModuleId());
                if (metaModel != null) {

                    boolean flag = InitOpratingContainer.checkWhetherItMatches(initOperatingContainerParam, metaModel);
                    if (flag) {
                        InitOpratingContainer initOpratingContainer = new InitOpratingContainer(metaModel, initOperatingContainerParam, true);
                        addContainer(initOpratingContainer);

                        scrollToBottom();
                    } else {
                        String text = "生成程序可能有问题！\n无法添加\"" + moduleRelatedParam.getModule().getModuleName() + "\"模块的\"" + metaModel.getOperatingModel().getShowText() +
                                "\"(" + metaModel.getOperatingModel().getOrdinal() + ")初始化功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "无法添加\"" + moduleRelatedParam.getModule().getModuleName() + "\"模块的\"" + metaModel.getOperatingModel().getShowText() +
                                "\"(" + metaModel.getOperatingModel().getOrdinal() + ")初始化功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);

                        metaModel = null;
                        InitOpratingContainer initOpratingContainer = new InitOpratingContainer(metaModel, initOperatingContainerParam, true);
                        addContainer(initOpratingContainer);
                        scrollToBottom();
                    }
                }
            } else {//没有写初始化
                InitMetaModel metaModel = null;
                InitOpratingContainer initOpratingContainer = new InitOpratingContainer(metaModel, initOperatingContainerParam, true);
                addContainer(initOpratingContainer);

                scrollToBottom();
            }
        }
    }

    /**
     * 获取某个模块的初始化代码的id
     *
     * @param moduleId
     * @return 为空的话，就是没有这模块的初始化代码
     */
    public Integer getModuleInitCodeSerialNumber(String moduleId) {
        Integer codeSerialNumber = null;
        InitOpratingContainer initOpratingContainerTemp = null;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof InitOpratingContainer) {
                initOpratingContainerTemp = (InitOpratingContainer) getComponent(i);
                if (moduleId.equals(initOpratingContainerTemp.getModuleId())) {
                    codeSerialNumber = initOpratingContainerTemp.getCodeSerialNumber();
                    break;
                }
            }
        }
        return codeSerialNumber;
    }

    /**
     * 添加功能容器
     *
     * @param container
     */
    @Override
    public void addContainer(Folder container) {
        tabs.add(container);
        this.add(container);
        // 为该面板添加抽屉事件
        ((InitCodeControlPaneUI) ui).addTab(container);
        SysUtil.updateFrameUI(container);
    }

    @Override
    public InitCodeControlPaneModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        InitCodeControlPaneModel model = new InitCodeControlPaneModel();
        setParam(model);
        return model;
    }

    /**
     * 删除某个模块的初始化方法
     *
     * @param moduleId
     */
    public void delModuleOpratingContainer(String moduleId) {
        InitOpratingContainer initOpratingContainerTemp = null;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof InitOpratingContainer) {
                initOpratingContainerTemp = (InitOpratingContainer) getComponent(i);
                initOpratingContainerTemp.delModuleOpratingContainer(moduleId);
            }
        }
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        OpratingContainerInterface OpratingContainerTemp = null;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof OpratingContainerInterface) {
                OpratingContainerTemp = (OpratingContainerInterface) getComponent(i);
                opratingContainerList.addAll(OpratingContainerTemp.getAllOpratingContainerListInThis());
                opratingContainerList.add(OpratingContainerTemp);
            }
        }
        return opratingContainerList;
    }


    /**
     * 收起所有面板
     */
    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof OpratingContainerInterface) {
                ((OpratingContainerInterface) getComponent(i)).collapseThis();
            }
        }
    }

    /**
     * 收起隐藏面板
     *
     * @param currentOpratingContainer
     */
    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (currentOpratingContainer != null) {
            OpratingContainerInterface opratingContainerTemp;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof OpratingContainerInterface) {
                    opratingContainerTemp = (OpratingContainerInterface) getComponent(i);
                    opratingContainerTemp.autoCollapse(currentOpratingContainer);
                }
            }
        }
    }

}
