package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleset;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ModuleSetCategoryEditPane extends JScrollPane implements CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 2061190693648288695L;

    private JPanel panel;

    private JTextField textField;

    private MyButton addButton, delButton;

    private JScrollPane scrollPane;

    private Box verticalBox;

    /**
     * 该分类面板的序号
     */
    private int typeSerialNumber = 0;

    /**
     * 当前添加方法数量
     */
    private int currentNum = 0;

    private ModuleSetCategoryEditPane() {
        setBorder(null);// 无边框，更贴合容器
//		jsp.getVerticalScrollBar().setUI(new MyScrollBarUI());
        panel = new JPanel();
        setViewportView(panel);
        panel.setLayout(new BorderLayout(0, 0));

        toolbarInit();

        verticalBox = Box.createVerticalBox();
        scrollPane = new JScrollPane(verticalBox);// 0.59
        Dimension dd = new Dimension((int) (SysUtil.SCREEN_SIZE.getWidth() * 0.297),
                (int) (SysUtil.SCREEN_SIZE.getHeight() * 0.59));
        scrollPane.setPreferredSize(dd);
        scrollPane.setMinimumSize(dd);
        scrollPane.setMaximumSize(dd);
        scrollPane.setBorder(null);// 无边框，更贴合容器
//		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
        panel.add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * 新建
     *
     * @param typeSerialNumber
     */
    public ModuleSetCategoryEditPane(int typeSerialNumber) {
        this();
        this.typeSerialNumber = typeSerialNumber;
    }

    private void toolbarInit() {
        MyToolBar toolBar = new MyToolBar();
        panel.add(toolBar, BorderLayout.NORTH);

        JLabel label = new JLabel("设置分类名：");
        toolBar.add(label);
        textField = new JTextField();
        toolBar.add(textField);
        Dimension dimension = new Dimension(230, 30);
        textField.setPreferredSize(dimension);
        textField.setMaximumSize(dimension);
        textField.setMinimumSize(dimension);

        Component horizontalStrut = Box.createHorizontalStrut(35);
        toolBar.add(horizontalStrut);

        addButton = new MyButton("添加");
        toolBar.add(addButton);
        addButton.addActionListener(listener);

        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut_1);

        delButton = new MyButton("删除");
        toolBar.add(delButton);
        delButton.addActionListener(listener);
    }


    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addButton) {
                addSetFunction();
                SysUtil.updateFrameUI(ModuleSetCategoryEditPane.this);
            } else if (e.getSource() == delButton) {
                delSetFunction();
                SysUtil.updateFrameUI(ModuleSetCategoryEditPane.this);
            }
        }
    };

    /**
     * 显示该模块该分类的内容
     *
     * @param typeName
     */
    public void displayModuleContent(String typeName) {
        textField.setText(typeName);
        currentNum = 0;
        ArrayList<ModuleSetMetaModel> list = SysService.MODULE_SET_SERVICE.getModuleSetMetaModelList(DataSourceEditHolder.currentModule.getModuleId(), typeName);
        if (list != null) {
            ModuleSetEditMetaPane moduleSetEditMetaPane;
            for (ModuleSetMetaModel metaModel: list) {
                currentNum++;
                moduleSetEditMetaPane = new ModuleSetEditMetaPane(metaModel);
                verticalBox.add(moduleSetEditMetaPane);

            }
        }

        this.updateUI();
        this.repaint();
    }

    /**
     * 获取操作参数列表
     */
    public ArrayList<ModuleSetOperatingModel> getOperationParamList() {
        ModuleSetEditMetaPane temp;
        ModuleSetOperatingModel tempModel;
        ArrayList<ModuleSetOperatingModel> list = new ArrayList<>();
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            temp = (ModuleSetEditMetaPane) verticalBox.getComponent(i);
            tempModel = temp.getOperatingModel();
            tempModel.setTypeName(textField.getText());
            list.add(tempModel);
        }
        return list;
    }

    public ArrayList<ContainerModel> getAllEditContainerModel() {
        ArrayList<ContainerModel> list = new ArrayList<ContainerModel>();
        ModuleSetEditMetaPane temp;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            temp = (ModuleSetEditMetaPane) verticalBox.getComponent(i);
            list.add(temp.getContainerModel());
        }
        return list;
    }

    /**
     * 获取代码参数列表
     */
    public ArrayList<ModuleSetCodeModel> getCodeParamList() {
        ModuleSetEditMetaPane temp;
        ModuleSetCodeModel tempModel;
        String typeName = textField.getText();
        ArrayList<ModuleSetCodeModel> list = new ArrayList<>(), tempList;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            temp = (ModuleSetEditMetaPane) verticalBox.getComponent(i);
            tempList = temp.getCodeModelList();
            for (int a = 0; a < tempList.size(); a++) {
                tempModel = tempList.get(a);
                tempModel.setTypeName(typeName);
                tempModel.setTypeSerialNumber(this.typeSerialNumber);
                list.add(tempModel);
            }
        }
        return list;
    }

    private void addSetFunction() {
        currentNum++;
        ModuleSetEditMetaPane moduleSetEditMetaPane = new ModuleSetEditMetaPane(typeSerialNumber, currentNum);
        verticalBox.add(moduleSetEditMetaPane);

        this.updateUI();
        this.repaint();
    }

    private void delSetFunction() {
        if (verticalBox.getComponentCount() > 0) {
            currentNum--;
            verticalBox.remove(verticalBox.getComponentCount() - 1);
            this.updateUI();
            this.repaint();
        }
    }

    /**
     * 获取该分类的方法数
     *
     * @return
     */
    public int getModuleSetOpreatingNum() {
        return verticalBox.getComponentCount();
    }

    /**
     * 获取该分类的某个方法的代码数
     */
    public int getModuleSetCodeNum(int opreatingSerial) {
        int moduleSetCodeNum = MarkElementName.MARK_NULL;
        ModuleSetEditMetaPane teMetaPane;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            teMetaPane = (ModuleSetEditMetaPane) verticalBox.getComponent(i);
            if (teMetaPane.getOrdinal() == opreatingSerial) {
                moduleSetCodeNum = teMetaPane.getModuleSetCodeNum();
                break;
            }
        }
        return moduleSetCodeNum;
    }

    /**
     * 获取模块类型名
     *
     * @return
     */
    public String getTheModuleSetTypeName() {
        return textField.getText().trim();
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if ("".equals(textField.getText().trim())) {
            LazyCoderOptionPane.showMessageDialog(null, "模块设置里第" + typeSerialNumber + "个没写分类名", "系统信息", JOptionPane.PLAIN_MESSAGE);
            flag = false;
        } else {
            if (checkSameNameFromMetaPane() == true) {
                ModuleSetEditMetaPane teMetaPane1;
                for (int i = 0; i < verticalBox.getComponentCount(); i++) {
                    teMetaPane1 = (ModuleSetEditMetaPane) verticalBox.getComponent(i);
                    if (teMetaPane1.check() == false) {
                        flag = false;
                        break;
                    }

                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    private boolean checkSameNameFromMetaPane() {
        boolean flag = true;
        String showText1, showText2;
        ModuleSetEditMetaPane paneTemp1, paneTemp2;
        if (verticalBox.getComponentCount() > 0) {
            for (int i = 0; i < verticalBox.getComponentCount(); i++) {
                paneTemp1 = (ModuleSetEditMetaPane) verticalBox.getComponent(i);
                showText1 = paneTemp1.getShowText();
                for (int a = i + 1; a < verticalBox.getComponentCount(); a++) {
                    paneTemp2 = (ModuleSetEditMetaPane) verticalBox.getComponent(a);
                    showText2 = paneTemp2.getShowText();
                    if (showText1.equals(showText2)) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null, "模块设置里\"" + textField.getText() + "\"分类中," + "第" + (i + 1)
                                + "个方法和第" + (a + 1) + "个方法显示名称一样，\n请检查清楚录入数据无误后再保存", "系统信息", JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                }
                if (flag == false) {
                    break;
                }
            }
        }
        return flag;
    }

    public int getTypeSerialNumber() {
        return typeSerialNumber;
    }

}
