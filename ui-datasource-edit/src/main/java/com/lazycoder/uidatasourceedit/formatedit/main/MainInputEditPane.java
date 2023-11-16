package com.lazycoder.uidatasourceedit.formatedit.main;


import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.database.model.format.MainOperating;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.MainFormatControlPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainInputEditPane extends JPanel implements CheckInterface, MainPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = 3739500664155104429L;

    private MyButton addVarButton, delVarButton, addFuncitonButton, delFuncitionButton, restoreButton, clearBt;

    private MainFormatControlPane mainFormatControlPane;

    private MainVariableTable mainVariableTable;

    private MainFuncitonTable mainFuncitonTable;

    private JScrollPane mainFormatControlScrollPane, mainVariableTableScrollPane, mainFuncitonTableScrollPane;

    private MainFormatEditPane mainFormatEditPane;

    private JLabel lblNewLabel;
    private JLabel label_1;

    private OperatingTipButton mainFormatOperatingTip, mainVariableOperatingTip, mainFunctionOperatingTip;

    /**
     * Create the panel.
     */
    public MainInputEditPane(MainFormatEditPane mainFormatEditPane) {
        setLayout(null);
        this.mainFormatEditPane = mainFormatEditPane;

        JLabel label = new JLabel("格式：");
        label.setBounds(30, 60, 72, 18);
        add(label);

        int width = (int) (SysUtil.SCREEN_SIZE.width * 0.27);

        mainFormatControlPane = new MainFormatControlPane();
        mainFormatControlScrollPane = new JScrollPane(mainFormatControlPane);
        mainFormatControlPane.setUpdateScrollpane(mainFormatControlScrollPane);
        FormatEditPaneHolder.mainFormatControlPane = mainFormatControlPane;
        mainFormatControlScrollPane.setBounds(80, 50, width, 300);
        add(mainFormatControlScrollPane);

        restoreButton = new MyButton("还原");
        restoreButton.addActionListener(actionListener);
        restoreButton.setBounds(100, 360, 80, 30);
        add(restoreButton);

        clearBt = new MyButton("清空");
        clearBt.setBounds(200, 360, 80, 30);
        add(clearBt);
        clearBt.addActionListener(actionListener);

        mainFormatOperatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "必填模板" + File.separator + "格式"
                ).getAbsolutePath()
        );
        mainFormatOperatingTip.setLocation(clearBt.getX() + clearBt.getWidth() + 30, clearBt.getY() + 5);
        add(mainFormatOperatingTip);

        addVarButton = new MyButton("添加变量");
        addVarButton.addActionListener(actionListener);
        addVarButton.setBounds(80, 420, 150, 30);
        add(addVarButton);

        delVarButton = new MyButton("删除变量");
        delVarButton.addActionListener(actionListener);
        delVarButton.setBounds(270, 420, 150, 30);
        add(delVarButton);

        mainVariableOperatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "原有变量")
                .getAbsolutePath());
        mainVariableOperatingTip.setLocation(delVarButton.getX() + delVarButton.getWidth() + 30, delVarButton.getY() + 5);
        add(mainVariableOperatingTip);

        lblNewLabel = new JLabel("变量：");
        lblNewLabel.setBounds(30, 490, 72, 18);
        add(lblNewLabel);

        mainVariableTable = new MainVariableTable();
        mainVariableTableScrollPane = new JScrollPane(mainVariableTable);
        mainVariableTableScrollPane.setBounds(80, 480, width, 200);
        add(mainVariableTableScrollPane);

        addFuncitonButton = new MyButton("添加方法");
        addFuncitonButton.addActionListener(actionListener);
        addFuncitonButton.setBounds(80, 760, 150, 30);
        add(addFuncitonButton);

        delFuncitionButton = new MyButton("删除方法");
        delFuncitionButton.addActionListener(actionListener);
        delFuncitionButton.setBounds(270, 760, 150, 30);
        add(delFuncitionButton);

        mainFunctionOperatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "原有方法")
                .getAbsolutePath());
        mainFunctionOperatingTip.setLocation(delFuncitionButton.getX() + delFuncitionButton.getWidth() + 30, delFuncitionButton.getY() + 5);
        add(mainFunctionOperatingTip);

        label_1 = new JLabel("方法：");
        label_1.setBounds(30, 830, 72, 18);
        add(label_1);

        mainFuncitonTable = new MainFuncitonTable();
        mainFuncitonTableScrollPane = new JScrollPane(mainFuncitonTable);
        mainFuncitonTableScrollPane.setBounds(80, 820, width, 200);
        add(mainFuncitonTableScrollPane);

        setPreferredSize(new Dimension((int) 0.343 * SysUtil.SCREEN_SIZE.width, 1080));
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addVarButton) {
                mainVariableTable.addVariable();

            } else if (e.getSource() == delVarButton) {
                mainVariableTable.delVariable();

            } else if (e.getSource() == addFuncitonButton) {
                mainFuncitonTable.addFunctionName();

            } else if (e.getSource() == delFuncitionButton) {
                mainFuncitonTable.delFunctionName();

            } else if (e.getSource() == restoreButton) {
                restoreFormat();
            } else if (e.getSource() == clearBt) {
                clear();
            }
        }
    };

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (mainVariableTable.check() == true) {
            if (mainFuncitonTable.check() == false) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    private void clear() {
        if (ModuleEditPaneHolder.usingRange.checkHaveSelected(UsingObject.MAIN_USING_OBJECT)) {
            LazyCoderOptionPane.showMessageDialog(null,
                    "你在\"模块设置\"里正在编辑的\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"模块中，\n设置那个模块要使用必填模板的代码文件，直接清空可能会删了不该删的内容，\n" +
                            "真要清空的话，先别在那里选必填模板吧", "系统信息", JOptionPane.PLAIN_MESSAGE);
        } else {
            LazyCoderFormatControl.removeAllSubCodePaneList(MainFormatEditPane.formatModel);
            LazyCoderFormatControl.clear(MainFormatEditPane.formatModel);
            FormatEditPaneHolder.mainCodePane.clearAllSubCodeInputPane();
        }
    }

    private void restoreFormat() {
        MainInfo mainInfo = SysService.FORMAT_INFO_SERVICE.getMainInfo();
        if (mainInfo != null) {
            mainFormatEditPane.restoreFormat(mainInfo);
        }
    }

    public MainFormatControlPane getMainFormatControlPane() {
        return mainFormatControlPane;
    }

    public void setMainInfoRelatedParam(MainInfo mainInfo) {
        mainInfo.setFunctionNameNum(mainFuncitonTable.getFunctionNameNum());
        mainInfo.setNumOfVariable(mainVariableTable.getVariableNum());
    }


    /**
     * 获取必填模板格式录入数据操作层的模型
     *
     * @return
     */
    public MainOperating getMainOperating() {
        return mainFormatControlPane.getMainOperating();
    }

    /**
     * 获取录入的必填模板的变量
     *
     * @return
     */
    public ArrayList<VariableData> getVariableDataList() {
        return mainVariableTable.getVariableDataList();
    }

    /**
     * 获取录入的必填模板的方法名
     *
     * @return
     */
    public ArrayList<FunctionNameData> getFunctionNameDataList() {
        return mainFuncitonTable.getFunctionNameDataList();
    }

    @Override
    public void displayMainContent(MainInfo mainInfo) {
        // TODO Auto-generated method stub
        mainVariableTable.displayMainContent(mainInfo);
        mainFuncitonTable.displayMainContent(mainInfo);
    }

}
