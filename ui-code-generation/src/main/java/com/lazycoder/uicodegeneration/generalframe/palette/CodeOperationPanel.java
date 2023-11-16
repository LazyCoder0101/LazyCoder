package com.lazycoder.uicodegeneration.generalframe.palette;

import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.generalframe.SelectedModuleParam;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.MainFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.palette.additional.AdditionalOperationPane;
import com.lazycoder.uicodegeneration.moduleselect.ModuleSelectPane;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class CodeOperationPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 3291782058884711260L;

    private JTextField pathTextField;
    private MyButton generateSourceCodeButton, updatePathButton, addModuleBt;
    private JCheckBox autoCollapseCheckBox, formattedSourceCodeCheckBox;
    private FeatureSelectedPane panel;
    private AdditionalOperationPane additionalOperationPane;

    private OperatingTipButton operatingTip;

    private final ImageIcon proPathIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration"
            + File.separator + "更改项目路径.png");

    public CodeOperationPanel() {
        setLayout(null);

        int featureSelectionPaneX = (int) (0.0234375 * SysUtil.SCREEN_SIZE.width),
                featureSelectionPaneY = 20,
                featureSelectionPaneWidth = (int) (0.117 * SysUtil.SCREEN_SIZE.width),
                featureSelectionPaneHeight = (int) (0.27 * SysUtil.SCREEN_SIZE.height),

                addModuleBtX = featureSelectionPaneX,
                addModuleBtY = featureSelectionPaneY + featureSelectionPaneHeight + 10,
                addModuleBtWidth = 100,
                addModuleBtHeight = 30,

                additionalOperationPaneX = featureSelectionPaneX,
                additionalOperationPaneY = addModuleBtY + addModuleBtHeight + 20,
                additionalOperationPaneWidth = featureSelectionPaneWidth,
                additionalOperationPaneHeight = (int) (0.17 * SysUtil.SCREEN_SIZE.height),

                autoCollapseCheckBoxY = additionalOperationPaneY + additionalOperationPaneHeight + 20,
                autoCollapseCheckBoxHeight = 30,

                formattedSourceCodeCheckBoxY = autoCollapseCheckBoxY + autoCollapseCheckBoxHeight,
                formattedSourceCodeCheckBoxHeight = 30,

                generateSourceCodeButtonY = formattedSourceCodeCheckBoxY + formattedSourceCodeCheckBoxHeight + 20,
                generateSourceCodeButtonHeight = 30,

                updatePathButtonY = generateSourceCodeButtonY + generateSourceCodeButtonHeight + 20,
                updatePathButtonHeight = 30,

                pathTextFieldY = updatePathButtonY + updatePathButtonHeight + 20,
                pathTextFieldHeight = 30;

        panel = new FeatureSelectedPane();
        JScrollPane scrollPane = new JScrollPane(panel);
//        scrollPane.setHorizontalScrollBarPolicy(
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        CodeGenerationFrameHolder.featureSelectedPane = panel;
        scrollPane.setBounds(featureSelectionPaneX, featureSelectionPaneY, featureSelectionPaneWidth,
                featureSelectionPaneHeight);
        add(scrollPane);

        addModuleBt = new MyButton("添加模块");
        addModuleBt.setBounds(addModuleBtX, addModuleBtY, addModuleBtWidth, addModuleBtHeight);
        addModuleBt.addActionListener(listener);
        add(addModuleBt);

        additionalOperationPane = new AdditionalOperationPane();
        CodeGenerationFrameHolder.additionalOperationPane = additionalOperationPane;
        JScrollPane scrollPane2 = new JScrollPane(additionalOperationPane);
        scrollPane2.setBounds(additionalOperationPaneX, additionalOperationPaneY, additionalOperationPaneWidth,
                additionalOperationPaneHeight);
        add(scrollPane2);

        autoCollapseCheckBox = new JCheckBox("自动折叠");
        autoCollapseCheckBox.setSelected(true);
        CodeGenerationFrameHolder.autoCollapseCheckBox = autoCollapseCheckBox;
        autoCollapseCheckBox.setFocusPainted(false);
        autoCollapseCheckBox.setBounds(featureSelectionPaneX, autoCollapseCheckBoxY, 133, autoCollapseCheckBoxHeight);
        add(autoCollapseCheckBox);

        formattedSourceCodeCheckBox = new JCheckBox("格式化源码");
        formattedSourceCodeCheckBox.setSelected(true);
        formattedSourceCodeCheckBox.addItemListener(codeFormatListener);
        CodeGenerationFrameHolder.formattedSourceCodeCheckBox = formattedSourceCodeCheckBox;
        formattedSourceCodeCheckBox.setFocusPainted(false);
        formattedSourceCodeCheckBox.setBounds(featureSelectionPaneX, formattedSourceCodeCheckBoxY, 133, formattedSourceCodeCheckBoxHeight);
        add(formattedSourceCodeCheckBox);

        operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "生成程序")
                .getAbsolutePath());
        operatingTip.setLocation(autoCollapseCheckBox.getLocation().x + autoCollapseCheckBox.getWidth() , autoCollapseCheckBox.getY() + 5);
        add(operatingTip);

        generateSourceCodeButton = new MyButton("生成源码");
        generateSourceCodeButton.setFont(new Font("微软雅黑",Font.BOLD,12));
        generateSourceCodeButton.setBorderPainted(false);
        generateSourceCodeButton.setBackground(new Color(235,104,65));
        generateSourceCodeButton.setForeground(Color.white);
        generateSourceCodeButton.addActionListener(listener);
        generateSourceCodeButton.setBounds(featureSelectionPaneX, generateSourceCodeButtonY, 140, generateSourceCodeButtonHeight);
        add(generateSourceCodeButton);

        JLabel label = new JLabel("生成源码位置：");
        label.setBounds(featureSelectionPaneX, updatePathButtonY, 113, updatePathButtonHeight);
        add(label);

        updatePathButton = new MyButton(proPathIcon);
        updatePathButton.setToolTipText("更改项目路径");
        updatePathButton.addActionListener(listener);
        updatePathButton.setBounds(featureSelectionPaneX + 100, updatePathButtonY, 60, updatePathButtonHeight);
        add(updatePathButton);

        pathTextField = new JTextField();
        pathTextField.setEditable(false);
        CodeGenerationFrameHolder.pathTextField = pathTextField;
        pathTextField.setBounds(featureSelectionPaneX, pathTextFieldY, additionalOperationPaneWidth, pathTextFieldHeight);
        add(pathTextField);
    }

    private ItemListener codeFormatListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (CodeGenerationFrameHolder.codeShowPanel != null) {
                CodeGenerationFrameHolder.codeShowPanel.updateAllCodeContent();
            }
        }
    };

    /**
     * 再添加模块
     */
    private void addModule() {
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
        ModuleSelectPane.ModuleSelectFrame(selectedModuleParam);
    }

    /**
     * 生成源码
     */
    private void generateCode() {
        CodeGenerationFrameHolder.generateCode();

        String[] options = new String[]{"好的", "打开项目文件"};
        int temp = LazyCoderOptionPane.showOptionDialog(null,
                "(^_^)  已生成代码！！", "系统消息",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
                options[0]);

        //把新项目路径的文件夹图标改成懒农图标（有时候生成项目的时候，没能成功设置成懒农图标，在这里重复设置）
        String newProPath = CodeGenerationFrameHolder.projectParentPath + File.separator + CodeGenerationFrameHolder.projectName;
        SourceGenerateFileMethod.setLannongProIcon(new File(newProPath));
        if (1 == temp) {//好的
            FileUtil.openAndSelect(new File(CodeGenerationFrameHolder.projectParentPath + File.separator + CodeGenerationFrameHolder.projectName + File.separator + "源码"));
        }
    }

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
            if (e.getSource() == generateSourceCodeButton) {
                generateCode();

            } else if (e.getSource() == updatePathButton) {
                updateProPath();

            } else if (e.getSource() == addModuleBt) {
                addModule();
            }
        }
    };


}
