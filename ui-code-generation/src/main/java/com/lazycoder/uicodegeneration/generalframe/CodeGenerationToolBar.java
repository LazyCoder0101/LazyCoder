package com.lazycoder.uicodegeneration.generalframe;

import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

public class CodeGenerationToolBar extends MyToolBar {

    private MyButton generateSourceCodeButton;

    private JCheckBox autoCollapseCheckBox, formattedSourceCodeCheckBox;

    private CodeGenerationFrame codeGenerationFrame = null;

    public CodeGenerationToolBar(CodeGenerationFrame codeGenerationFrame) {
        super();
        this.codeGenerationFrame = codeGenerationFrame;
        componentInit();
    }

    private void componentInit() {
        Component horizontalStrut1 = Box.createHorizontalStrut(10);
        add(horizontalStrut1);

        autoCollapseCheckBox = new JCheckBox("自动折叠");
        autoCollapseCheckBox.setSelected(true);
        CodeGenerationFrameHolder.autoCollapseCheckBox = autoCollapseCheckBox;
        autoCollapseCheckBox.setFocusPainted(false);
        //autoCollapseCheckBox.setBounds(featureSelectionPaneX, autoCollapseCheckBoxY, 133, autoCollapseCheckBoxHeight);
        add(autoCollapseCheckBox);

        Component horizontalStrut2 = Box.createHorizontalStrut(15);
        add(horizontalStrut2);

        formattedSourceCodeCheckBox = new JCheckBox("格式化源码");
        formattedSourceCodeCheckBox.setSelected(true);
        formattedSourceCodeCheckBox.addItemListener(codeFormatListener);
        CodeGenerationFrameHolder.formattedSourceCodeCheckBox = formattedSourceCodeCheckBox;
        formattedSourceCodeCheckBox.setFocusPainted(false);
        //formattedSourceCodeCheckBox.setBounds(featureSelectionPaneX, formattedSourceCodeCheckBoxY, 133, formattedSourceCodeCheckBoxHeight);
        add(formattedSourceCodeCheckBox);

        Component horizontalStrut3 = Box.createHorizontalStrut(15);
        add(horizontalStrut3);

        generateSourceCodeButton = new MyButton("生成源码");
        generateSourceCodeButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
        generateSourceCodeButton.setBorderPainted(false);
        generateSourceCodeButton.setBackground(new Color(235, 104, 65));
        generateSourceCodeButton.setForeground(Color.white);
        generateSourceCodeButton.addActionListener(listener);
        //generateSourceCodeButton.setBounds(featureSelectionPaneX, generateSourceCodeButtonY, 140, generateSourceCodeButtonHeight);
        add(generateSourceCodeButton);

    }


    /**
     * 生成源码
     */
    private void generateCode() {
        CodeGenerationFrameHolder.generateCode();
        if (this.codeGenerationFrame!=null&&this.codeGenerationFrame.getFrameModel()==CodeGenerationFrame.USER_CODE_GENERATETION_FRAME){
            String[] options = new String[]{"好的", "打开项目文件"};
            int temp = LazyCoderOptionPane.showOptionDialog(null,
                    "(^_^)  已生成代码！！", "系统消息",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
                    options[0]);
            if (1 == temp) {//好的
                FileUtil.openAndSelect(new File(CodeGenerationFrameHolder.projectParentPath + File.separator + CodeGenerationFrameHolder.projectName + File.separator + "源码"));
            }
        }else {
            LazyCoderOptionPane.showMessageDialog(null, "(^_^)  已生成代码！！");
        }

        //把新项目路径的文件夹图标改成懒农图标（有时候生成项目的时候，没能成功设置成懒农图标，在这里重复设置）
        String newProPath = CodeGenerationFrameHolder.projectParentPath + File.separator + CodeGenerationFrameHolder.projectName;
        SourceGenerateFileMethod.setLannongProIcon(new File(newProPath));
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == generateSourceCodeButton) {
                generateCode();
            }
        }
    };

    private ItemListener codeFormatListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (CodeGenerationFrameHolder.codeShowPanel != null) {
                CodeGenerationFrameHolder.codeShowPanel.updateAllCodeContent();
            }
        }
    };

}
