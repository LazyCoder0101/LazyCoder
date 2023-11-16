package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.EditContainerPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AdditionalFunctionPane extends JPanel implements CheckInterface, EditContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = 3336101546309623068L;

    private MyButton addBt=null, delBt, restoreButton;
    private Box verticalBox;
    private JScrollPane scrollPane;

    private MyToolBar toolBar = null;

    private OperatingTipButton operatingTip;

    /**
     * 可选模板业务功能的标识编号
     */
    private int additionalSerialNumber = 0;

    /**
     * Create the panel.
     */
    private AdditionalFunctionPane() {
        setLayout(new BorderLayout(0, 0));

        toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        addBt = new MyButton("添加功能");
        addBt.addActionListener(listener);
//		addBt.setToolTipText("添加的可选模板业务功能一般会写在默认代码文件的\"功能\"标记");
        toolBar.add(addBt);
        toolBar.add(Box.createHorizontalStrut(20));

        delBt = new MyButton("删除功能");
        delBt.addActionListener(listener);
        toolBar.add(delBt);
        toolBar.add(Box.createHorizontalStrut(20));

        restoreButton = new MyButton("还原");
        toolBar.add(restoreButton);
        toolBar.add(Box.createHorizontalStrut(20));
        restoreButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(20));
        operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "可选模板" + File.separator + "可选功能")
                .getAbsolutePath());
        toolBar.add(operatingTip);

        verticalBox = Box.createVerticalBox();
        scrollPane = new JScrollPane(verticalBox);
        add(scrollPane, BorderLayout.CENTER);
    }

    public AdditionalFunctionPane(int additionalSerialNumber) {
        this();
        this.additionalSerialNumber = additionalSerialNumber;
    }

    private void restore() {
        AdditionalInfo additionalInfo = SysService.FORMAT_INFO_SERVICE.getAdditionalInfo(additionalSerialNumber);
        if (additionalInfo == null) {
            LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
        } else if (additionalInfo != null) {
            if (additionalInfo.getNumOfFunctionCodeType() > 0) {
                verticalBox.removeAll();
                displayAdditionalContent(additionalInfo);
            } else {
                LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
            }
        }
    }

    public int getNumOfAdditionalFunction() {
        return verticalBox.getComponentCount();
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addBt) {
                AdditionalFunctionEditMetaPane additionalFunctionEditMetaPane = new AdditionalFunctionEditMetaPane(
                        additionalSerialNumber, verticalBox.getComponentCount() + 1);
                verticalBox.add(additionalFunctionEditMetaPane);
                SysUtil.scrollToBottom(scrollPane);
//				SysUtil.updateFrameUI(scrollPane);

            } else if (e.getSource() == delBt) {
                if (verticalBox.getComponentCount() > 0) {
                    verticalBox.remove(verticalBox.getComponentCount() - 1);
                    SysUtil.scrollToBottom(scrollPane);
                }
            } else if (e.getSource() == restoreButton) {
                restore();
            }
        }
    };

    /**
     * 获取操作参数列表
     */
    public ArrayList<AdditionalFunctionOperatingModel> getOperationParamList() {
        AdditionalFunctionEditMetaPane temp;
        AdditionalFunctionOperatingModel oModel;
        ArrayList<AdditionalFunctionOperatingModel> list = new ArrayList<>();
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            temp = (AdditionalFunctionEditMetaPane) verticalBox.getComponent(i);
            oModel = temp.getOperatingModel();
            list.add(oModel);
        }
        return list;
    }

    /**
     * 获取代码参数列表
     */
    public ArrayList<AdditionalFunctionCodeModel> getCodeParamList() {
        AdditionalFunctionEditMetaPane temp;
        ArrayList<AdditionalFunctionCodeModel> list = new ArrayList<>(), tempList;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            temp = (AdditionalFunctionEditMetaPane) verticalBox.getComponent(i);
            tempList = temp.getCodeModelList();
            for (AdditionalFunctionCodeModel codeTempModel : tempList) {
                codeTempModel.setAdditionalSerialNumber(this.additionalSerialNumber);
            }
            list.addAll(tempList);
        }
        return list;
    }

    public ArrayList<ContainerModel> getAllEditContainerModel() {
        ArrayList<ContainerModel> list = new ArrayList<ContainerModel>();
        AdditionalFunctionEditMetaPane temp;
        for (int i = 0; i < verticalBox.getComponentCount(); i++) {
            temp = (AdditionalFunctionEditMetaPane) verticalBox.getComponent(i);
            list.add(temp.getContainerModel());
        }
        return list;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (checkSameNameFromMetaPane() == true) {
            AdditionalFunctionEditMetaPane temp1, temp2;
            for (int i = 0; i < verticalBox.getComponentCount(); i++) {
                temp1 = (AdditionalFunctionEditMetaPane) verticalBox.getComponent(i);
                if (temp1.check() == false) {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    private boolean checkSameNameFromMetaPane() {
        boolean flag = true;
        String typeName = FormatEditPaneHolder.additionalEditPane.getAdditionalTypeName(additionalSerialNumber);
        String showText1, showText2;
        AdditionalFunctionEditMetaPane paneTemp1, paneTemp2;
        if (verticalBox.getComponentCount() > 0) {
            for (int i = 0; i < verticalBox.getComponentCount(); i++) {
                paneTemp1 = (AdditionalFunctionEditMetaPane) verticalBox.getComponent(i);
                showText1 = paneTemp1.getShowText();
                for (int a = i + 1; a < verticalBox.getComponentCount(); a++) {
                    paneTemp2 = (AdditionalFunctionEditMetaPane) verticalBox.getComponent(a);
                    showText2 = paneTemp2.getShowText();
                    if (showText1.equals(showText2)) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null, "\"" + typeName + "\"的功能面板，第" + (i + 1) + "个可选模板功能和第" + (a + 1)
                                + "个可选模板功能显示名称一样，\n请检查清楚录入数据无误后再保存", "系统信息", JOptionPane.PLAIN_MESSAGE);
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

    public void displayAdditionalContent(AdditionalInfo additionalInfo) {
        // TODO Auto-generated method stub
        if (additionalInfo.getNumOfFunctionCodeType() > 0) {
            ArrayList<AdditionalFunctionMetaModel> additionalFunctionList = SysService.ADDITIONAL_FUNCTION_SERVICE
                    .getAdditionalMetaModelList(this.additionalSerialNumber);
            AdditionalFunctionEditMetaPane additionalFunctionEditMetaPane;
            for (AdditionalFunctionMetaModel tempModel : additionalFunctionList) {
                additionalFunctionEditMetaPane = new AdditionalFunctionEditMetaPane(tempModel);
                verticalBox.add(additionalFunctionEditMetaPane);
            }
            scrollPane.updateUI();
            scrollPane.repaint();
            updateUI();
            repaint();
        }
    }

    @Override
    public void setUIResponse(boolean status) {
        if (status) {
            scrollPane.getVerticalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
            scrollPane.getHorizontalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
            toolBar.setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
        } else {
            scrollPane.getVerticalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
            scrollPane.getHorizontalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
            toolBar.setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
        }
    }

    public MyButton getNavigateToFunctionBt(){
        return addBt;
    }

}
