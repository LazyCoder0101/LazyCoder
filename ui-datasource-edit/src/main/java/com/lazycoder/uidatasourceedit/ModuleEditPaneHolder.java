package com.lazycoder.uidatasourceedit;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.moduleedit.codepane.ModuleFilePutCodePane;
import com.lazycoder.uidatasourceedit.moduleedit.codepane.NeedUseCodeFileEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.codepane.UseModuleFileEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.UseModuleFunctionEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.function.ModuleFunctionEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.InitFolderPane;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.control.ModuleControlFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.functionname.ModuleFunctionNameFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode.ImportCodeFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.init.InitCodeFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.variable.ModuleVariableFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleset.ModuleSetCodeEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.UseSettingMenu;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.moduleselect.CurrentModuleSelectMenu;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.relatedmoduleinfomenu.RelatedModuleInfoMenu;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.usingrange.UsingRangeCombobox;
import com.lazycoder.uiutils.component.AfToaster;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;


public class ModuleEditPaneHolder {


    /**
     * 模块控制模型
     */
    public static FormatModel moduleFormatModel = new FormatModel();

    /**
     * 初始化面板里面的隐藏按钮（引入代码、初始化代码、模块控制、模块变量）
     */
    public static ArrayList<FoldButton> initPaneHiddenButtonList = new ArrayList<>();

    /**
     * 分类选择下拉框
     */
//    public static ModuleEditPaneClassCombobox classificationCombobox = null;

    /**
     * 模块选择下拉框
     */
//    public static ModuleCombobox moduleCombobox = null;

    /**
     * 模块选择菜单
     */
    public static CurrentModuleSelectMenu currentModuleSelectMenu = null;

    /**
     * 使用范围下拉框
     */
    public static UsingRangeCombobox usingRange = null;

    public static RelatedModuleInfoMenu relatedModuleInfoMenu = null;

    /**
     * 不使用模块下拉框
     */
//	public static NoUseModuleMenu noUseModuleMenu = null;

    /**
     * 生成程序都要使用该模块 按钮
     */
    public static UseSettingMenu useSettingMenu = null;

    /****************************************************************************************/
    /**
     * 模块编辑那里那个放置需要用到的代码文件的面板
     */
    public static NeedUseCodeFileEditPane needUseCodeFileEditPane = null;

    /**
     * 模块内容放置面板
     */
    public static UseModuleFunctionEditPane useModuleFunctionEditPane = null;

    /**
     * 存放 模块使用的代码文件 所用的编辑面板
     */
    public static UseModuleFileEditPane useModuleFileEditPane = null;

    /**
     * 存放 模块本身自己的代码文件 所使用的编辑面板
     */
    public static ModuleFilePutCodePane moduleFilePutCodePane = null;

    /****************************************************************************************/

    /**
     * 引入代码
     */
    public static ImportCodeFolder importCodeFolder = null;

    /**
     * 编辑初始化代码的组件
     */
    public static InitCodeFolder initCodeFolder = null;

    /**
     * 初始化面板
     */
    public static InitFolderPane initFolderPane = null;

    /**
     * 编辑模块控制的组件
     */
    public static ModuleControlFolder moduleControlFolder = null;

    /**
     * 编辑模块变量的组件
     */
    public static ModuleVariableFolder moduleVariableFolder = null;

    /**
     * 编辑设置代码的组件
     */
    public static ModuleSetCodeEditPane moduleSetCodeEditPane = null;

    /**
     * 编辑模块方法的组件
     */
    public static ModuleFunctionEditPane moduleFunctionEditPane = null;

    public static ModuleFunctionNameFolder moduleFunctionNameFolder = null;

    /**
     * 显示模块内容（使用前需先设置好 GeneralHolder类 的className 和moduleName）
     */
//    public static void showModuleData(String className, String moduleName) {
//        DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空
//
//        Module module = SysService.moduleService.getModule(className, moduleName);
//        ModuleInfo moduleInfo = SysService.moduleInfoService.getModuleInfo(className,
//                moduleName);
//
//        DataSourceEditHolder.currentModuleId = moduleInfo.getModuleId();
//        DataSourceEditHolder.className = className;
//        DataSourceEditHolder.moduleName = moduleName;
//
//        usingRange.clearModuleContents();
////				noUseModuleMenu.clearModuleContents();
//        relatedModuleInfoMenu.clearModuleContents();
//        useSettingMenu.clearModuleContents();
//
//        importCodeFolder.clearModuleContents();
//        moduleVariableFolder.clearModuleContents();
//        moduleFunctionNameFolder.clearModuleContents();
//
//        needUseCodeFileEditPane.clearModuleContents();
//        moduleFilePutCodePane.clearModuleContents();
//        moduleControlFolder.clearModuleContents();
//
//        initCodeFolder.clearModuleContents();
//        moduleSetCodeEditPane.clearModuleContents();
//        moduleFunctionEditPane.clearModuleContents();
//
//        moduleFormatModel = new FormatModel();// 重新初始化模块格式模型
//
//        usingRange.displayModuleContents(moduleInfo, module);
//        relatedModuleInfoMenu.displayModuleContents(moduleInfo, module);
//        useSettingMenu.displayModuleContents(moduleInfo, module);
//        importCodeFolder.displayModuleContents(moduleInfo, module);
//        moduleVariableFolder.displayModuleContents(moduleInfo, module);
//        moduleFunctionNameFolder.displayModuleContents(moduleInfo, module);
//        needUseCodeFileEditPane.displayModuleContents(moduleInfo, module);
//        moduleControlFolder.displayModuleContents(moduleInfo, module);
//        moduleFilePutCodePane.displayModuleContents(moduleInfo, module);
//        initCodeFolder.displayModuleContents(moduleInfo, module);
//        moduleSetCodeEditPane.displayModuleContents(moduleInfo, module);
//        moduleFunctionEditPane.displayModuleContents(moduleInfo, module);
//
//        SysUtil.updateFrameUI(DataSourceEditHolder.moduleEditPane);
//        if (module.getEnabledState() == Module.FALSE_) {
//            AfToaster.show(DataSourceEditHolder.dataSourceEditFrame, AfToaster.Level.WARN, 4500, "\t这个模块还没写过任何内容，没法使用，\t\n\t请尽快填好要写的内容并保存");
//        } else {
//            DataSourceEditHolder.showErrorList("这模块原来写的内容有点异常喔   (=ω=；)");//如果在还原过程中出现异常，列出异常情况
//        }
//    }
    public static void showModuleData(Module module, TheClassification classification) {
        DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空

        if (currentModuleSelectMenu != null) {
            currentModuleSelectMenu.selectModule(module, classification);
        }
        ModuleInfo moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(module.getModuleId());

        usingRange.clearModuleContents();
//				noUseModuleMenu.clearModuleContents();
        relatedModuleInfoMenu.clearModuleContents();
        useSettingMenu.clearModuleContents();

        importCodeFolder.clearModuleContents();
        moduleVariableFolder.clearModuleContents();
        moduleFunctionNameFolder.clearModuleContents();

        needUseCodeFileEditPane.clearModuleContents();
        moduleFilePutCodePane.clearModuleContents();
        moduleControlFolder.clearModuleContents();

        initCodeFolder.clearModuleContents();
        moduleSetCodeEditPane.clearModuleContents();
        moduleFunctionEditPane.clearModuleContents();

        moduleFormatModel = new FormatModel();// 重新初始化模块格式模型

        if (moduleInfo != null) {
            usingRange.displayModuleContents(moduleInfo, module);
            relatedModuleInfoMenu.displayModuleContents(moduleInfo, module);
            useSettingMenu.displayModuleContents(moduleInfo, module);
            importCodeFolder.displayModuleContents(moduleInfo, module);
            moduleVariableFolder.displayModuleContents(moduleInfo, module);
            moduleFunctionNameFolder.displayModuleContents(moduleInfo, module);
            needUseCodeFileEditPane.displayModuleContents(moduleInfo, module);
            moduleFilePutCodePane.displayModuleContents(moduleInfo, module);
            moduleControlFolder.displayModuleContents(moduleInfo, module);
            initCodeFolder.displayModuleContents(moduleInfo, module);
            moduleSetCodeEditPane.displayModuleContents(moduleInfo, module);
            moduleFunctionEditPane.displayModuleContents(moduleInfo, module);
            LazyCoderFormatControl.updateCodePaneMenu(ModuleEditPaneHolder.moduleFormatModel);
        }

        SysUtil.updateFrameUI(DataSourceEditHolder.moduleEditPane);
        if (module.getEnabledState() == Module.FALSE_) {
            AfToaster.show(DataSourceEditHolder.dataSourceEditFrame, AfToaster.Level.WARN, 4500, "\t这个模块还没写过任何内容，没法使用，\t\n\t请尽快填好要写的内容并保存");
        } else {
            DataSourceEditHolder.showErrorListIfNeed("这模块原来写的内容有点异常喔   (=ω=；)");//如果在还原过程中出现异常，列出异常情况
        }
    }


}
