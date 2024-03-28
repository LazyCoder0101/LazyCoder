package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.DatabaseFrameFileMethod;
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
import com.lazycoder.uidatasourceedit.previewtest.PreviewTestProButton;
import com.lazycoder.uidatasourceedit.previewtest.PreviewTestProjectFrame;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AdditionalEditPane extends JPanel implements CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7672532882571182948L;

    /**
     * Create the panel.
     */
    private MyButton addBt, delBt, saveBt, restoreButton;
    private PreviewTestProButton previewTestBt;
    private JTabbedPane tabbedPane;

    private OperatingTipButton additionalOperatingTipButton;

    /**
     * 当前可选模板文件个数
     */
    private int currentlNum = 0;

    public AdditionalEditPane() {
        setLayout(new BorderLayout(0, 0));

        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        additionalOperatingTipButton = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "可选模板" + File.separator + "添加可选模板")
                .getAbsolutePath());
        toolBar.add(additionalOperatingTipButton);
        toolBar.add(Box.createHorizontalStrut(20));

        addBt = new MyButton("添加");
        addBt.addActionListener(listener);
        toolBar.add(addBt);

        toolBar.add(Box.createHorizontalStrut(20));
        delBt = new MyButton("删除");
        delBt.addActionListener(listener);
        toolBar.add(delBt);

        toolBar.add(Box.createHorizontalStrut(20));
        saveBt = new MyButton("保存");
        saveBt.addActionListener(listener);
        toolBar.add(saveBt);

        toolBar.add(Box.createHorizontalStrut(20));
        restoreButton = new MyButton("还原");
        restoreButton.addActionListener(listener);
        toolBar.add(restoreButton);

        toolBar.add(Box.createHorizontalStrut(20));
        previewTestBt = new PreviewTestProButton();
        toolBar.add(previewTestBt);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        add(tabbedPane, BorderLayout.CENTER);

        displayAdditionalContent();
    }

    /**
     * 保存其他文件的设置
     */
    private void save() {
        List<AdditionalInfo> additionalInfoList = getAdditionalInfoList();
        ArrayList<AdditionalOperating> additionalOperatingList = getAdditionalOperatingList();
        List<GeneralFileFormat> additionalCodeFormatList = getAdditionalCodeFormatList();
        ArrayList<AdditionalVariableInputData> additionalVariableInputDataList = getAdditionalVariableInputDataList();
        ArrayList<AdditionalFunctionNameInputData> additionalFunctionNameInputDataList = getAdditionalFunctionNameInputDataList();
        List<FormatTypeOperatingModel> additionalSetOperatingList = getAdditionalSetOperatingList();
        List<FormatTypeCodeModel> additionalSetCodeList = getAdditionalSetCodeModelList();
        ArrayList<AdditionalFunctionOperatingInputData> additionalFunctionOperatingInputDataList = getAdditionalFunctionOperatingInputDataList();
        ArrayList<AdditionalFunctionCodeInputData> additionalFunctionCodeInputDataList = getAdditionalFunctionCodeInputDataList();

        boolean flag =
                SysService.INPUT_DATA_SAVE_SERVICE.saveAdditionalInputData(additionalInfoList,
                        additionalOperatingList, additionalCodeFormatList,
                        additionalVariableInputDataList, additionalFunctionNameInputDataList,
                        additionalSetOperatingList, additionalSetCodeList,
                        additionalFunctionOperatingInputDataList, additionalFunctionCodeInputDataList);
        if (flag) {
            LazyCoderOptionPane.showMessageDialog(null, "已保存可选模板所有内容！", "系统信息", JOptionPane.PLAIN_MESSAGE);
            delRedundantFiles();
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "(╥╯^╰╥) 不知道为什么，保存不了了，要不清理一下内存，或者重新打开软件再试试吧", "系统信息", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addBt) {
                addAdditionalClass();
            } else if (e.getSource() == delBt) {
                delAdditionalClass();
            } else if (e.getSource() == saveBt) {
                if (check() == true) {
                    save();
                }
            } else if (e.getSource() == restoreButton) {
                restore();
            }
        }
    };

    /**
     * 预览测试
     */
    private void previewTest(){
        if (!DataSourceEditHolder.previewTesting){
            if (SysService.SYS_PARAM_SERVICE.getEnabledState()) {
                new PreviewTestProjectFrame();
            }else {
                LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆	这个数据源还没用录入内容，请录入内容并保存后再进行预览测试！");
            }
        }else {
            LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆	已经打开预览测试功能了！");
        }
    }

    private void restore() {
        tabbedPane.removeAll();
        displayAdditionalContent();
    }

    /**
     * 获取可选模板的可使用范围，给可使用范围下拉框用
     *
     * @return
     */
    public ArrayList<UsingObject> getAdditionalTypeList() {
        ArrayList<UsingObject> list = new ArrayList<>();
        AdditionalClassPane oTemp;
        UsingObject usingObject;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            usingObject = new UsingObject(oTemp.getAdditionalSerialNumber());
            list.add(usingObject);
        }
        return list;
    }

    /**
     * 获取设置方法的操作层相关数据
     *
     * @return
     */
    private List<FormatTypeOperatingModel> getAdditionalSetOperatingList() {
        List<FormatTypeOperatingModel> list = new ArrayList<com.lazycoder.database.model.command.FormatTypeOperatingModel>();
        List<FormatTypeOperatingModel> tempList;
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            tempList = oTemp.getAdditionalSetOperatingModelList();
            list.addAll(tempList);
        }
        return list;
    }


    /**
     * 根据codeFormatFlagParam的信息获取对应面板
     *
     * @param additionalSerialNumber
     * @param codeFileId
     * @return
     */
    public AbstractFormatCodeInputPane getFormatCodeInputPane(int additionalSerialNumber, String codeFileId) {
        AbstractFormatCodeInputPane formatCodeInputPane = null;
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            if (oTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                formatCodeInputPane = oTemp.getFormatCodeInputPane(codeFileId);
                break;
            }
        }
        return formatCodeInputPane;
    }

    public ArrayList<AbstractFormatCodeInputPane> getAllormatCodeInputPane(int additionalSerialNumber) {
        ArrayList<AbstractFormatCodeInputPane> list = new ArrayList<>();
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            if (oTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                list = oTemp.getAllormatCodeInputPane();
                break;
            }
        }
        return list;
    }


    private List<FormatTypeCodeModel> getAdditionalSetCodeModelList() {
        List<FormatTypeCodeModel> list = new ArrayList<com.lazycoder.database.model.command.FormatTypeCodeModel>();
        List<FormatTypeCodeModel> tempList;
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            tempList = oTemp.getAdditionalSetCodeModelList();
            list.addAll(tempList);
        }
        return list;
    }


    private List<AdditionalInfo> getAdditionalInfoList() {
        List<AdditionalInfo> additionalInfoList = new ArrayList<>();
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            additionalInfoList.add(oTemp.getAdditionalInfo());
        }
        return additionalInfoList;
    }

    /**
     * 重新加载格式代码面板
     */
    public void reloadFormatCodeInputPane() {
        AdditionalClassPane oTemp;
        currentlNum = 0;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            currentlNum++;
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            oTemp.reloadFormatCodeInputPane();
        }
    }

    private ArrayList<AdditionalOperating> getAdditionalOperatingList() {
        ArrayList<AdditionalOperating> list = new ArrayList<>();
        AdditionalOperating temp;
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            temp = oTemp.getAdditionalOperating();
            list.add(temp);
        }
        return list;
    }

    private ArrayList<AdditionalVariableInputData> getAdditionalVariableInputDataList() {
        ArrayList<AdditionalVariableInputData> list = new ArrayList<>();
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            list.add(oTemp.getAdditionalVariableInputData());
        }
        return list;
    }

    private ArrayList<AdditionalFunctionNameInputData> getAdditionalFunctionNameInputDataList() {
        ArrayList<AdditionalFunctionNameInputData> list = new ArrayList<>();
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            list.add(oTemp.getAdditionalFunctionNameInputData());
        }
        return list;
    }

    private boolean checkAdditionalSetCategoryEditPane() {
        boolean flag = true;
        String typeName1, typeName2;
        AdditionalClassPane paneTemp1, paneTemp2;
        if (tabbedPane.getComponentCount() > 0) {
            for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                paneTemp1 = (AdditionalClassPane) tabbedPane.getComponent(i);
                typeName1 = paneTemp1.getAdditionalTypeName();
                for (int a = i + 1; a < tabbedPane.getComponentCount(); a++) {
                    paneTemp2 = (AdditionalClassPane) tabbedPane.getComponent(a);
                    typeName2 = paneTemp2.getAdditionalTypeName();
                    if (typeName1.equals(typeName2)) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null,
                                "第" + (i + 1) + "个其他面板和第" + (a + 1) + "个其他面板分类名一样，\n请检查清楚录入数据无误后再保存", "系统信息",
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
     * 保存其他代码
     *
     * @return
     */
//    public void saveAdditionalCodeFormatList() {
//        List<GeneralFileFormat> list = new ArrayList<>();
//        AdditionalClassPane oTemp;
//        List<GeneralFileFormat> tempList;
//        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
//            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
//            tempList = oTemp.getCodeFormatList();
//            list.addAll(tempList);
//        }
//        SysService.ADDITIONAL_FORMAT_FILE_SERVICE.saveFormatCodeFileList(list);
//    }
    public List<GeneralFileFormat> getAdditionalCodeFormatList() {
        List<GeneralFileFormat> list = new ArrayList<>();
        AdditionalClassPane oTemp;
        List<GeneralFileFormat> tempList;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            tempList = oTemp.getCodeFormatList();
            list.addAll(tempList);
        }
        return list;
    }

    public String getAdditionalTypeName(int additionalTypeSerialNumber) {
        String typeName = "";
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            if (oTemp.getAdditionalSerialNumber() == additionalTypeSerialNumber) {
                typeName = oTemp.getAdditionalTypeName();
                break;
            }
        }
        return typeName;
    }

    /**
     * 获取可选模板代码层的数据
     *
     * @return
     */
    public ArrayList<AdditionalFunctionCodeInputData> getAdditionalFunctionCodeInputDataList() {
        ArrayList<AdditionalFunctionCodeInputData> list = new ArrayList<>();
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            list.add(oTemp.getAdditionalFunctionCodeInputData());
        }
        return list;
    }

    /**
     * 获取可选模板代码层的数据
     *
     * @return
     */
    public ArrayList<AdditionalFunctionOperatingInputData> getAdditionalFunctionOperatingInputDataList() {
        ArrayList<AdditionalFunctionOperatingInputData> list = new ArrayList<>();
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            list.add(oTemp.getAdditionalFunctionOperatingInputData());
        }
        return list;
    }

    public String getTheAdditionalSetTypeName(int additionalSerialNumber, int typeSerialNumber) {
        // TODO Auto-generated method stub
        String typeNameString = "";
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            if (oTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                typeNameString = oTemp.getTheAdditionalSetTypeName(typeSerialNumber);
                break;
            }
        }
        return typeNameString;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (checkAdditionalSetCategoryEditPane() == true) {
            if (tabbedPane.getComponentCount() > 0) {
                AdditionalClassPane oTemp;
                for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                    oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
                    if (oTemp.check() == false) {
                        flag = false;
                        break;
                    }
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            list.addAll(oTemp.getAllEditContainerModel());
        }
        return list;
    }

    private void addAdditionalClass() {
        currentlNum++;
        AdditionalClassPane additionalClassPane = new AdditionalClassPane(currentlNum, "additional.xx");
        tabbedPane.addTab("分类" + currentlNum, additionalClassPane);
        DatabaseFrameFileMethod.generateOrCheckAdditionalFileStrucure(GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                currentlNum);
    }

    private void delAdditionalClass() {
        if (tabbedPane.getComponentCount() > 0) {
            currentlNum--;
            tabbedPane.remove(tabbedPane.getComponentCount() - 1);
        }
    }

    private void displayAdditionalContent() {
        // TODO Auto-generated method stub
        currentlNum = 0;
        ArrayList<AdditionalMetaModel> list = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalMetaModelList();//获取所有
        if (list != null) {
            AdditionalClassPane additionalClassPane;
            for (AdditionalMetaModel temp : list) {
                currentlNum++;
                DatabaseFrameFileMethod.generateOrCheckAdditionalFileStrucure(GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                        temp.getOperatingModel().getAdditionalSerialNumber());
                additionalClassPane = new AdditionalClassPane(temp.getOperatingModel().getAdditionalSerialNumber(), temp);
                tabbedPane.addTab("分类" + currentlNum, additionalClassPane);
            }
        }
    }

    /**
     * 删除多余的文件（保存时调用）
     */
    private void delRedundantFiles() {
        // 如果有点击添加，对生成对应存储数据文件，在这里删除
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();
        int fileNum = tabbedPane.getComponentCount();
        File filePutFolder = DatabaseFileStructure.getAdditionalRootFolder(sysDataFileFolder,
                GeneralHolder.currentUserDataSourceLabel.getDataSourceName()), tempFolder;
        File[] files = filePutFolder.listFiles();
        if (files != null) {
            if (files.length > fileNum) {
                for (int i = fileNum + 1; i <= files.length; i++) {
                    tempFolder = DatabaseFileStructure.getAdditionalFolder(sysDataFileFolder,
                            GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), i);
                    if (tempFolder.isDirectory()) {
                        FileUtil.delFolder(tempFolder.getAbsolutePath());
                    }
                }
            }
        }
        // 删除目前剩下的各个其他面板里，多出来的功能拓展文件
        if (tabbedPane.getComponentCount() > 0) {
            AdditionalClassPane oTemp;
            for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
                oTemp.delRedundantFiles();
            }
        }
    }

    /**
     * 获取对应序号的可选模板的设置功能输入面板
     *
     * @return
     */
    public AdditionalSetCodeEditPane getAdditionalSetCodeEditPane(int additionalSerialNumber) {
        AdditionalSetCodeEditPane additionalSetCodeEditPane = null;
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            if (oTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                additionalSetCodeEditPane = oTemp.getAdditionalSetCodeEditPane();
                break;
            }
        }
        return additionalSetCodeEditPane;
    }

    /**
     * 获取对应序号的可选模板的方法录入面板
     *
     * @return
     */
    public AdditionalFunctionPane getAdditionalFunctionPane(int additionalSerialNumber) {
        AdditionalFunctionPane additionalFunctionPane = null;
        AdditionalClassPane oTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            oTemp = (AdditionalClassPane) tabbedPane.getComponent(i);
            if (oTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                additionalFunctionPane = oTemp.getAdditionalFunctionPane();
                break;
            }
        }
        return additionalFunctionPane;
    }

}
