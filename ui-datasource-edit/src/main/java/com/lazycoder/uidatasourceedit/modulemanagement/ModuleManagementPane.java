package com.lazycoder.uidatasourceedit.modulemanagement;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.ModuleManagementPaneHolder;
import com.lazycoder.uidatasourceedit.component.component.ClassNameTablePageNavigator;
import com.lazycoder.uidatasourceedit.component.component.table.classification.ClassificationTable;
import com.lazycoder.uidatasourceedit.component.component.table.module.ModuleTable;
import com.lazycoder.uidatasourceedit.modulemanagement.editframe.ClassEditFrame;
import com.lazycoder.uidatasourceedit.modulemanagement.editframe.ModuleEditFrame;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class ModuleManagementPane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -808044989895970411L;
    private ClassificationTable classTable;
    private ModuleTable moduleTable;
    private MyButton addClassificationBt, addModuleBt, queryBt;
    private ModuleManagementPaneClassCombobox classNameComboBox;

    private ModuleManagementPaneUsingRangeCombobox managementPaneUsingRangeCombobox;

    private ClassNameTablePageNavigator navigator;

    private OperatingTipButton operatingTip;

    /**
     * Create the panel.
     */
    public ModuleManagementPane() {
        setLayout(null);

        Dimension dd = SysUtil.SCREEN_SIZE;

//		this.setBounds(0, 0, 1766, 768);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBackground(Color.black);
        separator.setBounds((int) (0.28 * dd.width), 0, 2, 1080);
        add(separator);

//		MyFlatButtonUI buttonUI = new MyFlatButtonUI();

        // 分类表格
        classTable = new ClassificationTable();
        ModuleManagementPaneHolder.classTable = classTable;
        JScrollPane classScrollPane = new JScrollPane(classTable);
        classScrollPane.setBounds((int) (0.036 * dd.width), 130, (int) (0.2 * dd.width), (int) (0.56 * dd.height));
        add(classScrollPane);

        // 模块表格
        moduleTable = new ModuleTable();
        ModuleManagementPaneHolder.moduleTable = moduleTable;
        JScrollPane moduleScrollPane = new JScrollPane(moduleTable);
        moduleScrollPane.setBounds((int) (0.34 * dd.width), 130, (int) (0.43 * dd.width), (int) (0.48 * dd.height));
        add(moduleScrollPane);

        addClassificationBt = new MyButton("添加分类");
//		addClassificationBt.setUI(buttonUI);
        addClassificationBt.setBounds(70, 60, 100, 30);
        addClassificationBt.addActionListener(listener);
        add(addClassificationBt);

        addModuleBt = new MyButton("添加模块");
//		addModuleBt.setUI(buttonUI);
        addModuleBt.setBounds((int) (0.34 * dd.width), 60, 100, 30);
        addModuleBt.addActionListener(listener);
        add(addModuleBt);

        JLabel classLabel = new JLabel("分类：");
        classLabel.setBounds((int) addModuleBt.getLocation().getX() + 160, 60, 50, 30);
        add(classLabel);

        classNameComboBox = new ModuleManagementPaneClassCombobox();
        ModuleManagementPaneHolder.classNameComboBox = classNameComboBox;
        classNameComboBox.setBounds((int) classLabel.getLocation().getX() + 50, 60, 120, 30);
        add(classNameComboBox);

        JLabel usingRangeLabel = new JLabel("使用范围：");
        usingRangeLabel.setBounds((int) classNameComboBox.getLocation().getX() + 180, 60, 80, 30);
        add(usingRangeLabel);

        managementPaneUsingRangeCombobox = new ModuleManagementPaneUsingRangeCombobox();
        ModuleManagementPaneHolder.managementPaneUsingRangeCombobox = managementPaneUsingRangeCombobox;
        managementPaneUsingRangeCombobox.setBounds((int) usingRangeLabel.getLocation().getX() + 80, 60, 120, 30);
        add(managementPaneUsingRangeCombobox);

        queryBt = new MyButton("查询");
//		queryBt.setUI(buttonUI);
        queryBt.setBounds((int) managementPaneUsingRangeCombobox.getLocation().getX() + 180, 60, 90, 30);
        queryBt.addActionListener(listener);
        add(queryBt);

        navigator = new ClassNameTablePageNavigator(moduleTable);
        ModuleManagementPaneHolder.navigator = navigator;
        navigator.setBounds((int) (0.29 * dd.width), moduleScrollPane.getY() + moduleScrollPane.getHeight() + 40, 1100,
                60);
        add(navigator);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "模块管理"
                ).getAbsolutePath()
        );
        operatingTip.setLocation(separator.getLocation().x - 60, addClassificationBt.getY() + 5);
        add(operatingTip);
    }

    /**
     * 添加分类
     */
    public void addClass() {
        new ClassEditFrame();
    }

    /**
     * 添加模块
     */
    public void addModule() {
        new ModuleEditFrame();
    }

    /**
     * 查询模块
     */
    public void queryModule() {
        if (ModuleManagementPaneHolder.navigator != null) {
            ModuleManagementPaneHolder.navigator.initModuleTable();
        }
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addClassificationBt) {
                addClass();
            } else if (e.getSource() == addModuleBt) {
                addModule();
            } else if (e.getSource() == queryBt) {
                queryModule();
            }
        }
    };

}
