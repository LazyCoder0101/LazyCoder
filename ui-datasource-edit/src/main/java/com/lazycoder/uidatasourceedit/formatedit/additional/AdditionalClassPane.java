package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.service.vo.save.AdditionalFunctionCodeInputData;
import com.lazycoder.service.vo.save.AdditionalFunctionNameInputData;
import com.lazycoder.service.vo.save.AdditionalFunctionOperatingInputData;
import com.lazycoder.service.vo.save.AdditionalVariableInputData;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.formatedit.additional.settype.AdditionalSetCodeEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AdditionalClassPane extends JPanel implements CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = -8511855201672720204L;

    /**
     * Create the panel.
     */
    private JTextField additionalClassField;

    private AdditionalFormatPane additionalFormatPane;

//    private OperatingTipButton operatingTipButton;

    /**
     * 可选模板的标识编号
     */
    private int additionalSerialNumber = 0;

    private AdditionalClassPane(int additionalSerialNumber) {
        this.additionalSerialNumber = additionalSerialNumber;

        setLayout(new BorderLayout(0, 0));

        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        JLabel label = new JLabel("模板名称：");
        toolBar.add(label);

        additionalClassField = new JTextField();
        toolBar.add(additionalClassField);
        additionalClassField.setColumns(10);
        Dimension dd = new Dimension(260, 30);
        additionalClassField.setMinimumSize(dd);
        additionalClassField.setMaximumSize(dd);
        additionalClassField.setPreferredSize(dd);

        Component horizontalStrut = Box.createHorizontalStrut(30);
        toolBar.add(horizontalStrut);

//        operatingTipButton = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
//                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "可选模板")
//                .getAbsolutePath());
//        toolBar.add(operatingTipButton);

        additionalFormatPane = new AdditionalFormatPane(additionalSerialNumber);
        add(additionalFormatPane, BorderLayout.CENTER);
    }

    /**
     * 还原内容
     *
     * @param metaModel
     */
    public AdditionalClassPane(int additionalSerialNumber, AdditionalMetaModel metaModel) {
        this(additionalSerialNumber);
        additionalClassField.setText(metaModel.getOperatingModel().getTypeName());

        AdditionalInfo additionalInfo = SysService.FORMAT_INFO_SERVICE.getAdditionalInfo(additionalSerialNumber);
        if (additionalInfo != null) {
            additionalFormatPane.displayAdditionalContent(additionalInfo, metaModel);
        }
    }

    /**
     * 新建内容
     *
     * @param fileName
     */
    public AdditionalClassPane(int additionalSerialNumber, String fileName) {
        this(additionalSerialNumber);
        additionalFormatPane.buildingNewAdditionalPane(fileName);
    }

    /**
     * 根据codeFormatFlagParam的信息获取对应面板
     *
     * @param codeFileId
     * @return
     */
    public AbstractFormatCodeInputPane getFormatCodeInputPane(String codeFileId) {
        return additionalFormatPane.getFormatCodeInputPane(codeFileId);
    }

    public ArrayList<AbstractFormatCodeInputPane> getAllormatCodeInputPane() {
        return additionalFormatPane.getAllormatCodeInputPane();
    }

    public String getTheAdditionalSetTypeName(int typeSerialNumber) {
        // TODO Auto-generated method stub
        return additionalFormatPane.getTheAdditionalSetTypeName(typeSerialNumber);
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        return additionalFormatPane.getAllEditContainerModel();
    }

    /**
     * 删除多余的文件（保存时调用）
     */
    public void delRedundantFiles() {
        ArrayList<AbstractEditContainerModel> modelList = getAllEditContainerModel();
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();

        //删除多余的文件选择组件模型对应的文件夹
        File needFileFolder = DatabaseFileStructure.getAdditionalNeedFileFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), additionalSerialNumber);
        ArrayList<File> fsfileList = DataSourceEditHolder.getCorrespondingModelFileSelectFolderList(needFileFolder, modelList);
        DataSourceEditHolder.delRedundantFiles(fsfileList, needFileFolder);

        //删除多余的图片组件模型对应的文件夹
        File pictureFolder = DatabaseFileStructure.getAdditionalPictureFolder(SysFileStructure.getDataFileFolder(),
                GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), additionalSerialNumber);
        ArrayList<File> psfileList = DataSourceEditHolder.getCorrespondingModelFilePictureList(pictureFolder, modelList);
        DataSourceEditHolder.delRedundantFiles(psfileList, pictureFolder);
    }

    public AdditionalInfo getAdditionalInfo() {
        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalFormatPane.setAdditionalInfo(additionalInfo);
        return additionalInfo;
    }

    /**
     * 重新加载格式代码面板
     */
    public void reloadFormatCodeInputPane() {
        additionalFormatPane.reloadFormatCodeInputPane();
    }


    /**
     * 获取其他操作
     *
     * @return
     */
    public AdditionalOperating getAdditionalOperating() {
        AdditionalOperating operating = additionalFormatPane.getAdditionalOperatingl();
        operating.setTypeName(additionalClassField.getText().trim());
        return operating;
    }

    /**
     * 获取该其他部分所有文件格式
     *
     * @return
     */
    public List<GeneralFileFormat> getCodeFormatList() {
        return additionalFormatPane.getCodeFormatList();
    }

    /**
     * 获取可选模板设置方法操作列表
     *
     * @return
     */
    public List<FormatTypeOperatingModel> getAdditionalSetOperatingModelList() {
        return additionalFormatPane.getAdditionalSetOperatingModelList();
    }

    /**
     * 获取可选模板的其他代码操作层相关数据
     *
     * @return
     */
    public AdditionalFunctionCodeInputData getAdditionalFunctionCodeInputData() {
        return additionalFormatPane.getAdditionalFunctionCodeInputData();
    }

    /**
     * 获取可选模板的其他代码层相关数据
     *
     * @return
     */
    public AdditionalFunctionOperatingInputData getAdditionalFunctionOperatingInputData() {
        return additionalFormatPane.getAdditionalFunctionOperatingInputData();
    }

    /**
     * 获取可选模板设置方法代码模型列表
     *
     * @return
     */
    public List<FormatTypeCodeModel> getAdditionalSetCodeModelList() {
        return additionalFormatPane.getAdditionalSetCodeModelList();
    }

    /**
     * 获取该其他面板的唯一识别号
     *
     * @return
     */
    public int getAdditionalSerialNumber() {
        return additionalSerialNumber;
    }

    public String getAdditionalTypeName() {
        return additionalClassField.getText().trim();
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if ("".equals(additionalClassField.getText().trim())) {
            LazyCoderOptionPane.showMessageDialog(null, "其他" + additionalSerialNumber + "没写分类名", "系统信息", JOptionPane.PLAIN_MESSAGE);
            flag = false;
        } else {
            if (additionalFormatPane.check() == false) {
                flag = false;
            }
        }
        return flag;
    }


    /**
     * 获取录入的可选模板的变量相关数据
     *
     * @return
     */
    public AdditionalVariableInputData getAdditionalVariableInputData() {
        return additionalFormatPane.getAdditionalVariableInputData();
    }

    /**
     * 获取录入的可选模板的方法名相关数据
     *
     * @return
     */
    public AdditionalFunctionNameInputData getAdditionalFunctionNameInputData() {
        return additionalFormatPane.getAdditionalFunctionNameInputData();
    }

    /**
     * 获取附带模板的设置功能输入面板
     * @return
     */
    public AdditionalSetCodeEditPane getAdditionalSetCodeEditPane() {
        return additionalFormatPane.getAdditionalSetCodeEditPane();
    }

    /**
     * 获取附带模板的方法功能输入面板
     * @return
     */
    public AdditionalFunctionPane getAdditionalFunctionPane(){
        return additionalFormatPane.getAdditionalFunctionPane();
    }

}
