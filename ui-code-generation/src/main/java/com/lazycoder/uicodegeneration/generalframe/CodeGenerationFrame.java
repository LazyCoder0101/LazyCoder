package com.lazycoder.uicodegeneration.generalframe;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.SysParam;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.service.fileStructure.SourceGenerateProFile;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.*;
import com.lazycoder.uicodegeneration.component.generalframe.FormatControlPaneLable;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowTabbedPanel;
import com.lazycoder.uicodegeneration.generalframe.operation.CodeControlTabbedPane;
import com.lazycoder.uicodegeneration.generalframe.palette.CodeOperationPanel;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.tool.FunctionAddBeanIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.tool.FunctionNameIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.tool.VariableIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.CodeShowPaneModel;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.UUIDUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import lombok.Getter;
import org.noos.xing.mydoggy.DockedTypeDescriptor;
import org.noos.xing.mydoggy.ToolWindow;
import org.noos.xing.mydoggy.ToolWindowAnchor;
import org.noos.xing.mydoggy.ToolWindowTypeDescriptor;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerationFrame extends LazyCoderCommonFrame {

    /**
     * 功能选择比例
     */
    public final static double FEATURE_SELECTION_PROPORTION = 0.15625;
    /**
     *
     */
    private static final long serialVersionUID = -289076051842811026L;

    //    private final double ifFividerLocation = 0.45;
    public CodeOperationPanel codeOperationPanel;
    public CodeControlTabbedPane codeControlTabbedPane;
    public CodeShowTabbedPanel codeShowPanel;
    private JPanel contentPane;

    @Getter
    private int frameModel = USER_CODE_GENERATETION_FRAME;

    /**
     * 终端用户使用的代码生成界面模式
     */
    public final static int USER_CODE_GENERATETION_FRAME = 0;

    /**
     * 开发者用于进行预览测试的模式
     */
    public final static int PREVIEW_TEST_PROJECT_FRAME = 1;

//    private JSplitPane splitPane;

    private String proID;//项目id

    private String useUserDBID;//使用的数据库id

    private CodeGenerationToolBar toolBar;

    private CodeGenerationFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置最高并去掉任务栏高度 摘自 https://www.iteye.com/blog/krs-2042006
        //设置frame的大小
        this.setSize(SysUtil.SCREEN_SIZE.width, SysUtil.SCREEN_SIZE.height - SysUtil.taskbarInsets.bottom);
    }

    /**
     * 界面初始化
     */
    private void codeGenerationFrameInit(){

//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPane.setLayout(new BorderLayout());
        contentPane.setLayout(new BorderLayout(0, 0));

        codeOperationPanel = new CodeOperationPanel(this);
        contentPane.add(codeOperationPanel, BorderLayout.WEST);
        Dimension dd1 = new Dimension((int) (FEATURE_SELECTION_PROPORTION * SysUtil.SCREEN_SIZE.width),
                SysUtil.SCREEN_SIZE.height);
        codeOperationPanel.setPreferredSize(dd1);
        codeOperationPanel.setMaximumSize(dd1);
        codeOperationPanel.setMinimumSize(dd1);

//        splitPaneInit();

        codeControlTabbedPane = new CodeControlTabbedPane();
        CodeGenerationFrameHolder.codeControlTabbedPane = codeControlTabbedPane;
//        splitPane.setLeftComponent(codeControlTabbedPane);
        Dimension dd2 = new Dimension((int) ((1 - FEATURE_SELECTION_PROPORTION) * SysUtil.SCREEN_SIZE.width * 0.5),
                SysUtil.SCREEN_SIZE.height);
        codeControlTabbedPane.setPreferredSize(dd2);
        codeControlTabbedPane.setMinimumSize(dd2);
        codeControlTabbedPane.setMaximumSize(dd2);

        contentPane.add(codeControlTabbedPane, BorderLayout.CENTER);

        codeShowPanel = new CodeShowTabbedPanel();
        CodeGenerationFrameHolder.codeShowPanel = codeShowPanel;
//        splitPane.setRightComponent(codeShowPanel);
        Dimension dd3 = new Dimension((int) ((1 - FEATURE_SELECTION_PROPORTION) * SysUtil.SCREEN_SIZE.width * 0.5),
                SysUtil.SCREEN_SIZE.height);
        codeShowPanel.setPreferredSize(dd3);
        codeShowPanel.setMinimumSize(dd3);
        codeShowPanel.setMaximumSize(dd3);

        MyDoggyToolWindowManager codePaneToolWindowManager = new MyDoggyToolWindowManager();
        codePaneToolWindowManager.setMainContent(contentPane);
        ToolWindow codePaneToolWindow = codePaneToolWindowManager.registerToolWindow("代码", "生成代码", null, codeShowPanel, ToolWindowAnchor.RIGHT);
        codePaneToolWindow.setAvailable(true);
        setContentPane(codePaneToolWindowManager);

        toolBarInit();
        // 设置侧边栏的默认比例
        SwingUtilities.invokeLater(() -> {
            // 确保组件已经被布局，然后设置分割位置
            int width = SysUtil.SCREEN_SIZE.width;
            DockedTypeDescriptor descriptor = codePaneToolWindow.getTypeDescriptor(DockedTypeDescriptor.class);
            descriptor.setMinimumDockLength((int) (width * 0.7));
            //toolWindowManager.getRepresentativeAnchorDescriptor().setDividerLocation((int) (width * 0.75));
            ToolWindowTypeDescriptor toolWindowTypeDescriptor = codePaneToolWindow.getTypeDescriptor(ToolWindowTypeDescriptor.class);
            toolWindowTypeDescriptor.setIdVisibleOnTitleBar(false);

        });

        requestFocus();
    }

    /**
     * 打开文件
     *
     * @param proInit
     */
    public CodeGenerationFrame(ProInit proInit, int frameModel) {
        this();
        this.frameModel = frameModel;
        codeGenerationFrameInit();

        CodeGenerationFrameHolder.currentEncodingType = SysService.SYS_PARAM_SERVICE.getEncoding();

        CodeGenerationFrameModel codeGenerationFrameModel = SourceGenerateModelGet
                .getCodeGenerationFrameModel(proInit.getProjectParentPath(), proInit.getProjectName());// 获取根模型

        this.proID = codeGenerationFrameModel.getProID();
        this.useUserDBID = codeGenerationFrameModel.getUseUserDBID();

        CodeGenerationFrameHolder.projectParentPath = proInit.getProjectParentPath();
        CodeGenerationFrameHolder.projectName = proInit.getProjectName();
        CodeGenerationFrameHolder.codeGenerationFrame = this;

        setFrameTitle(CodeGenerationFrameHolder.projectName);
        CodeGenerationFrameHolder.pathTextField.setText(proInit.getProjectParentPath() + File.separator + proInit.getProjectName());

        CodeGenerationModuleCustomVariableHolder.moduleCustomVariableList = codeGenerationFrameModel
                .getModuleCustomVariableList();

        CodeGenerationModuleFormatVariableHolder.moduleFormatVariableList = codeGenerationFrameModel
                .getModuleFormatVariableList();// 添加模块变量

        CodeGenerationAdditionalFormatVariableHolder.additionalFormatVariableList = codeGenerationFrameModel
                .getAdditionalFormatVariableList();

        CodeGenerationAdditionalFormatFunctionNameHolder.additionalFormatFunctionNameHolderList = codeGenerationFrameModel
                .getAdditionalFormatFunctionNameHolderList();

        CodeGenerationModuleCustomFunctionNameHolder.moduleCustomFunctionNameList = codeGenerationFrameModel
                .getModuleCustomFunctionNameHolderList();

        CodeGenerationModuleFormatFunctionNameHolder.moduleFormatFunctionNameList = codeGenerationFrameModel
                .getModuleFormatFunctionNameHolderList();

        FileSelectorHolder.fileRecordList = codeGenerationFrameModel.getFileRecordList();


        CodeIDGenerator.setCodeSerialNumber(codeGenerationFrameModel.getCodeSerialNumber());
        FunctionAddBeanIDGenerator.setFunctionAddBeanSerialNumber(codeGenerationFrameModel.getFunctionAddBeanSerialNumber());
        FunctionNameIDGenerator.setFunctionNameSerialNumber(codeGenerationFrameModel.getFunctionNameSerialNumber());
        VariableIDGenerator.setVariableSerialNumber(codeGenerationFrameModel.getVariableSerialNumber());

        restoreCodeShowPane(codeGenerationFrameModel);
        restoreFormatControlPane(codeGenerationFrameModel);
        CodeGenerationFrameHolder.codeControlTabbedPane.setSelectedIndex(0);
        closingWindow();
        setVisible(true);

        ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
        if (opratingContainerList != null) {
            for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                opratingContainer.showSelectedValue();
            }
        }

        CodeGenerationFrameHolder.featureSelectedPane.updateModuleList(CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane());
    }

    /**
     * 新建
     *
     * @param projectParentPath
     * @param projectName
     * @param moduleRelatedParamList
     * @param mainFileName
     */
    public CodeGenerationFrame(String projectParentPath, String projectName, ArrayList<ModuleRelatedParam> moduleRelatedParamList,
                               String mainFileName, int frameModel) {
        this();
        this.frameModel = frameModel;
        codeGenerationFrameInit();

        CodeGenerationFrameHolder.currentEncodingType = SysService.SYS_PARAM_SERVICE.getEncoding();

        CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空

        CodeGenerationFrameHolder.projectParentPath = projectParentPath;
        CodeGenerationFrameHolder.projectName = projectName;
        CodeGenerationFrameHolder.codeGenerationFrame = this;
        setFrameTitle(CodeGenerationFrameHolder.projectName);

        this.proID = UUIDUtil.getUUID();
        this.useUserDBID = SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.DBID);

        try {
            SourceGenerateFileMethod.generateProjectFileStrucure(projectParentPath, projectName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SourceGenerateFileMethod.copyTemplateInProject(projectParentPath, projectName);
        SourceGenerateFileMethod.copyUseDocInProject(projectParentPath, projectName);
        SourceGenerateFileMethod.renameToNeedProjectFiles(projectParentPath, projectName);

        addMain(moduleRelatedParamList, mainFileName);
        addAdditionalThatMustBeAdded();
        CodeGenerationFrameHolder.codeControlTabbedPane.setSelectedIndex(0);

        CodeGenerationFrameHolder.pathTextField.setText(projectParentPath + File.separator + projectName);

        closingWindow();
        setVisible(true);

        //把不需要用户设置的值都自动选上
        ArrayList<OpratingContainerInterface> allOpratingContainer = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
        for (OpratingContainerInterface opratingContainer : allOpratingContainer) {
            opratingContainer.setNoUserSelectionIsRequiredValue();
        }

        CodeGenerationFrameHolder.showErrorListIfNeed("使用的这个数据源有点异常喔   (=ω=；)");
    }

    /**
     * 根据存储的信息还原代码面板
     *
     * @param codeGenerationFrameModel
     */
    private void restoreCodeShowPane(CodeGenerationFrameModel codeGenerationFrameModel) {
        CodeShowPaneModel codeShowPaneModelTemp;

        ArrayList<CodeFormatFlagParam> codeFormatFlagParamList = codeGenerationFrameModel.getCodeFormatFlagParamList();
        if (codeFormatFlagParamList != null) {
            for (CodeFormatFlagParam codeFormatFlagParamTemp : codeFormatFlagParamList) {
                codeShowPaneModelTemp = SourceGenerateModelGet.getCodeShowPaneModel(
                        CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName,
                        codeFormatFlagParamTemp);// 根据代码格式的属性，在对应路径找到存有内容的文件并转换成模型
                codeShowPanel.restoreCodeShowPane(codeFormatFlagParamTemp, codeShowPaneModelTemp);
            }
        }
    }


//    private ComponentAdapter cAdapter = new ComponentAdapter() {
//        @Override
//        public void componentResized(ComponentEvent e) {
//            splitPane.setDividerLocation(ifFividerLocation);
//        }
//    };


    /**
     * 根据储存的信息还原格式控制面板
     *
     * @param codeGenerationFrameModel
     */
    private void restoreFormatControlPane(CodeGenerationFrameModel codeGenerationFrameModel) {
        ArrayList<FormatControlPaneLable> list = codeGenerationFrameModel.getFormatControlPaneLableList();
        codeControlTabbedPane.restoreFormatControlPane(list);
    }

//    private void splitPaneInit() {
//        splitPane = new JSplitPane();
//        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 设置分割线方向
//        splitPane.addComponentListener(cAdapter);
//        splitPane.setEnabled(false);
//        contentPane.add(splitPane, BorderLayout.CENTER);
//    }

    /**
     * 添加必填模板，只用一次
     *
     * @param list     模块列表
     * @param fileName 文件名
     */
    private void addMain(ArrayList<ModuleRelatedParam> list, String fileName) {
        codeControlTabbedPane.addMainRelatedContent(list, fileName);
    }

    /**
     * 添加每次一定要添加的可选模板
     */
    private void addAdditionalThatMustBeAdded() {//把自动生成一次还有自动生成一次且只能使用一次的可选模板添加进去
        String fileName;
        List<Integer> setPropertyList = new ArrayList<>();
        setPropertyList.add(FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue());
        List<AdditionalOperating> alist = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalOperatingList(setPropertyList);
        for (AdditionalOperating operating : alist) {
            fileName = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalDeafaultFormatCodeFileName(operating.getAdditionalSerialNumber());//获取该可选模板默认的文件名
            codeControlTabbedPane.addAdditionalRelatedContent(fileName, operating.getAdditionalSerialNumber(), false);
        }

        setPropertyList = new ArrayList<>();
        setPropertyList.add(FunctionUseProperty.autoGenerateOnce.getSysDictionaryValue());
        setPropertyList.add(FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue());
        alist = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalOperatingList(setPropertyList);
        for (AdditionalOperating operating : alist) {
            fileName = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalDeafaultFormatCodeFileName(operating.getAdditionalSerialNumber());//获取该可选模板默认的文件名
            codeControlTabbedPane.addAdditionalRelatedContent(fileName, operating.getAdditionalSerialNumber(), true);
        }
    }

    /**
     * 保存项目文件
     */
    public void saveProjectFile() {
        CodeGenerationFrameModel codeGenerationFrameModel = new CodeGenerationFrameModel();
        codeGenerationFrameModel.setProID(this.proID);
        codeGenerationFrameModel.setUseUserDBID(this.useUserDBID);
        codeGenerationFrameModel.setProClientVersion(System.getProperty("clientVersion"));//将当前客户端版本记录，作为这个项目最后生成代码对应的客户端版本

        String ds_cli_ver = SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.DS_CLI_VER);//获取当前这个数据源的对应客户端版本
        codeGenerationFrameModel.setDataSourceClientVersion(ds_cli_ver);//进行记录

        codeGenerationFrameModel
                .setModuleCustomVariableList(CodeGenerationModuleCustomVariableHolder.moduleCustomVariableList);
        codeGenerationFrameModel
                .setModuleFormatVariableList(CodeGenerationModuleFormatVariableHolder.moduleFormatVariableList);
        codeGenerationFrameModel.setAdditionalFormatVariableList(
                CodeGenerationAdditionalFormatVariableHolder.additionalFormatVariableList);
        codeGenerationFrameModel.setAdditionalFormatFunctionNameHolderList(
                CodeGenerationAdditionalFormatFunctionNameHolder.additionalFormatFunctionNameHolderList);
        codeGenerationFrameModel.setModuleCustomFunctionNameHolderList(
                CodeGenerationModuleCustomFunctionNameHolder.moduleCustomFunctionNameList);
        codeGenerationFrameModel.setModuleFormatFunctionNameHolderList(
                CodeGenerationModuleFormatFunctionNameHolder.moduleFormatFunctionNameList);
        codeGenerationFrameModel.setFormatControlPaneLableList(
                CodeGenerationFrameHolder.codeControlTabbedPane.getFormatControlPaneLable());
        codeGenerationFrameModel
                .setCodeFormatFlagParamList(CodeGenerationFrameHolder.codeShowPanel.getCodeFormatFlagParamList());
        codeGenerationFrameModel.setFileRecordList(FileSelectorHolder.fileRecordList);
        File file = SourceGenerateProFile.getCodeGenerationRootProFile(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName);
//        FileUtil.writeFile(file, JsonUtil.getJsonStr(codeGenerationFrameModel));


        codeGenerationFrameModel.setCodeSerialNumber(CodeIDGenerator.getCodeSerialNumber());
        codeGenerationFrameModel.setFunctionAddBeanSerialNumber(FunctionAddBeanIDGenerator.getFunctionAddBeanSerialNumber());
        codeGenerationFrameModel.setFunctionNameSerialNumber(FunctionNameIDGenerator.getFunctionNameSerialNumber());
        codeGenerationFrameModel.setVariableSerialNumber(VariableIDGenerator.getVariableSerialNumber());

        JsonUtil.writeFile(file, codeGenerationFrameModel);
    }

    /**
     * 关闭窗口
     */
    private void closingWindow() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String showText = "亲，确定要退出系统吗？";
                if (frameModel == PREVIEW_TEST_PROJECT_FRAME) {
                    showText = "亲，确定要关闭这个预览测试窗口吗？";
                }
                int show = LazyCoderOptionPane.showConfirmDialog(null, showText, "系统提示", JOptionPane.YES_NO_OPTION);
                if (show == JOptionPane.YES_OPTION) {
                    CodeGenerationFrameHolder.generateCode();
//                    FileUtil.setFileHidden(SourceGenerateFileStructure.getGenerateSourceLannongPath(
//                            CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName));//把lannong文件夹进行隐藏

                    if (frameModel == USER_CODE_GENERATETION_FRAME) {
                        System.exit(0);// 正常退出
                    } else if (frameModel == PREVIEW_TEST_PROJECT_FRAME) {
                        GeneralHolder.previewTesting = false;
                        dispose();
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {// 当点击最小化时
                // TODO 自动生成的方法存根
                super.windowIconified(e);
            }
        });
    }

    /**
     * 后续考虑添加功能，在这里添加操作组件
     */
    private void toolBarInit() {
        toolBar = new CodeGenerationToolBar(this);
        contentPane.add(toolBar, BorderLayout.NORTH);
    }

    /**
     * 设置界面标题
     * @param title
     */
    private void setFrameTitle(String title){
        if (frameModel == USER_CODE_GENERATETION_FRAME) {
            setTitle(title);
        } else if (frameModel == PREVIEW_TEST_PROJECT_FRAME) {
            setTitle(title+" （预览测试）");
        }
    }

}
