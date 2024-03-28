package com.lazycoder.uicodegeneration.generalframe.operation;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.FormatControlPaneLable;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.SourceGenerateModelGet;
import com.lazycoder.uicodegeneration.proj.stostr.operation.AdditionalFormatControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.MainFormatControlPaneModel;
import com.lazycoder.uiutils.mycomponent.CodeTabbedPane;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class CodeControlTabbedPane extends CodeTabbedPane implements MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = -203841482298859944L;

    @Getter
    private MainFormatControlPane mainCodeControlPane;

    /**
     * Create the panel.
     */
    public CodeControlTabbedPane() {
        super();
        FlatTabbedPaneUI theUI = new FlatTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                selectedBackground = Color.white;
                tabSeparatorsFullHeight = true;
                showTabSeparators = true;
            }
        };
        setUI(theUI);
        setUI(theUI);
        addChangeListener(changeListener);
        addMouseListener(this);

    }

    /**
     * 获取所有其他面板
     *
     * @return
     */
    public static ArrayList<AdditionalFormatControlPane> getAdditionalFormatControlPaneList(int additionalSerialNumber) {
        ArrayList<AdditionalFormatControlPane> allFormatControlPaneList = new ArrayList<AdditionalFormatControlPane>();
        AdditionalFormatControlPane formatControlPaneTemp;
        for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
            if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AdditionalFormatControlPane) {

                formatControlPaneTemp = (AdditionalFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                        .getComponent(i);
                if (formatControlPaneTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                    allFormatControlPaneList.add(formatControlPaneTemp);
                }
            }
        }
        return allFormatControlPaneList;
    }

    public static ArrayList<AbstractFormatControlPane> getAllFormatControlPane() {
        ArrayList<AbstractFormatControlPane> allFormatControlPaneList = new ArrayList<AbstractFormatControlPane>();
        AbstractFormatControlPane formatControlPaneTemp;
        for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
            if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AbstractFormatControlPane) {

                formatControlPaneTemp = (AbstractFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                        .getComponent(i);
                allFormatControlPaneList.add(formatControlPaneTemp);
            }
        }
        return allFormatControlPaneList;
    }

    /**
     * 添加控制面板
     *
     * @param list
     */
    public void addMainRelatedContent(ArrayList<ModuleRelatedParam> list, String fileName) {
        mainCodeControlPane = new MainFormatControlPane(fileName, list);
        if (mainCodeControlPane.getDefaultPane().checkHaveBusinessLogicMark()) {//有业务逻辑标记
            CodeGenerationFrameHolder.currentAdditiveMethodCodePane = mainCodeControlPane.businessLogicCodePane;
        }

        this.superAddTab(fileName, mainCodeControlPane, "必填模板");
        CodeGenerationFrameHolder.currentFormatControlPane = mainCodeControlPane;

//        //把不需要用户设置的值都自动选上
//        ArrayList<OpratingContainerInterface> allOpratingContainer = mainCodeControlPane.getAllOpratingContainer();
//        for (OpratingContainerInterface opratingContainer : allOpratingContainer) {
//            opratingContainer.setNoUserSelectionIsRequiredValue();
//        }
    }

    /**
     * 添加可选模板的相关内容
     *
     * @param fileName
     * @param additionalSerialNumber
     * @param canBeDelOrNot
     */
    public AdditionalFormatControlPane addAdditionalRelatedContent(String fileName, int additionalSerialNumber, boolean canBeDelOrNot) {
        AdditionalFormatControlPane formatControlPane = new AdditionalFormatControlPane(additionalSerialNumber, fileName, canBeDelOrNot);
        CodeGenerationFrameHolder.currentAdditiveMethodCodePane = formatControlPane.businessLogicCodePane;
        if (canBeDelOrNot == true) {
            this.addTab(fileName, formatControlPane, "可选模板");
        } else {
            this.superAddTab(fileName, formatControlPane, "可选模板");
        }
        CodeGenerationFrameHolder.currentFormatControlPane = formatControlPane;

        ArrayList<ModuleRelatedParam> list = new ArrayList<>();
//        UsingObject usingObject = new UsingObject(additionalSerialNumber);
//        List<Module> moduleList = SysService.MODULE_SERVICE.getModuleList(null, usingObject, ModuleUseSetting.MUST_USE);//获取可选模板每次生成都要添加的模块
//        if (moduleList != null && moduleList.size() > 0) {
//            ArrayList<Module> alist = new ArrayList<>();
//            alist.addAll(moduleList);
//            List<AssociatedModule> allNeedModuleList = SysService.MODULE_SERVICE.getAllNeedUsedModuleList(alist);//除了设置一定要用的模块，还有使用他们所需要的模块
//            for (AssociatedModule associatedModule : allNeedModuleList) {
//                moduleList.add(associatedModule.getModule());
//            }
//
//            ArrayList<Module> sortList = SysService.MODULE_SERVICE.sortModuleListByNeedModuleParam(alist);//重新进行排序，然后添加进去
//            list.addAll(SysService.MODULE_SERVICE.getCurrentModuleInfoListBy(sortList));
//        }
        formatControlPane.addInit(list);

//        //把不需要用户设置的值都自动选上
//        ArrayList<OpratingContainerInterface> allOpratingContainer = formatControlPane.getAllOpratingContainer();
//        for (OpratingContainerInterface opratingContainer : allOpratingContainer) {
//            opratingContainer.setNoUserSelectionIsRequiredValue();
//        }

        setSelectedIndex(getComponentCount() - 1);
        return formatControlPane;
    }


    private ChangeListener changeListener = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
            // TODO Auto-generated method stub

            //先收起当前控制面板的所有组件
            if (CodeGenerationFrameHolder.currentFormatControlPane != null) {
                CodeGenerationFrameHolder.currentFormatControlPane.collapseThis();
            }

            //把当前选中的控制面板的业务逻辑面板设为当前添加方法的面板
            AbstractFormatControlPane currentFormatPane = (AbstractFormatControlPane) getSelectedComponent();
            CodeGenerationFrameHolder.currentFormatControlPane = currentFormatPane;
            CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                    CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());

            //在方法选择面板显示当前的控制面板的各个模块
            CodeGenerationFrameHolder.featureSelectedPane.updateModuleList(currentFormatPane);

            //把代码显示面板的默认选项选为当前的控制面板的默认代码面板
            if (currentFormatPane != null) {
                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(currentFormatPane.getDefaultPane());
            }
        }
    };

    /**
     * 查看有没有添加过一个名叫fileName的
     *
     * @param fileName
     * @return
     */
    public boolean checkHaveTheFileName(String fileName) {
        boolean flag = false;
        for (int i = 0; i < this.getComponentCount(); i++) {
            if (getTitleAt(i).equals(fileName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 还原格式控制面板
     *
     * @param list
     */
    public void restoreFormatControlPane(ArrayList<FormatControlPaneLable> list) {
        if (list != null) {
            String defaultFileName = "";
            int mainIndex = 0;
            for (FormatControlPaneLable formatControlPaneLableTemp : list) {
                defaultFileName = formatControlPaneLableTemp.getDefaultFileName();

                if (FormatControlPaneLable.MAIN_TYPE == formatControlPaneLableTemp.getType()) {
                    MainFormatControlPaneModel mainFormatControlPaneModel = SourceGenerateModelGet
                            .getMainFormatControlPane(CodeGenerationFrameHolder.projectParentPath,
                                    CodeGenerationFrameHolder.projectName, defaultFileName);
                    mainCodeControlPane = new MainFormatControlPane();
                    mainCodeControlPane.restoreContent(mainFormatControlPaneModel);
                    this.superAddTab(defaultFileName, mainCodeControlPane, "必填模板");
                    mainIndex = getComponentCount() - 1;

                } else if (FormatControlPaneLable.ADDITIONAL_TYPE == formatControlPaneLableTemp.getType()) {
                    boolean canBeDelOrNot = formatControlPaneLableTemp.isCanBeDelOrNot();

                    int additionalSerialNumber = formatControlPaneLableTemp.getAdditionalSerialNumber();
                    AdditionalFormatControlPaneModel formatControlPaneModel = SourceGenerateModelGet
                            .getFormatControlPaneModel(CodeGenerationFrameHolder.projectParentPath,
                                    CodeGenerationFrameHolder.projectName, defaultFileName, additionalSerialNumber);
                    AdditionalFormatControlPane formatControlPane = new AdditionalFormatControlPane(
                            formatControlPaneLableTemp.getDefaultFileName(), additionalSerialNumber, canBeDelOrNot);
                    formatControlPane.restoreContent(formatControlPaneModel);
                    if (canBeDelOrNot == true) {//可以删除该控制面板
                        this.addTab(defaultFileName, null, formatControlPane, "可选模板");
                    } else {//不能删除该控制面板
                        this.superAddTab(defaultFileName, formatControlPane, "可选模板");
                    }
                }
            }
            setSelectedIndex(mainIndex);
            CodeGenerationFrameHolder.currentAdditiveMethodCodePane = mainCodeControlPane.businessLogicCodePane;
            CodeGenerationFrameHolder.currentFormatControlPane = mainCodeControlPane;
            CodeGenerationFrameHolder.featureSelectedPane.updateModuleList(mainCodeControlPane);
        }
    }

    /**
     * 检查有没有这模块
     *
     * @return
     */
    public boolean checkTheModuleHaveOrNot(ModuleInfo moduleInfo) {
        boolean flag = false;
        AbstractFormatControlPane formatControlPaneTemp;
        for (int i = 0; i < getComponentCount(); i++) {
            formatControlPaneTemp = (AbstractFormatControlPane) getComponent(i);
            ArrayList<ModuleRelatedParam> useModuleListTemp = formatControlPaneTemp.getUseModuleRelatedParamList();
            for (ModuleRelatedParam moduleRelatedParamTemp : useModuleListTemp) {
                if (moduleRelatedParamTemp.getModuleInfo().getModuleId().equals(moduleInfo.getModuleId())) {
                    flag = true;
                    break;
                }
            }
            if (flag == true) {
                break;
            }
        }
        return flag;
    }

    /**
     * 获取格式控制面板的各个标记
     *
     * @return
     */
    public ArrayList<FormatControlPaneLable> getFormatControlPaneLable() {
        AbstractFormatControlPane formatControlPane;
        ArrayList<FormatControlPaneLable> list = new ArrayList<>();
        for (int i = 0; i < getComponentCount(); i++) {
            formatControlPane = (AbstractFormatControlPane) getComponent(i);
            list.add(formatControlPane.getFormatControlPaneLable());
        }
        return list;
    }

    /**
     * 获取已添加的各个可选模板的格式信息
     *
     * @return
     */
    public ArrayList<AdditionalOperating> getAllAddedAdditionalOperatingList() {
        AdditionalFormatControlPane formatControlPane;
        Component component;
        ArrayList<AdditionalOperating> list = new ArrayList<>();
        for (int i = 0; i < getComponentCount(); i++) {
            component = getComponent(i);
            if (component instanceof AdditionalFormatControlPane) {
                formatControlPane = (AdditionalFormatControlPane) component;
                list.add(formatControlPane.getOperating());
            }
        }
        return list;
    }

    /**
     * 保存项目文件
     */
    public void saveProjectFile() {
        AbstractFormatControlPane formatControlPane;
        for (int i = 0; i < getComponentCount(); i++) {
            formatControlPane = (AbstractFormatControlPane) getComponent(i);
            formatControlPane.saveProjectFile();
        }
    }

    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        ArrayList<OpratingContainerInterface> opratingContainerInterfaceList = new ArrayList<OpratingContainerInterface>();
        ArrayList<AbstractFormatControlPane> formatControlPaneList = getAllFormatControlPane();
        if (formatControlPaneList != null) {
            for (AbstractFormatControlPane formatControlPane : formatControlPaneList) {
                opratingContainerInterfaceList.addAll(formatControlPane.getAllOpratingContainer());
            }
        }
        return opratingContainerInterfaceList;
    }

    /**
     * 查看序号为additionalSerialNumber的函数目前添加了多少个
     *
     * @param additionalSerialNumber
     * @return
     */
    public int getAdditionalFormatControlPaneNum(int additionalSerialNumber) {
        int num = 0;
        AdditionalFormatControlPane formatControlPane;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof AdditionalFormatControlPane) {
                formatControlPane = (AdditionalFormatControlPane) getComponent(i);
                if (formatControlPane.getAdditionalSerialNumber() == additionalSerialNumber) {
                    num = num + 1;
                }
            }
        }
        return num;
    }


    /**
     * 检查除了该面板以外，有没有添加过该模块
     */
    public boolean checkIfTheModuleHasBeenAddedExceptFor(String moduleId,
                                                         AbstractFormatControlPane formatControlPane) {
        boolean flag = false;
        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            AbstractFormatControlPane tempPane;
            for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
                tempPane = (AbstractFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i);
                if (tempPane != formatControlPane) {
                    flag = tempPane.checkIfTheModuleHasBeenAddedExceptFor(moduleId);
                    if (flag == true) {
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 检查处了formatControlPane以外，所有其他代码为additionalSerialNumber的格式中，有没有添加过这个模块
     *
     * @param moduleId
     * @param formatControlPane
     * @param additionalSerialNumber
     * @return
     */
    public boolean checkIfTheModuleHasBeenAddedExceptFor(String moduleId,
                                                         AdditionalFormatControlPane formatControlPane, int additionalSerialNumber) {
        boolean flag = false;
        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            AdditionalFormatControlPane tempPane;
            for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
                if (getComponent(i) instanceof AdditionalFormatControlPane) {
                    tempPane = (AdditionalFormatControlPane) getComponent(i);
                    if (tempPane != formatControlPane) {
                        if (tempPane.getAdditionalSerialNumber() == additionalSerialNumber) {
                            flag = tempPane.checkIfTheModuleHasBeenAddedExceptFor(moduleId);
                            if (flag == true) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 方法名id为functionNameId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
     */
    public void functionNameSynchronousChange(int functionNameId) {
        ArrayList<OpratingContainerInterface> opratingContainerInterfaceList = getAllOpratingContainer();
        if (opratingContainerInterfaceList != null) {
            for (OpratingContainerInterface opratingContainer : opratingContainerInterfaceList) {
                opratingContainer.functionNameSynchronousChange(functionNameId);
            }
        }
    }

    /**
     * 方法名id为functionNameId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
     *
     * @param functionNameId
     */
    public void functionNameSynchronousDelete(int functionNameId) {
        ArrayList<OpratingContainerInterface> opratingContainerInterfaceList = getAllOpratingContainer();
        if (opratingContainerInterfaceList != null) {
            for (OpratingContainerInterface opratingContainer : opratingContainerInterfaceList) {
                opratingContainer.functionNameSynchronousDelete(functionNameId);
            }
        }
    }

    /**
     * 方法名id为variableId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
     *
     * @param variableId
     */
    public void variableSynchronousChange(int variableId) {
        ArrayList<OpratingContainerInterface> opratingContainerInterfaceList = getAllOpratingContainer();
        if (opratingContainerInterfaceList != null) {
            for (OpratingContainerInterface opratingContainer : opratingContainerInterfaceList) {
                opratingContainer.variableSynchronousChange(variableId);
            }
        }
    }

    /**
     * 方法名id为variableId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
     *
     * @param variableId
     */
    public void variableSynchronousDelete(int variableId) {
        ArrayList<OpratingContainerInterface> opratingContainerInterfaceList = getAllOpratingContainer();
        if (opratingContainerInterfaceList != null) {
            for (OpratingContainerInterface opratingContainer : opratingContainerInterfaceList) {
                opratingContainer.variableSynchronousDelete(variableId);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if (pressTheCloseButton(e) == true) {
            int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
            String fileName = this.getTitleAt(tabNumber);
            int n = LazyCoderOptionPane.showConfirmDialog(null, "真的要删除\"" + fileName + "\"这个面板，以及它所有的相关内容吗?\n删除后会立刻保存当前内容无法还原！", "确认一下",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.OK_OPTION) {
                Component component = getComponent(tabNumber);
                if (component instanceof AdditionalFormatControlPane) {
                    ((AdditionalFormatControlPane) component).delThisPane();
                }
                super.mouseClicked(e);
                CodeGenerationFrameHolder.generateCode();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
