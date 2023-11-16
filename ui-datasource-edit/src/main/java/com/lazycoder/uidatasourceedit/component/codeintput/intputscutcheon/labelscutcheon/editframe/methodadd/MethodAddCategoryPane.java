package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.methodadd;

import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MethodAddCategoryPane extends JScrollPane implements CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = -5285885341617620141L;

//	private int isNeedBottom = 0;

    private JPanel panel;

    private MyButton addButton, delButton;

    private JScrollPane scrollPane;

    private Box verticalBox;

//    private int currentNum = 0;

    private FunctionAddControl controlElement;

    private FunctionAddParam functionAddParam;

    private MethodAddCategoryPane() {
        panel = new JPanel();
        setViewportView(panel);
        panel.setLayout(new BorderLayout(0, 0));

        toolbarInit();

        verticalBox = Box.createVerticalBox();
        scrollPane = new JScrollPane(verticalBox);
        Dimension dd = new Dimension((int) (SysUtil.SCREEN_SIZE.getWidth() * 0.285),
                (int) (SysUtil.SCREEN_SIZE.getHeight() * 0.36));
        scrollPane.setPreferredSize(dd);
        scrollPane.setMinimumSize(dd);
        scrollPane.setMaximumSize(dd);

        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public MethodAddCategoryPane(FunctionAddParam functionAddParam) {
        this();
        this.functionAddParam = functionAddParam;
    }

    /**
     * 还原内容
     *
     * @param list
     */
    public void reductionOfTheContent(ArrayList<MethodAddStorageFormat> list) {
        MethodAddEditMetaPane methodAddEditMetaPane;
        int i = 0;
        for (MethodAddStorageFormat methodAddStorageFormat : list) {
            i++;
            methodAddStorageFormat.setOrdinal(i);
            methodAddEditMetaPane = new MethodAddEditMetaPane(methodAddStorageFormat, controlElement, this.functionAddParam);
            verticalBox.add(methodAddEditMetaPane);
        }
        //currentNum = list.size();
    }

    private void toolbarInit() {
        MyToolBar toolBar = new MyToolBar();
        panel.add(toolBar, BorderLayout.NORTH);

        addButton = new MyButton("添加");
        toolBar.add(addButton);
        addButton.addActionListener(listener);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut);

        delButton = new MyButton("删除");
        toolBar.add(delButton);
        delButton.addActionListener(listener);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addButton) {
                addFunction();
            } else if (e.getSource() == delButton) {
                delFunction();
            }
        }
    };

    private void addFunction() {
        int currentNum = verticalBox.getComponentCount()+1;
        MethodAddEditMetaPane methodAddEditMetaPane = new MethodAddEditMetaPane(currentNum, controlElement, functionAddParam);
        verticalBox.add(methodAddEditMetaPane);

        scrollPane.updateUI();
        scrollPane.repaint();
        this.updateUI();
        this.repaint();
    }

    @Override
    public boolean check() {
        boolean flag = true;
        MethodAddEditMetaPane temp1, temp2;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            temp1 = (MethodAddEditMetaPane) verticalBox.getComponent(i);
            if (temp1.check() == false) {
                flag = false;
                break;
            }

            for (int k = i + 1; k < verticalBox.getComponentCount(); k++) {
                temp2 = (MethodAddEditMetaPane) verticalBox.getComponent(k);
                if (temp1.getShowText().trim().equals(temp2.getShowText().trim())) {
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   第" + (i + 1) + "个内部功能和第" + (k + 1) + "个内部功能的名称一样，这样我没法区分", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);
                    flag = false;
                    break;
                }
            }
            if (flag == false) {
                break;
            }
        }
        return flag;
    }

    private void delFunction() {
        if (verticalBox.getComponentCount() > 0) {
            //currentNum--;
            verticalBox.remove(verticalBox.getComponentCount() - 1);
            scrollPane.updateUI();
            scrollPane.repaint();
            this.updateUI();
            this.repaint();
        }
    }

    /**
     * 编辑面板
     *
     * @return
     */
    public ArrayList<MethodAddStorageFormat> getMethodAddStorageFormatList() {
        ArrayList<MethodAddStorageFormat> list = new ArrayList<>();
        MethodAddEditMetaPane tempPane;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            tempPane = (MethodAddEditMetaPane) (verticalBox.getComponent(i));
            list.add(tempPane.getOperatingModel());
        }
        return list;
    }

    /**
     * 删除组件
     */
    public void deleteComponent() {
        MethodAddEditMetaPane tempPane;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            tempPane = (MethodAddEditMetaPane) (verticalBox.getComponent(i));
            tempPane.deleteComponent();
        }
    }

    public FunctionAddControl getControlElement() {
        return controlElement;
    }

    public void setControlElement(FunctionAddControl controlElement) {
        this.controlElement = controlElement;
    }

    public int getInternalMethodsNum() {
        return verticalBox.getComponentCount();
    }


}
