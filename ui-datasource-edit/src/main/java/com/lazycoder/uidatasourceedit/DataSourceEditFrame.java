package com.lazycoder.uidatasourceedit;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.formatedit.FormatEditPane;
import com.lazycoder.uidatasourceedit.generalset.GeneralSettingPane;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditPane;
import com.lazycoder.uidatasourceedit.modulemanagement.ModuleManagementPane;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DataSourceEditFrame extends LazyCoderCommonFrame {

    /**
     *
     */
    private static final long serialVersionUID = -5066333190331583114L;

    private JPanel contentPane;
    private JTabbedPane tabbedPane;

    private GeneralSettingPane generalSettingPane;
    private FormatEditPane formatEditPane;
    private ModuleManagementPane moduleManagementPane;
    private ModuleEditPane moduleEditPane;

    /**
     * Create the frame.
     */
    public DataSourceEditFrame() {
        setTitle("编辑数据源——" + DataSourceEditHolder.currentUserDataSourceLabel.getDataSourceName());

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//真正使用用这句
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 测试使用用这句

//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //设置最高并去掉任务栏高度 摘自 https://www.iteye.com/blog/krs-2042006
        //getScreenInsets是指获得屏幕的 insets
        //设置frame的大小
        this.setSize(SysUtil.SCREEN_SIZE.width, SysUtil.SCREEN_SIZE.height - SysUtil.taskbarInsets.bottom);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
//        tabbedPane.putClientProperty("TabbedPane.underlineColor", new ColorUIResource(Color.red));
        FlatTabbedPaneUI tabbedPaneUI = new FlatTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                underlineColor = getBackground();
                focusColor = new Color(236, 245, 255);
//                selectedBackground = new Color(242, 243, 245);
                hoverColor = new Color(242, 243, 245);
                selectedForeground = new Color(69, 125, 255);
            }
        };
        tabbedPane.setUI(tabbedPaneUI);

        contentPane.add(tabbedPane);
        dbEditInit();
        sysEditInit();
        moduleManagementInit();
        moduleEditInit();
        tabbedPane.addChangeListener(changeListener);
        DataSourceEditHolder.dataSourceEditFrame = this;

        closingWindow();
        setVisible(true);
    }

    /**
     * 设置当前选中的tab面板
     *
     * @param selectedIndex
     */
    public void setSelectedTab(int selectedIndex) {
        tabbedPane.setSelectedIndex(selectedIndex);
    }

    /**
     * 获取当前选中的面板（0：系统设置模板模板 1：必填模板和可选模板内容编辑面板 2：模块管理模板 3：模块内容编辑面板）
     *
     * @return
     */
    public int getCurrentSelectedIndex() {
        return tabbedPane.getSelectedIndex();
    }

    private void dbEditInit() {
        generalSettingPane = new GeneralSettingPane();
        generalSettingPane.setPreferredSize(new Dimension(1430, 960));
        JScrollPane dbEditScrollPane = new JScrollPane(generalSettingPane);
        tabbedPane.addTab("通用管理", null, dbEditScrollPane, null);
    }

    private void sysEditInit() {
        formatEditPane = new FormatEditPane();
        DataSourceEditHolder.formatEditPane = formatEditPane;
        tabbedPane.addTab("格式设置", null, formatEditPane, null);
    }

    private void moduleManagementInit() {
        moduleManagementPane = new ModuleManagementPane();
        DataSourceEditHolder.moduleManagementPane = moduleManagementPane;
        JScrollPane moduleEditScrollPane = new JScrollPane(moduleManagementPane);
        tabbedPane.addTab("模块管理", null, moduleEditScrollPane, null);
    }

    private void moduleEditInit() {
        moduleEditPane = new ModuleEditPane();
        DataSourceEditHolder.moduleEditPane = moduleEditPane;
        JScrollPane moduleScrollPane = new JScrollPane(moduleEditPane);
        tabbedPane.addTab("模块设置", null, moduleScrollPane, null);
        showHandCursor();
    }

    private void showHandCursor() {
        ArrayList<FoldButton> list = ModuleEditPaneHolder.initPaneHiddenButtonList;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).addMouseListener(adapter);
        }
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        list.addAll(formatEditPane.getAllEditContainerModel());
        list.addAll(moduleEditPane.getAllEditContainerModel());
        return list;
    }

    private ChangeListener changeListener = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
            // TODO Auto-generated method stub
            int tabNumber = getCurrentSelectedIndex();
            if (tabNumber == 1) {// 切换到格式面板
                formatEditPane.reloadFormatCodeInputPane();
            } else if (tabNumber == 3) {// 切换到模块模板
                if (ModuleEditPaneHolder.needUseCodeFileEditPane != null) {
                    ModuleEditPaneHolder.needUseCodeFileEditPane.reloadFormatCodeInputPane();
                }
                if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
                    ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
                }
            }
        }
    };

    private MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    };

    /**
     * 关闭窗口
     */
    private void closingWindow() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int show = LazyCoderOptionPane.showConfirmDialog(null, "亲，确定要退出系统吗？\n退出前，记得要保存所有数据喔 (*^▽^*)", "系统提示", JOptionPane.YES_NO_OPTION);
                if (show == JOptionPane.YES_OPTION) {
                    System.exit(0);// 正常退出
                } else {
                    return;
                }
            }
        });
    }


}
