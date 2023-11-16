package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractFunctionControlInputPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.FunctionNameTextField;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.HiddenControlButton;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipMenuItem;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.JsonUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class AbstractControlCabinet extends Folder implements CheckInterface {
    /**
     *
     */
    private static final long serialVersionUID = 6499337550092001417L;

    private static final int FUNCTION_NAME_TEXT_FIELD_HEIGHT = 30;
    //HIDDEN_BUTTON_HEIGHT = 15;

    private JLabel ordinalLabel = null;//显示序号的标签

    protected int operatingOrdinalNumber = 0;
    /**
     * 方法名称输入
     */
    protected FunctionNameTextField functionNameTextField = null;

    @Setter
    @Getter
    protected AbstractFunctionControlInputPane derectInputPanel = null, // 默认输入框
            hiddenInputPane = null;// 隐藏输入框
    /**
     * 隐藏按钮
     */
    protected HiddenControlButton hiddenButton;

    protected JScrollPane derectInputPanelJsp, hiddenInputPaneJsp;

    protected MyButton moreButton;

    protected JPopupMenu popupMenu;

    private JMenu opMenu;

    protected JMenuItem restoreMenuItem, clearMenuItem;

    private OperatingTipMenuItem operatingTipMenuItem;

    private static ImageIcon moreIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
            + "base_code_content_edit" + File.separator + "code_input_pane" + File.separator + "更多.png");

//    private ContainerModel theModel;

    /**
     * 一般使用构造函数
     */
    public void init(ContainerModel theModel, int operatingOrdinalNumber, boolean expanded, double proportion) {
        // TODO Auto-generated constructor stub
        uiInit(expanded, proportion);
        this.operatingOrdinalNumber = operatingOrdinalNumber;

        ordinalLabel.setText("（" + operatingOrdinalNumber + "）");
        derectInputPanel.setModel(theModel);
        theModel.setMainFunctionControl(derectInputPanel);

        hiddenInputPane.setModel(theModel);
        theModel.setHiddenFunctionControl(hiddenInputPane);
    }


    private ActionListener menuItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == restoreMenuItem) {
                restoreContainer();
            } else if (e.getSource() == clearMenuItem) {
                clearContainer();
            }
        }
    };


    private MouseAdapter thisMouseAdapter = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            // 进入时字体变蓝，弹出窗口
            popupMenu.show(moreButton, e.getX(), e.getY());
        }
    };

    public void paneStyteInit(boolean showTip) {
        if (showTip) {
            functionNameTextField.run();
        }
        String derectTip = "这里填写功能的主要内容（必填）";
        int hiddenTextFontSize = 14;
        Color hiddenTextColor = new Color(121, 121, 121);

        derectInputPanel.setToolTipText(derectTip);

        String hiddenTip = "这里填写功能次要内容（平时隐藏不见的内容，非必填）";
        hiddenInputPane.setToolTipText(hiddenTip);
        hiddenInputPane.setTheStyle(hiddenTextColor, hiddenTextFontSize);
    }

    /**
     * 测试用
     *
     * @param expanded
     */
//    public AbstractControlCabinet(boolean expanded, double proportion) {
//        // TODO Auto-generated constructor stub
//        init(expanded, proportion);
//    }

    /**
     * 还原内容
     *
     * @param generalOperatingModel
     */
    public void reductionContent(GeneralCommandOperatingModel generalOperatingModel) {
        functionNameTextField.setText(generalOperatingModel.getShowText());

        ArrayList<BaseElementInterface> defaultPaneElementList = DeserializeElementMethods.getControlPaneElmentList(generalOperatingModel.getDefaultControlStatementFormat());
        derectInputPanel.reductionContent(defaultPaneElementList);

        ArrayList<BaseElementInterface> hiddenPaneElementList = DeserializeElementMethods.getControlPaneElmentList(generalOperatingModel.getHiddenControlStatementFormat());
        hiddenInputPane.reductionContent(hiddenPaneElementList);
//		derect_input_panel.reductionContent(generalOperatingModel.getDefaultControlStatementFormat());
//		hidden_input_pane.reductionContent(generalOperatingModel.getHiddenControlStatementFormat());
        if (BaseModel.FALSE_ == generalOperatingModel.getHiddenState()) {
            packUpHiddenPanel();
        }
    }

    /**
     * ui初始化
     *
     * @param expanded
     * @param proportion
     */
    public void uiInit(boolean expanded, double proportion) {
        // 设置自己的layout
        setLayout(new UseControlFolderTabLayout());

        int width = (int) (proportion * SysUtil.SCREEN_SIZE.getWidth());
        // int width = 200;

        ordinalLabel = new JLabel();
        add(ordinalLabel);
        functionNameTextField = new FunctionNameTextField();
        add(functionNameTextField);

        derectInputPanelJsp = new JScrollPane(derectInputPanel);
        derectInputPanel.setUpdateScrollpane(derectInputPanelJsp);
        derectInputPanelJsp.setSize(width, 80);
        add(derectInputPanelJsp);

        popupMenu = new JPopupMenu();
        opMenu = new JMenu("操作");
        popupMenu.add(opMenu);
        restoreMenuItem = new JMenuItem("还原");
        restoreMenuItem.addActionListener(menuItemActionListener);
        clearMenuItem = new JMenuItem("清空");
        clearMenuItem.addActionListener(menuItemActionListener);
        opMenu.add(restoreMenuItem);
        opMenu.addSeparator();
        opMenu.addSeparator();
        opMenu.add(clearMenuItem);
        opMenu.addSeparator();
        operatingTipMenuItem = new OperatingTipMenuItem(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "功能录入提示")
                .getAbsolutePath());
        opMenu.add(operatingTipMenuItem);

        moreButton = new MyButton(moreIcon);
        moreButton.setContentAreaFilled(false);
        moreButton.addMouseListener(thisMouseAdapter);
        add(moreButton);

        hiddenButton = new HiddenControlButton(true);
        this.setHiddenButton(hiddenButton);
        add(hiddenButton);

        hiddenInputPaneJsp = new JScrollPane(hiddenInputPane);
        hiddenInputPane.setUpdateScrollpane(hiddenInputPaneJsp);
        hiddenInputPaneJsp.setSize(width, 100);

        // 生成并添加抽屉
        Drawer drawer = new Drawer(expanded ? 1 : 0, hiddenInputPaneJsp);
        this.setDrawer(drawer);
        add(drawer);
    }

    /**
     * 获取源码显示描述
     *
     * @return
     */
    public String getShowText() {
        return functionNameTextField.getText();
    }

    /**
     * 获取默认控制语句格式
     *
     * @return
     */
    public String getDefaultControlStatementFormat() {
        String out = "";
        try {
            out = derectInputPanel.getControlStatementFormat();
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 获取隐藏控制语句格式
     *
     * @return
     */
    public String getHiddenControlStatementFormat() {
        String out = "";
        try {
            out = hiddenInputPane.getControlStatementFormat();
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 获取隐藏状态
     *
     * @return
     */
    public int getHiddenState() {
        int hiddenState = GeneralCommandOperatingModel.TRUE_;
        if (hiddenInputPane.getUseState() == false) {
            hiddenState = GeneralCommandOperatingModel.FALSE_;
        }
        return hiddenState;
    }

    public String getNoteListParam() {
        ArrayList<String> noteList = new ArrayList<>();
        if (derectInputPanel != null) {
            noteList.addAll(derectInputPanel.getUsageReminderList());
        }
        if (hiddenInputPane != null) {
            noteList.addAll(hiddenInputPane.getUsageReminderList());
        }
        return JsonUtil.getJsonStr(noteList);
    }

    /**
     * 清空
     */
    public void clear() {
        functionNameTextField.clear();
        derectInputPanel.clear();
        hiddenInputPane.clear();
    }

    protected void setDeafaultLayout() {
        setLayout(new UseControlFolderTabLayout());
    }

    /**
     * 获得该面板目前所需的空间大小：drawer+caption_height
     *
     * @return
     */
    @Override
    public Dimension getRequiredDimension() {
        // 高度是抽屉的高度加上标题的高度，抽屉的高度是目前抽出的长度
        Drawer drawerTemp = getDrawer();
//        int hiddenBtHeight = 0;
//        if (getHiddenButton() != null) {
//            hiddenBtHeight = getHiddenButton().getHeight();
//        }
        int w = drawerTemp.getContentWidth(), dHeight = functionNameTextField.getHeight()
                + derectInputPanelJsp.getHeight();

        // 高度是抽屉的高度加上标题的高度，抽屉的高度是目前抽出的长度
        int h = (int) (drawerTemp.getContentHeight() * drawerTemp.getRatio()) + dHeight;
        return new Dimension(w, h);
    }

    @Override
    public int getRequiredHeight() {
        Drawer drawerTemp = getDrawer();
//        int hiddenBtHeight = 0;
//        if (getHiddenButton() != null) {
//            hiddenBtHeight = getHiddenButton().getHeight();
//        }
        int dHeight = functionNameTextField.getHeight() + derectInputPanelJsp.getHeight();
        return (int) (drawerTemp.getContentHeight() * drawerTemp.getRatio()) + dHeight;
    }

    /**
     * 负责布局面板组件
     */
    class UseControlFolderTabLayout implements LayoutManager {

        /**
         * x坐标
         */
        private int xCoordinates = 5;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return parent.getPreferredSize();
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return parent.getMinimumSize();
        }

        @Override
        public void layoutContainer(Container parent) {
            int w = parent.getWidth(), h = parent.getHeight(), dHeight = FUNCTION_NAME_TEXT_FIELD_HEIGHT
                    + derectInputPanelJsp.getHeight();

            ordinalLabel.setBounds(0, 0, 45, FUNCTION_NAME_TEXT_FIELD_HEIGHT);
            functionNameTextField.setBounds(45, 0, w - 45, FUNCTION_NAME_TEXT_FIELD_HEIGHT);
            derectInputPanelJsp.setBounds(xCoordinates, FUNCTION_NAME_TEXT_FIELD_HEIGHT, w - moreIcon.getIconWidth() - xCoordinates,
                    derectInputPanelJsp.getHeight());
            moreButton.setBounds(w - moreIcon.getIconWidth(), FUNCTION_NAME_TEXT_FIELD_HEIGHT, moreIcon.getIconWidth(), 40);
            if (getHiddenButton() != null) {
//                getHiddenButton().setBounds(x_coordinates,
//                        FUNCTION_NAME_TEXT_FIELD_HEIGHT + derectInputPanelJsp.getHeight(), w, HIDDEN_BUTTON_HEIGHT);
                getHiddenButton().setBounds(w - moreIcon.getIconWidth(),
                        FUNCTION_NAME_TEXT_FIELD_HEIGHT + moreIcon.getIconHeight(),
                        moreIcon.getIconWidth(), derectInputPanelJsp.getHeight() - 40);

            }
            // 抽屉只显示抽出的比例
            getDrawer().setBounds(xCoordinates, dHeight, w, h - dHeight);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color oridinalColor = g.getColor();
        g.setColor(Color.red);
        g.drawString("*", functionNameTextField.getX() - 10, functionNameTextField.getY() + 20);
        g.drawString("*", derectInputPanelJsp.getX() - 6, derectInputPanelJsp.getY() + 30);
        g.setColor(oridinalColor);
    }

    /**
     * 还原整个功能的内容
     */
    public void restoreContainer() {
    }


    /**
     * 清空整个功能的内容
     */
    public void clearContainer() {
    }

}
