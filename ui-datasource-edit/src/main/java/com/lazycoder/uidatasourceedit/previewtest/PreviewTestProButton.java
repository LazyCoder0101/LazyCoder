package com.lazycoder.uidatasourceedit.previewtest;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 预览测试按钮
 */
public class PreviewTestProButton extends MyButton {

    private static ImageIcon previewTestIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator +
            "DataSourceEdit" + File.separator
            + "预览测试.png");


    public PreviewTestProButton(){
        setIcon(previewTestIcon);
        setToolTipText("录入并保存数据后点击此按钮，\n" +
                "可创建项目测试录入数据是否正常生成代码");
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewTest();
            }
        });
    }

    /**
     * 预览测试
     */
    private void previewTest(){
        if (!DataSourceEditHolder.previewTesting){
            if (SysService.SYS_PARAM_SERVICE.getEnabledState()) {
                new PreviewTestProjectFrame();
            }else {
                LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆	这个数据源还没用录入内容，请录入内容并保存后再进行预览测试！");
            }
        }else {
            LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆	已经打开预览测试功能了！");
        }
    }

}

