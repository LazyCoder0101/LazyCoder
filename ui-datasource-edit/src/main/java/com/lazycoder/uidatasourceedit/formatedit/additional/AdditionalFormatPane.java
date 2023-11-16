package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.command.AdditionalFunctionServicelmpl;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.service.vo.save.AdditionalFunctionCodeInputData;
import com.lazycoder.service.vo.save.AdditionalFunctionNameInputData;
import com.lazycoder.service.vo.save.AdditionalFunctionOperatingInputData;
import com.lazycoder.service.vo.save.AdditionalVariableInputData;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.AdditionalDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.AdditionalFormatControlPane;
import com.lazycoder.uidatasourceedit.formatedit.additional.attachedfile.AdditionalAttachedFileSettingFrame;
import com.lazycoder.uidatasourceedit.formatedit.additional.settype.AdditionalSetCodeEditPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.OperatingPropertyCombobox;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;

public class AdditionalFormatPane extends JPanel implements AdditionalPaneInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 8719815815776679031L;
    public final AdditionalFunctionServicelmpl additionalFunctionServicelmpl = SysService.ADDITIONAL_FUNCTION_SERVICE;
    private JSplitPane splitPane;
    private double dividerLocation = 0.4;
    private AdditionalInputEditPane additionalInputEditPane;
    private AdditionalDeafaultFormatCodePane additionalDeafaultFormatCodePane = null;
    private AdditionalCodeFormatPutPane additionalCodeFormatPutPane;
    private JTabbedPane tabbedPane;
    private AdditionalSetCodeEditPane additionalSetCodeEditPane;
    private AdditionalFunctionPane additionalFunctionPane;
    private MyButton newBt, openBt, setBt;

    private OperatingPropertyCombobox setPropertyCombobox;

    /**
     * 可选模板的标识编号
     */
    private int additionalSerialNumber = 0;
    /**
     * 该其他面板的模型
     */
    private FormatModel thisFormatModel = null;

    /**
     * Create the panel.
     */
    public AdditionalFormatPane(int additionalSerialNumber) {
        this.additionalSerialNumber = additionalSerialNumber;
        setLayout(new BorderLayout(0, 0));
        toolBarInit();
        formatComponentInit();
    }

    private void toolBarInit() {
        MyToolBar toolBar = new MyToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        newBt = new MyButton("新建源文件");
        newBt.addActionListener(listener);
        toolBar.add(newBt);
        toolBar.add(Box.createHorizontalStrut(20));

        openBt = new MyButton("导入源文件");
        openBt.addActionListener(listener);
        toolBar.add(openBt);
        toolBar.add(Box.createHorizontalStrut(20));

        setBt = new MyButton("设置");
        toolBar.add(setBt);
        toolBar.add(Box.createHorizontalStrut(20));
        setBt.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(20));
        JLabel label = new JLabel("使用限制：");
        toolBar.add(label);
        setPropertyCombobox = OperatingPropertyCombobox.creatFormatSetPropertyCombobox();
        setPropertyCombobox.setMaximumSize(new Dimension(200, 30));
        toolBar.add(setPropertyCombobox);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == newBt) {
                additionalCodeFormatPutPane.createNewBlankSourceFile();
            } else if (e.getSource() == openBt) {
                additionalCodeFormatPutPane.openSourceFile();
            } else if (e.getSource() == setBt) {
                new AdditionalAttachedFileSettingFrame(additionalSerialNumber);
            }
        }
    };

    private ComponentAdapter cAdapter = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            splitPane.setDividerLocation(dividerLocation);
        }
    };

    /**
     * 根据codeFormatFlagParam的信息获取对应面板
     *
     * @param codeFileId
     * @return
     */
    public AbstractFormatCodeInputPane getFormatCodeInputPane(String codeFileId) {
        return additionalCodeFormatPutPane.getFormatCodeInputPane(codeFileId);
    }

    public ArrayList<AbstractFormatCodeInputPane> getAllormatCodeInputPane() {
        return additionalCodeFormatPutPane.getAllormatCodeInputPane();
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        if (additionalInputEditPane.getAdditionalFormatControlPane().getUseState() == true) {
            additionalInfo.setFormatState(AdditionalInfo.TRUE_);
        } else {
            additionalInfo.setFormatState(AdditionalInfo.FALSE_);
        }
        additionalInfo.setNumOfVariable(additionalInputEditPane.getAdditionalVariableTable().getVariableNum());
        additionalInfo.setFunctionNameNum(additionalInputEditPane.getAdditionalFuncitonTable().getFunctionNameNum());
        additionalInfo.setAdditionalSerialNumber(additionalSerialNumber);
        additionalCodeFormatPutPane.setAdditionalInfo(additionalInfo);
        additionalSetCodeEditPane.setAdditonalInfo(additionalInfo);
        additionalInfo.setNumOfFunctionCodeType(additionalFunctionPane.getNumOfAdditionalFunction());
    }

    public void clear() {
        if (ModuleEditPaneHolder.usingRange.checkHaveSelected(new UsingObject(additionalSerialNumber))) {
            LazyCoderOptionPane.showMessageDialog(null,
                    "你在\"模块设置\"里正在编辑的\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"模块中，\n设置那个模块要使用可选模板" + additionalSerialNumber + "的代码文件，直接清空可能会删了不该删的内容，\n" +
                            "真要清空的话，先别在那里选可选模板" + additionalSerialNumber + "吧", "系统信息", JOptionPane.PLAIN_MESSAGE);
        } else {
            LazyCoderFormatControl.removeAllSubCodePaneList(thisFormatModel);
            LazyCoderFormatControl.clear(thisFormatModel);
            additionalCodeFormatPutPane.clearAllSubCodeInputPane();
        }
    }

    public void restoreFormat() {
        AdditionalInfo additionalInfo = SysService.FORMAT_INFO_SERVICE.getAdditionalInfo(additionalSerialNumber);
        if (additionalInfo == null) {
            LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
        } else if (additionalInfo != null) {
            AdditionalMetaModel metaModel = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalMetaModel(additionalSerialNumber);
            if (metaModel == null) {
                LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
            } else {
                LazyCoderFormatControl.clear(thisFormatModel);
                additionalCodeFormatPutPane.clearAllCodePane();
                thisFormatModel = new FormatModel();
                additionalInputEditPane.getAdditionalFormatControlPane().restore(metaModel, thisFormatModel);
                additionalCodeFormatPutPane.setAdditionalFormatModel(thisFormatModel);
                additionalCodeFormatPutPane.displayAdditionalContent(additionalInfo, metaModel);
            }
        }
    }

    /**
     * 重新加载格式代码面板
     */
    public void reloadFormatCodeInputPane() {
        additionalCodeFormatPutPane.reloadFormatCodeInputPane();
    }

    private void formatComponentInit() {
        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 设置分割线方向
        splitPane.setOneTouchExpandable(false);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.addComponentListener(cAdapter);
        splitPane.setEnabled(false);
        add(splitPane, BorderLayout.CENTER);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder());

        additionalInputEditPane = new AdditionalInputEditPane(this.additionalSerialNumber, this);
        JScrollPane scrollPane1 = new JScrollPane(additionalInputEditPane);
        tabbedPane.addTab("可选模板格式", scrollPane1);

        additionalCodeFormatPutPane = new AdditionalCodeFormatPutPane(additionalDeafaultFormatCodePane, additionalSerialNumber);
        splitPane.setRightComponent(additionalCodeFormatPutPane);

        additionalSetCodeEditPane = new AdditionalSetCodeEditPane(additionalCodeFormatPutPane, additionalSerialNumber);
        tabbedPane.addTab("可选模板设置", additionalSetCodeEditPane);

        additionalFunctionPane = new AdditionalFunctionPane(this.additionalSerialNumber);
        tabbedPane.addTab("功能", null, additionalFunctionPane, null);
        if (additionalFunctionPane.getNavigateToFunctionBt() != null) {
            additionalFunctionPane.getNavigateToFunctionBt().addMouseListener(navigateToFunctionMarkMouseAdapter);
        }

        splitPane.setLeftComponent(tabbedPane);
    }


    private MouseAdapter navigateToFunctionMarkMouseAdapter = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            super.mouseEntered(mouseEvent);
            additionalCodeFormatPutPane.navigateToFunctionMark(true);
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            super.mouseExited(mouseEvent);
            additionalCodeFormatPutPane.navigateToFunctionMark(false);
        }
    };

    /**
     * 获取其他操作
     *
     * @return
     */
    public AdditionalOperating getAdditionalOperatingl() {
        AdditionalOperating operating = new AdditionalOperating();
        LazyCoderFormatControl.setOperating(operating, thisFormatModel);
        try {
            operating.setDefaultControlStatementFormat(
                    additionalInputEditPane.getAdditionalFormatControlPane().getControlStatementFormat());
            operating.setNoteListParam(JsonUtil.getJsonStr(additionalInputEditPane.getAdditionalFormatControlPane().getUsageReminderList()));

        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        operating.setAdditionalSerialNumber(additionalSerialNumber);
        operating.setSetProperty(setPropertyCombobox.getSelectedItem().getSysDictionaryValue());
        return operating;
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        list.add(thisFormatModel);
        list.addAll(additionalSetCodeEditPane.getAllEditContainerModel());
        list.addAll(additionalFunctionPane.getAllEditContainerModel());
        return list;
    }

    /**
     * 获取可选模板的其他代码操作层相关数据
     *
     * @return
     */
    public AdditionalFunctionCodeInputData getAdditionalFunctionCodeInputData() {
        AdditionalFunctionCodeInputData additionalFunctionCodeInputData = new AdditionalFunctionCodeInputData();
        additionalFunctionCodeInputData.setAdditionalSerialNumber(this.additionalSerialNumber);
        additionalFunctionCodeInputData.setAdditionalFunctionCodeModelList(additionalFunctionPane.getCodeParamList());
        return additionalFunctionCodeInputData;
    }

    /**
     * 获取可选模板的其他代码待层
     *
     * @return
     */
    public AdditionalFunctionOperatingInputData getAdditionalFunctionOperatingInputData() {
        AdditionalFunctionOperatingInputData additionalFunctionOperatingInputData = new AdditionalFunctionOperatingInputData();
        additionalFunctionOperatingInputData.setAdditionalSerialNumber(this.additionalSerialNumber);
        additionalFunctionOperatingInputData.setAdditionalFunctionOperatingModelList(additionalFunctionPane.getOperationParamList());
        return additionalFunctionOperatingInputData;
    }

    /**
     * 显示additionalMetaModel的内容
     */
    @Override
    public void displayAdditionalContent(AdditionalInfo additionalInfo, AdditionalMetaModel additionalMetaModel) {
        // TODO Auto-generated method stub
        setPropertyCombobox.setSelectedItem(
                FunctionUseProperty.getFunctionUsePropertyBy(additionalMetaModel.getOperatingModel().getSetProperty()));

        // 可选模板操作
        thisFormatModel = new FormatModel();
        additionalInputEditPane.getAdditionalFormatControlPane().restore(additionalMetaModel, thisFormatModel);
        additionalInputEditPane.displayAdditionalContent(additionalInfo, additionalMetaModel);
        additionalCodeFormatPutPane.setAdditionalFormatModel(thisFormatModel);
        additionalCodeFormatPutPane.displayAdditionalContent(additionalInfo, additionalMetaModel);
        additionalSetCodeEditPane.displayAdditionalSetContents(additionalInfo);
        additionalFunctionPane.displayAdditionalContent(additionalInfo);

    }

    /**
     * 构建一个名为fileName的其他代码面板
     *
     * @param fileName
     */
    public void buildingNewAdditionalPane(String fileName) {
        // TODO Auto-generated method stub
        thisFormatModel = new FormatModel();
        additionalCodeFormatPutPane.setAdditionalFormatModel(thisFormatModel);
        LazyCoderFormatControl.buildingNewOperatingPane(additionalInputEditPane.getAdditionalFormatControlPane(),
                thisFormatModel);
        additionalCodeFormatPutPane.buildingNewAdditionalPane(fileName);
    }

    public List<GeneralFileFormat> getCodeFormatList() {
        return additionalCodeFormatPutPane.getCodeFormatList();
    }

    public AdditionalFormatControlPane getControlPane() {
        return additionalInputEditPane.getAdditionalFormatControlPane();
    }

    /**
     * 获取可选模板设置操作列表
     *
     * @return
     */
    public List<FormatTypeOperatingModel> getAdditionalSetOperatingModelList() {
        return additionalSetCodeEditPane.getAdditionalSetOperatingModelList();
    }

    /**
     * 获取可选模板设置代码模型列表
     *
     * @return
     */
    public List<FormatTypeCodeModel> getAdditionalSetCodeModelList() {
        return additionalSetCodeEditPane.getAdditionalSetCodeModelList();
    }

    public String getTheAdditionalSetTypeName(int typeSerialNumber) {
        // TODO Auto-generated method stub
        return additionalSetCodeEditPane.getTheAdditionalSetTypeName(typeSerialNumber);
    }

    /**
     * 获取录入的可选模板的变量相关数据
     *
     * @return
     */
    public AdditionalVariableInputData getAdditionalVariableInputData() {
        return additionalInputEditPane.getAdditionalVariableInputData();
    }

    /**
     * 获取录入的可选模板的功能名相关数据
     *
     * @return
     */
    public AdditionalFunctionNameInputData getAdditionalFunctionNameInputData() {
        return additionalInputEditPane.getAdditionalFunctionNameInputData();
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (additionalInputEditPane.check() == true) {
            if (additionalCodeFormatPutPane.check() == true) {
                if (additionalSetCodeEditPane.check() == true) {
                    if (additionalFunctionPane.check() == false) {
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 获取附带模板的设置功能输入面板
     *
     * @return
     */
    public AdditionalSetCodeEditPane getAdditionalSetCodeEditPane() {
        return additionalSetCodeEditPane;
    }

    /**
     * 获取附带模板的功能输入面板
     *
     * @return
     */
    public AdditionalFunctionPane getAdditionalFunctionPane() {
        return additionalFunctionPane;
    }

}
