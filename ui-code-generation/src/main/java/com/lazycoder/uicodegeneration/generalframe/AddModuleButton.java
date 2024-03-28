package com.lazycoder.uicodegeneration.generalframe;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.moduleselect.ModuleSelectListPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class AddModuleButton extends MyPopupButton {

    protected JScrollPane moduleSelectListScrollPane;

    protected ModuleSelectListPane moduleSelectListPane;

    private MyToolBar buttonBar;

    private JPanel contentPanel, bottomPanel;

    private MyButton closeBt, okBt;

    private static ImageIcon closeIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
                    + "close" + File.separator + "关闭.png"),

    closePessedIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
                    + "close" + File.separator + "关闭_ressed.png"),

    closeRolloverIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
                    + "close" + File.separator + "关闭_rollover.png");

    public AddModuleButton(String text, int bWidth, int bHeight) {
        super(text);
        buttonWidth = bWidth;
        buttonHeight = bHeight;
        paneWidth = 340;
        paneHeight = 650;
        init();
    }

    private void init() {
        buttonBar = new MyToolBar();
        buttonBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        closeBt = new MyButton(closeIcon);
        closeBt.setBorderPainted(false);
        closeBt.setRolloverIcon(closeRolloverIcon);
        closeBt.setPressedIcon(closePessedIcon);
        closeBt.addActionListener(listener);
        buttonBar.add(closeBt);

        moduleSelectListPane = new ModuleSelectListPane();
        moduleSelectListScrollPane = new JScrollPane(moduleSelectListPane);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        okBt = new MyButton("完成");
        okBt.setBorderPainted(false);
        okBt.setBackground(new Color(51,153,255));
        okBt.setForeground(Color.white);
        okBt.addActionListener(listener);
        bottomPanel.add(okBt);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(buttonBar, BorderLayout.NORTH);
        contentPanel.add(moduleSelectListScrollPane, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        setInternalComponent(contentPanel);
        contentPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == closeBt) {
                packUpPanel();
            } else if (e.getSource() == okBt) {
                readdModule();
            }
        }
    };

    /**
     * 再添加模块
     */
    private void readdModule() {
        if (CodeGenerationFrameHolder.currentFormatControlPane != null) {
            CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空

            ArrayList<Module> list = moduleSelectListPane.getSelectedModuleList();// 获取选中的所有模块
            if (list.size() > 0) {
                ArrayList<Module> sortList = SysService.MODULE_SERVICE.sortModuleListByNeedModuleParam(list);

                ArrayList<ModuleRelatedParam> moduleRelatedParaList = SysService.MODULE_SERVICE.getCurrentModuleInfoListBy(sortList);// 根据模块列表拿到所有模块的模块信息
                CodeGenerationFrameHolder.currentFormatControlPane.addInit(moduleRelatedParaList);

                CodeGenerationFrameHolder.currentFormatControlPane.setNoUserSelectionIsRequiredValue();

            }
            packUpPanel();
            CodeGenerationFrameHolder.showErrorListIfNeed("这个功能有点异常喔   (=ω=；)");
        }
    }


    @Override
    protected void showPopupPane() {
        if (isSelected() == true) {
            if (popupPanel == null) {
                if (theComponentPane != null) {
                    int additionalNum = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalFeatureSelectionNum();//看看有多少个其他格式

                    if (additionalNum == 0) {//没有其他格式
                        int buttonWidthTemp = getButtonWidth(),
                                buttonHeightTemp = getButtonHeight();
                        popupPanel = getPopupPanel(theComponentPane);
                        int theHeight = 0 - (paneHeight / 5) * 4 + buttonHeightTemp;
                        popupPanel.showPopup(AddModuleButton.this, buttonWidthTemp, theHeight);
                        doSomeThingWhenExpandPanel();

                    } else if (additionalNum > 0) {//有其他格式
                        int buttonWidthTemp = getButtonWidth(),
                                buttonHeightTemp = getButtonHeight();
                        popupPanel = getPopupPanel(theComponentPane);
                        int theHeight = 0 - paneHeight / 2 + buttonHeightTemp;
                        popupPanel.showPopup(AddModuleButton.this, buttonWidthTemp, theHeight);
                        doSomeThingWhenExpandPanel();

                    }
                }
            }
        } else {
            if (popupPanel != null) {
                doSomeThingWhenPackUpPanel();
                popupPanel.hidePopup();
                popupPanel = null;
            }
        }
    }

}
