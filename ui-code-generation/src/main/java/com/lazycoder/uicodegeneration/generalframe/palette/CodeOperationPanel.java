package com.lazycoder.uicodegeneration.generalframe.palette;

import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.generalframe.AddModuleButton;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrame;
import com.lazycoder.uicodegeneration.generalframe.SelectedModuleParam;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.MainFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.palette.additional.AdditionalOperationPane;
import com.lazycoder.uicodegeneration.moduleselect.ModuleSelectListPane;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class CodeOperationPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 3291782058884711260L;

    private JTextField pathTextField;
    private MyButton updatePathButton;
    private AddModuleButton addModuleBt;
    private FeatureSelectedTabPane panel;
    private AdditionalOperationPane additionalOperationPane;

    private OperatingTipButton operatingTip;

    private final ImageIcon proPathIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration"
            + File.separator + "更改项目路径.png");

    private CodeGenerationFrame codeGenerationFrame = null;

    public CodeOperationPanel(CodeGenerationFrame codeGenerationFrame) {
        this.codeGenerationFrame = codeGenerationFrame;

        setLayout(null);

        int additionalNum = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalFeatureSelectionNum();//看看有多少个其他格式

        int featureSelectionPaneX = (int) (0.02 * SysUtil.SCREEN_SIZE.width),
                featureSelectionPaneY = 20,
                featureSelectionPaneWidth = (int) (0.117 * SysUtil.SCREEN_SIZE.width),
                featureSelectionPaneHeight = additionalNum > 0 ?
                        (int) (0.4 * SysUtil.SCREEN_SIZE.height) : (int) (0.6 * SysUtil.SCREEN_SIZE.height),

                addModuleBtX = featureSelectionPaneX,
                addModuleBtY = featureSelectionPaneY + featureSelectionPaneHeight + 10,
                addModuleBtWidth = 80,
                addModuleBtHeight = 30,

                additionalOperationPaneX = featureSelectionPaneX,
                additionalOperationPaneY = addModuleBtY + addModuleBtHeight + 20,
                additionalOperationPaneWidth = featureSelectionPaneWidth,
                additionalOperationPaneHeight = (int) (0.17 * SysUtil.SCREEN_SIZE.height);

        int updatePathButtonY = 0, updatePathButtonHeight = 30, pathTextFieldY = 0, pathTextFieldHeight = 30;
        if (additionalNum == 0) {
            updatePathButtonY = addModuleBtY + addModuleBtHeight + 30;

        } else if (additionalNum > 0) {
            updatePathButtonY = additionalOperationPaneY + additionalOperationPaneHeight + 30;
        }

        pathTextFieldY = updatePathButtonY + updatePathButtonHeight + 20;

        panel = new FeatureSelectedTabPane();
        CodeGenerationFrameHolder.featureSelectedPane = panel;
        panel.setBounds(featureSelectionPaneX, featureSelectionPaneY, featureSelectionPaneWidth,
                featureSelectionPaneHeight);
        add(panel);

        addModuleBt = new AddModuleButton("添加模块", addModuleBtWidth, addModuleBtHeight) {
            @Override
            protected void doSomeThingWhenExpandPanel() {
                super.doSomeThingWhenExpandPanel();
                SelectedModuleParam selectedModuleParam = new SelectedModuleParam();

                AbstractFormatControlPane currentFormatControlPane = CodeGenerationFrameHolder.currentFormatControlPane;

                selectedModuleParam.setModuleRelatedParamList(currentFormatControlPane.getUseModuleRelatedParamList());
                selectedModuleParam.setProjectParentPath(CodeGenerationFrameHolder.projectParentPath);
                selectedModuleParam.setProjectName(CodeGenerationFrameHolder.projectName);
                selectedModuleParam.setCorrespondingPaneFileName(currentFormatControlPane.getFileName());
                if (currentFormatControlPane instanceof MainFormatControlPane) {
                    selectedModuleParam.setUsingObject(UsingObject.MAIN_USING_OBJECT);
                } else if (currentFormatControlPane instanceof AdditionalFormatControlPane) {
                    UsingObject usingObject = new UsingObject(currentFormatControlPane.getAdditionalSerialNumber());
                    selectedModuleParam.setUsingObject(usingObject);
                }
                moduleSelectListPane.showModuleList(selectedModuleParam.getUsingObject(), selectedModuleParam.getModuleRelatedParamList(), ModuleSelectListPane.RESELECT);

            }
        };
        addModuleBt.setBounds(addModuleBtX, addModuleBtY, addModuleBtWidth, addModuleBtHeight);
        addModuleBt.setBorderPainted(false);
        addModuleBt.setBackground(new Color(29,162,253));
        addModuleBt.setForeground(Color.white);
        add(addModuleBt);

        operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "生成程序")
                .getAbsolutePath());
        operatingTip.setLocation(addModuleBt.getLocation().x + addModuleBt.getWidth() + 20, addModuleBt.getY() + 5);
        add(operatingTip);

        if (additionalNum > 0) {
            additionalOperationPane = new AdditionalOperationPane();
            CodeGenerationFrameHolder.additionalOperationPane = additionalOperationPane;
            JScrollPane scrollPane2 = new JScrollPane(additionalOperationPane);
            scrollPane2.setBounds(additionalOperationPaneX, additionalOperationPaneY, additionalOperationPaneWidth,
                    additionalOperationPaneHeight);
            add(scrollPane2);
        }

        JLabel label = new JLabel("生成源码位置：");
        label.setBounds(featureSelectionPaneX, updatePathButtonY, 113, updatePathButtonHeight);
        add(label);

        updatePathButton = new MyButton(proPathIcon);
        updatePathButton.setToolTipText("更改项目路径");
        updatePathButton.addActionListener(listener);
        updatePathButton.setBounds(featureSelectionPaneX + 100, updatePathButtonY, 60, updatePathButtonHeight);
        add(updatePathButton);
        if (this.codeGenerationFrame != null && this.codeGenerationFrame.getFrameModel() == CodeGenerationFrame.PREVIEW_TEST_PROJECT_FRAME) {
            updatePathButton.setEnabled(false);
        }

        pathTextField = new JTextField();
        pathTextField.setEditable(false);
        CodeGenerationFrameHolder.pathTextField = pathTextField;
        pathTextField.setBounds(featureSelectionPaneX, pathTextFieldY, additionalOperationPaneWidth, pathTextFieldHeight);
        add(pathTextField);
        if (this.codeGenerationFrame != null && this.codeGenerationFrame.getFrameModel() == CodeGenerationFrame.PREVIEW_TEST_PROJECT_FRAME) {
            pathTextField.setEnabled(false);
        }
    }

    /**
     * 再添加模块
     */
//    private void addModule() {
//        SelectedModuleParam selectedModuleParam = new SelectedModuleParam();
//
//        AbstractFormatControlPane currentFormatControlPane = CodeGenerationFrameHolder.currentFormatControlPane;
//
//        selectedModuleParam.setModuleRelatedParamList(currentFormatControlPane.getUseModuleRelatedParamList());
//        selectedModuleParam.setProjectParentPath(CodeGenerationFrameHolder.projectParentPath);
//        selectedModuleParam.setProjectName(CodeGenerationFrameHolder.projectName);
//        selectedModuleParam.setCorrespondingPaneFileName(currentFormatControlPane.getFileName());
//        if (currentFormatControlPane instanceof MainFormatControlPane) {
//            selectedModuleParam.setUsingObject(UsingObject.MAIN_USING_OBJECT);
//        } else if (currentFormatControlPane instanceof AdditionalFormatControlPane) {
//            UsingObject usingObject = new UsingObject(currentFormatControlPane.getAdditionalSerialNumber());
//            selectedModuleParam.setUsingObject(usingObject);
//        }
//        ModuleSelectPane.ModuleSelectFrame(selectedModuleParam);
//    }


    /**
     * 更改项目路径
     */
    private void updateProPath() {
        File pathFolder = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL, "请选择新的项目路径");
        if (pathFolder != null) {
            if (pathFolder.getAbsolutePath().equals(CodeGenerationFrameHolder.projectParentPath) == false) {//如果选择路径和当前路径不同，开始改项目路径
                String oldProPath = CodeGenerationFrameHolder.projectParentPath + File.separator + CodeGenerationFrameHolder.projectName;
                try {
                    FileUtil.copyDir(oldProPath, pathFolder.getAbsolutePath());//把生成文件拷到对应路径

                    //更改对应静态变量和输入框显示的值
                    CodeGenerationFrameHolder.projectParentPath = pathFolder.getAbsolutePath();
                    pathTextField.setText(CodeGenerationFrameHolder.projectParentPath + File.separator + CodeGenerationFrameHolder.projectName);

                    //立即保存项目信息的存储文件，以实时更新数据
                    CodeGenerationFrameHolder.codeGenerationFrame.saveProjectFile();

                    //删除原来路径的生成文件
                    FileUtil.delFolder(oldProPath);

                    //把新项目路径的文件夹图标改成懒农图标
                    String newProPath = pathFolder.getAbsolutePath() + File.separator + CodeGenerationFrameHolder.projectName;
                    SourceGenerateFileMethod.setLannongProIcon(new File(newProPath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == updatePathButton) {
                updateProPath();

//            } else if (e.getSource() == addModuleBt) {
//                addModule();
            }
        }
    };


}
