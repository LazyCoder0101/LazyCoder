package com.lazycoder.uidatasourceedit.formatedit.main.settype;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.save.MainSetInputData;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.EditContainerPane;
import com.lazycoder.uidatasourceedit.formatedit.main.MainPaneInterface;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

public class MainSetCodeEditPane extends JPanel implements CheckInterface, MainPaneInterface, EditContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = -7412530082047240398L;

    private Border defaultborder;

    /**
     * Create the panel.
     */
    private JTabbedPane tabbedPane;

    private MyButton addButton, delButton, restoreButton;

    private OperatingTipButton operatingTipButton;

    private int currentNum = 0;

    public MainSetCodeEditPane() {
        setLayout(new BorderLayout(0, 0));

        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        addButton = new MyButton("添加");
        toolBar.add(addButton);
        addButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));

        delButton = new MyButton("删除");
        toolBar.add(delButton);
        delButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));

        restoreButton = new MyButton("还原");
        toolBar.add(restoreButton);
        restoreButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));

        operatingTipButton = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑"
                                + File.separator + "格式设置" + File.separator + "必填模板" + File.separator + "必填模板设置")
                .getAbsolutePath());
        toolBar.add(operatingTipButton);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//        tabbedPane.setBorder(BorderFactory.createEmptyBorder());
        add(tabbedPane, BorderLayout.CENTER);
        defaultborder = tabbedPane.getBorder();

    }

    private void restore() {
        MainInfo mainInfo = SysService.FORMAT_INFO_SERVICE.getMainInfo();
        if (mainInfo != null) {
            currentNum = 0;
            tabbedPane.removeAll();
            displayMainContent(mainInfo);
        }
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            if (arg0.getSource() == addButton) {
                addCategory();
            } else if (arg0.getSource() == delButton) {
                delCategory();
            } else if (arg0.getSource() == restoreButton) {
                restore();
            }
        }
    };

    @Override
    public void displayMainContent(MainInfo mainInfo) {
        // TODO Auto-generated method stub
        if (mainInfo.getNumOfSetCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
            currentNum = 0;
            MainSetCategoryEditPane mainSetCategoryEditPaneTemp;
            ArrayList<String> list = SysService.FORMAT_INFO_SERVICE.getSetTypeListParam(mainInfo);
            if (list != null) {
                for (int a = 0; a < list.size(); a++) {
                    currentNum++;
                    mainSetCategoryEditPaneTemp = new MainSetCategoryEditPane(currentNum);
                    mainSetCategoryEditPaneTemp.displayMainSetContent(list.get(a));
                    tabbedPane.addTab("设置" + currentNum, mainSetCategoryEditPaneTemp);
                }
            }
            //       } else if (mainInfo.getNumOfSetCodeType() == 0) {// 此前没添加过内容

        }
    }

    public MainSetInputData getMainSetInputData() {
        ArrayList<FormatTypeOperatingModel> operatingList = new ArrayList<>(), operatingTempList;
        ArrayList<FormatTypeCodeModel> codeList = new ArrayList<>(), codeTempList;
        MainSetCategoryEditPane paneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            paneTemp = (MainSetCategoryEditPane) tabbedPane.getComponent(i);

            operatingTempList = paneTemp.getOperationParamList();
            operatingList.addAll(operatingTempList);

            codeTempList = paneTemp.getCodeParamList();
            codeList.addAll(codeTempList);
        }
        MainSetInputData mainSetInputData = new MainSetInputData();
        mainSetInputData.setOperatingList(operatingList);
        mainSetInputData.setCodeList(codeList);
        return mainSetInputData;
    }

    private void addCategory() {
        currentNum++;
        MainSetCategoryEditPane mainSetCodeEditPane = new MainSetCategoryEditPane(currentNum);
        tabbedPane.addTab("设置" + currentNum, mainSetCodeEditPane);
    }

    private void delCategory() {
        if (tabbedPane.getComponentCount() > 0) {
            currentNum--;
            tabbedPane.remove(tabbedPane.getComponent(tabbedPane.getComponentCount() - 1));
        }
    }

    public String getMainSetTypeName(int typeSerialNumber) {
        String mainSetTypeName = "";
        MainSetCategoryEditPane mainSetCategoryEditPaneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            mainSetCategoryEditPaneTemp = (MainSetCategoryEditPane) tabbedPane.getComponent(i);
            if (mainSetCategoryEditPaneTemp.getTypeSerialNumber() == typeSerialNumber) {
                mainSetTypeName = mainSetCategoryEditPaneTemp.getMainSetTypeName();
            }
        }
        return mainSetTypeName;
    }

    /**
     * 获取模块类型列表
     *
     * @return
     */
    private ArrayList<String> getMainSetTypeList() {
        ArrayList<String> list = new ArrayList<>();
        MainSetCategoryEditPane mainSetCategoryEditPaneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            mainSetCategoryEditPaneTemp = (MainSetCategoryEditPane) tabbedPane.getComponent(i);
            list.add(mainSetCategoryEditPaneTemp.getMainSetTypeName());
        }
        return list;
    }

    /**
     * 设置模块设置的相关参数
     *
     * @param mainInfo
     */
    public void setMainInfoRelatedParam(MainInfo mainInfo) {
        mainInfo.setNumOfSetCodeType(getMainSetNum());// 记录必填模板设置代码的分类数量
        mainInfo.setSetTheTypeOfSetCodeParam(JsonUtil.getJsonStr(getMainSetTypeList()));
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (tabbedPane.getComponentCount() > 0) {
            if (checkMainSetCategoryEditPane() == true) {
                MainSetCategoryEditPane mainSetCategoryEditPaneTemp;
                for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                    mainSetCategoryEditPaneTemp = (MainSetCategoryEditPane) tabbedPane.getComponent(i);
                    if (mainSetCategoryEditPaneTemp.check() == false) {
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

    private boolean checkMainSetCategoryEditPane() {
        boolean flag = true;
        String typeName1, typeName2;
        MainSetCategoryEditPane paneTemp1, paneTemp2;
        if (tabbedPane.getComponentCount() > 0) {
            for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                paneTemp1 = (MainSetCategoryEditPane) tabbedPane.getComponent(i);
                typeName1 = paneTemp1.getMainSetTypeName();
                for (int a = i + 1; a < tabbedPane.getComponentCount(); a++) {
                    paneTemp2 = (MainSetCategoryEditPane) tabbedPane.getComponent(a);
                    typeName2 = paneTemp2.getMainSetTypeName();
                    if (typeName1.equals(typeName2)) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null,
                                "第" + (i + 1) + "个必填模板设置面板和第" + (a + 1) + "个必填模板设置面板分类名一样，\n请检查清楚录入数据无误后再保存", "系统信息",
                                JOptionPane.PLAIN_MESSAGE);
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

    /**
     * 获取设置总类数量
     *
     * @return
     */
    private int getMainSetNum() {
        return tabbedPane.getComponentCount();
    }

    /**
     * 获取该分类的方法总数
     *
     * @param opreatingSerial
     */
    public int getMainSetOpreatingNum(int opreatingSerial) {
        int mainSetOpreatingNum = MarkElementName.MARK_NULL;
        if (opreatingSerial > 0) {
            MainSetCategoryEditPane mainSetCategoryEditPaneTemp;

            for (int a = 0; a < tabbedPane.getComponentCount(); a++) {
                mainSetCategoryEditPaneTemp = (MainSetCategoryEditPane) tabbedPane.getComponent(a);
                if (mainSetCategoryEditPaneTemp.getTypeSerialNumber() == opreatingSerial) {
                    mainSetOpreatingNum = mainSetCategoryEditPaneTemp.getMainSetOpreatingNum();
                    break;
                }
            }

        }
        return mainSetOpreatingNum;

    }

    /**
     * 获取该分类的某个方法的代码数
     *
     * @param classificationSerial
     * @param operatingOrdinalNumber
     * @return
     */
    public int getMainSetCodeNum(int classificationSerial, int operatingOrdinalNumber) {
        int mainSetCodeNum = MarkElementName.MARK_NULL;
        if (operatingOrdinalNumber > 0) {
            MainSetCategoryEditPane mainSetCategoryEditPaneTemp;
            for (int r = 0; r < tabbedPane.getComponentCount(); r++) {
                mainSetCategoryEditPaneTemp = (MainSetCategoryEditPane) tabbedPane.getComponent(r);
                if (mainSetCategoryEditPaneTemp.getTypeSerialNumber() == classificationSerial) {
                    mainSetCodeNum = mainSetCategoryEditPaneTemp.getMainSetCodeNum(operatingOrdinalNumber);
                    break;
                }
            }
        }
        return mainSetCodeNum;
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        MainSetCategoryEditPane paneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            paneTemp = (MainSetCategoryEditPane) tabbedPane.getComponent(i);
            list.addAll(paneTemp.getAllEditContainerModel());
        }
        return list;
    }

    @Override
    public void setUIResponse(boolean status) {
        if (status) {
            tabbedPane.setBorder(DataSourceEditHolder.RESPONSE_TRUE_BORDER);
            setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
        } else {
            tabbedPane.setBorder(defaultborder);
            setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
        }
    }

}
