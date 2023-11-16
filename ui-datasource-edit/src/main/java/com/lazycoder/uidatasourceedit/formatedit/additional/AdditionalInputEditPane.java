package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.service.vo.save.AdditionalFunctionNameInputData;
import com.lazycoder.service.vo.save.AdditionalVariableInputData;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.AdditionalFormatControlPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import lombok.Getter;

public class AdditionalInputEditPane extends JPanel implements AdditionalPaneInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 8796634939292739650L;

    private MyButton addVarButton, delVarButton, addFuncitonButton, delFuncitionButton, restoreButton, clearBt;

    @Getter
    private AdditionalFormatControlPane additionalFormatControlPane;

    @Getter
    private AdditionalVariableTable additionalVariableTable;

    @Getter
    private AdditionalFuncitonTable additionalFuncitonTable;

    private JScrollPane additionalFormatControlScrollPane, additionalVariableTableScrollPane, additionalFuncitonTableScrollPane;

    private AdditionalFormatPane additionalFormatPane;

    private JLabel lblNewLabel;
    private JLabel label1;

    private OperatingTipButton additionalFormatOperatingTip, additionalVariableOperatingTip, additionalFunctionOperatingTip;

    /**
     * Create the panel.
     */
    public AdditionalInputEditPane(int additionalSerialNumber, AdditionalFormatPane additionalFormatPane) {
        setLayout(null);
        this.additionalFormatPane = additionalFormatPane;

        JLabel label = new JLabel("格式：");
        label.setBounds(30, 60, 72, 18);
        add(label);

        int width = (int) (SysUtil.SCREEN_SIZE.width * 0.27);

        additionalFormatControlPane = new AdditionalFormatControlPane(additionalSerialNumber);
        additionalFormatControlScrollPane = new JScrollPane(additionalFormatControlPane);
        additionalFormatControlPane.setUpdateScrollpane(additionalFormatControlScrollPane);
        additionalFormatControlScrollPane.setBounds(80, 50, width, 300);
        add(additionalFormatControlScrollPane);

        restoreButton = new MyButton("还原");
        restoreButton.addActionListener(actionListener);
        restoreButton.setBounds(100, 360, 80, 30);
        add(restoreButton);

        clearBt = new MyButton("清空");
        clearBt.setBounds(200, 360, 80, 30);
        add(clearBt);
        clearBt.addActionListener(actionListener);

        additionalFormatOperatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "可选模板" + File.separator + "可选格式控制面板")
                .getAbsolutePath());
        additionalFormatOperatingTip.setLocation(clearBt.getX() + clearBt.getWidth() + 30, clearBt.getY() + 5);
        add(additionalFormatOperatingTip);

        addVarButton = new MyButton("添加变量");
        addVarButton.addActionListener(actionListener);
        addVarButton.setBounds(80, 430, 150, 30);
        add(addVarButton);

        delVarButton = new MyButton("删除变量");
        delVarButton.addActionListener(actionListener);
        delVarButton.setBounds(270, 430, 150, 30);
        add(delVarButton);

        additionalVariableOperatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "原有变量")
                .getAbsolutePath());
        additionalVariableOperatingTip.setLocation(delVarButton.getX() + delVarButton.getWidth() + 30, delVarButton.getY() + 5);
        add(additionalVariableOperatingTip);

        lblNewLabel = new JLabel("变量：");
        lblNewLabel.setBounds(30, 490, 72, 18);
        add(lblNewLabel);

        additionalVariableTable = new AdditionalVariableTable(additionalSerialNumber);
        additionalVariableTableScrollPane = new JScrollPane(additionalVariableTable);
        additionalVariableTableScrollPane.setBounds(80, 480, width, 200);
        add(additionalVariableTableScrollPane);

        addFuncitonButton = new MyButton("添加方法");
        addFuncitonButton.addActionListener(actionListener);
        addFuncitonButton.setBounds(80, 760, 150, 30);
        add(addFuncitonButton);

        delFuncitionButton = new MyButton("删除方法");
        delFuncitionButton.addActionListener(actionListener);
        delFuncitionButton.setBounds(270, 760, 150, 30);
        add(delFuncitionButton);

        additionalFunctionOperatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "原有方法")
                .getAbsolutePath());
        additionalFunctionOperatingTip.setLocation(delFuncitionButton.getX() + delFuncitionButton.getWidth() + 30, delFuncitionButton.getY() + 5);
        add(additionalFunctionOperatingTip);

        label1 = new JLabel("方法：");
        label1.setBounds(30, 830, 72, 18);
        add(label1);

        additionalFuncitonTable = new AdditionalFuncitonTable(additionalSerialNumber);
        additionalFuncitonTableScrollPane = new JScrollPane(additionalFuncitonTable);
        additionalFuncitonTableScrollPane.setBounds(80, 820, width, 200);
        add(additionalFuncitonTableScrollPane);

        setPreferredSize(new Dimension((int) 0.343 * SysUtil.SCREEN_SIZE.width, 1080));
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addVarButton) {
                additionalVariableTable.addVariable();

            } else if (e.getSource() == delVarButton) {
                additionalVariableTable.delVariable();

            } else if (e.getSource() == addFuncitonButton) {
                additionalFuncitonTable.addFunctionName();

            } else if (e.getSource() == delFuncitionButton) {
                additionalFuncitonTable.delFunctionName();

            } else if (e.getSource() == restoreButton) {
                additionalFormatPane.restoreFormat();

            } else if (e.getSource() == clearBt) {
                additionalFormatPane.clear();
            }
        }
    };

    @Override
    public void displayAdditionalContent(AdditionalInfo additionalInfo, AdditionalMetaModel additionalMetaModel) {
        // TODO Auto-generated method stub
        additionalVariableTable.displayAdditionalContent(additionalInfo, additionalMetaModel);
        additionalFuncitonTable.displayAdditionalContent(additionalInfo, additionalMetaModel);
    }

    /**
     * 获取录入的可选模板的变量相关数据
     *
     * @return
     */
    public AdditionalVariableInputData getAdditionalVariableInputData() {
        return additionalVariableTable.getAdditionalVariableInputData();
    }

    /**
     * 获取录入的可选模板的方法名相关数据
     *
     * @return
     */
    public AdditionalFunctionNameInputData getAdditionalFunctionNameInputData() {
        return additionalFuncitonTable.getAdditionalFunctionNameInputData();
    }


    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (additionalVariableTable.check() == true) {
            if (additionalFuncitonTable.check() == false) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

}
