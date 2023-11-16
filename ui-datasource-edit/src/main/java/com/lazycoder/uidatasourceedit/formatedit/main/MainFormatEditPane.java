package com.lazycoder.uidatasourceedit.formatedit.main;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.FormatInfoServiceImpl;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.MainMetaModel;
import com.lazycoder.service.vo.save.MainSetInputData;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.MainDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.formatedit.main.settype.MainSetCodeEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


public class MainFormatEditPane extends JPanel implements CheckInterface, MainPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1364550586442142454L;

    /**
     * 格式模型
     */
    public static FormatModel formatModel = null;
    /**
     * 必填模板默认代码输入面板
     */
    public static MainDeafaultFormatCodePane mainFormatDeafaultPane = null;

    /**
     * 分割比例
     */
    private double dividerLocation = 0.4;
    private MainFormatPutPane mainCodePane;

    private JSplitPane splitPane;
    private JTabbedPane mainTabbedPane;

    private MainInputEditPane mainInputEditPane;
    private MyButton save, newBt, openBt;

    private MainSetCodeEditPane mainSetCodeEditPane;

    private OperatingTipButton mainOperatingTipButton;

    private Dimension dd;

    /**
     * Create the panel.
     */
    public MainFormatEditPane() {
        // this.setBounds(0, 0, 1366, 768);
        setLayout(new BorderLayout(0, 0));
        toolbarInit();
        mainPaneInit();
    }

    private void toolbarInit() {
        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        newBt = new MyButton("新建源文件");
        newBt.addActionListener(listener);
        toolBar.add(newBt);

        openBt = new MyButton("导入源文件");
        openBt.addActionListener(listener);
        toolBar.add(openBt);

        save = new MyButton("保存");
        save.addActionListener(listener);
        toolBar.add(save);

        mainOperatingTipButton = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "必填模板"
                ).getAbsolutePath()
        );
        toolBar.add(mainOperatingTipButton);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == save) {
                if (check() == true) {
                    save();
                }
            } else if (e.getSource() == newBt) {
                createNewBlankSourceFile();
            } else if (e.getSource() == openBt) {
                openSourceFile();
            }
        }
    };

    private ComponentAdapter cAdapter = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            splitPane.setDividerLocation(dividerLocation);
        }
    };

    private void mainPaneInit() {
        dd = SysUtil.SCREEN_SIZE;

        Dimension dd1 = new Dimension((int) (0.94 * dd.getWidth()), (int) (0.5138 * dd.getHeight()));
        splitPane = new JSplitPane();
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 设置分割线方向
        splitPane.setOneTouchExpandable(false);
        splitPane.addComponentListener(cAdapter);
        splitPane.setEnabled(false);
        splitPane.setMinimumSize(dd1);
        splitPane.setMaximumSize(dd1);
        splitPane.setPreferredSize(dd1);
        add(splitPane, BorderLayout.CENTER);

        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setLeftComponent(mainTabbedPane);

        mainInputEditPane = new MainInputEditPane(this);
        JScrollPane scrollPane1 = new JScrollPane(mainInputEditPane);
        Dimension dd2 = new Dimension((int) (0.36 * dd.getWidth()), (int) (0.755 * dd.getHeight()));
        scrollPane1.setMinimumSize(dd2);
        scrollPane1.setMaximumSize(dd2);
        scrollPane1.setPreferredSize(dd2);
        mainTabbedPane.addTab("必填模板格式", scrollPane1);

        mainSetCodeEditPane = new MainSetCodeEditPane();
        FormatEditPaneHolder.mainSetCodeEditPane = mainSetCodeEditPane;
        JScrollPane scrollPane2 = new JScrollPane(mainSetCodeEditPane);
        mainTabbedPane.addTab("必填模板设置", scrollPane2);

        mainCodePane = new MainFormatPutPane();// 放置所有必填模板源码的面板
        FormatEditPaneHolder.mainCodePane = mainCodePane;
        splitPane.setRightComponent(mainCodePane);

        FormatInfoServiceImpl e = SysService.FORMAT_INFO_SERVICE;
        MainInfo mainInfo = SysService.FORMAT_INFO_SERVICE.getMainInfo();
        displayMainContent(mainInfo);

        FormatEditPaneHolder.splitPane = splitPane;
    }

    @Override
    public void displayMainContent(MainInfo mainInfo) {
        // TODO Auto-generated method stub
        formatModel = new FormatModel();
        mainInputEditPane.getMainFormatControlPane().displayMainContent(mainInfo);
        mainInputEditPane.displayMainContent(mainInfo);
        mainCodePane.displayMainContent(mainInfo);
        mainSetCodeEditPane.displayMainContent(mainInfo);
    }

    public void restoreFormat(MainInfo mainInfo) {
        // TODO Auto-generated method stub
        LazyCoderFormatControl.clear(formatModel);
        mainCodePane.clearAllCodePane();
        formatModel = new FormatModel();
        mainInputEditPane.getMainFormatControlPane().displayMainContent(mainInfo);
        mainCodePane.displayMainContent(mainInfo);
    }

    public String getMainSetTypeName(int typeSerialNumber) {
        return mainSetCodeEditPane.getMainSetTypeName(typeSerialNumber);
    }

    /**
     * 添加空白源文件
     */
    public void createNewBlankSourceFile() {
        FormatEditPaneHolder.mainCodePane.createNewBlankSourceFile();
    }

    /**
     * 打开源文件
     */
    private void openSourceFile() {
        FormatEditPaneHolder.mainCodePane.openSourceFile();
    }

    /**
     * 保存主代码格式文件
     */
    private void save() {
        // TODO Auto-generated method stub
//		saveMainInfo();
//		mainInputEditPane.save();
//		mainCodePane.save();
//        mainSetCodeEditPane.save();
        MainInfo mainInfo = getMainInfo();

        MainMetaModel mainMetaModel = new MainMetaModel();
        mainMetaModel.setOperatingModel(mainInputEditPane.getMainOperating());
        mainMetaModel.setCodeModelList(mainCodePane.getFormatCodeList());

        List<VariableData> variableDataList = mainInputEditPane.getVariableDataList();
        List<FunctionNameData> functionNameDataList = mainInputEditPane.getFunctionNameDataList();
        MainSetInputData mainSetInputData = mainSetCodeEditPane.getMainSetInputData();

        boolean flag = SysService.INPUT_DATA_SAVE_SERVICE.saveMainInputData(mainInfo, mainMetaModel,
                variableDataList, functionNameDataList,
                mainSetInputData.getOperatingList(), mainSetInputData.getCodeList());
        if (flag) {
            LazyCoderOptionPane.showMessageDialog(null, "已保存必填模板部分内容！", "系统信息", JOptionPane.PLAIN_MESSAGE);
            delRedundantFiles();
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "(╥╯^╰╥) 不知道为什么，保存不了了，要不清理一下内存，或者重新打开软件再试试吧", "系统信息", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private MainInfo getMainInfo() {
        MainInfo mainInfo = new MainInfo();
        mainInputEditPane.setMainInfoRelatedParam(mainInfo);
        mainSetCodeEditPane.setMainInfoRelatedParam(mainInfo);
        mainCodePane.setMainInfoRelatedParam(mainInfo);

        if (mainInputEditPane.getMainFormatControlPane().getUseState() == true) {
            mainInfo.setFormatState(BaseModel.TRUE_);
        } else {
            mainInfo.setFormatState(BaseModel.FALSE_);
        }
        return mainInfo;
    }

    /**
     * 保存必填模板的状态信息
     */
    private void saveMainInfo() {
        MainInfo mainInfo = new MainInfo();
        mainInputEditPane.setMainInfoRelatedParam(mainInfo);
        mainSetCodeEditPane.setMainInfoRelatedParam(mainInfo);
        mainCodePane.setMainInfoRelatedParam(mainInfo);

        if (mainInputEditPane.getMainFormatControlPane().getUseState() == true) {
            mainInfo.setFormatState(BaseModel.TRUE_);
        } else {
            mainInfo.setFormatState(BaseModel.FALSE_);
        }

        SysService.FORMAT_INFO_SERVICE.updateMainSetInfo(mainInfo);
    }

    /**
     * 获取所有代码编辑面板的模型
     *
     * @return
     */
    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        list.add(formatModel);
        list.addAll(mainSetCodeEditPane.getAllEditContainerModel());
        return list;
    }

    /**
     * 删除多余的文件（保存时调用）
     */
    private void delRedundantFiles() {
        ArrayList<AbstractEditContainerModel> modelList = getAllEditContainerModel();

        //删除多余的文件选择组件模型对应的文件夹
        File needFileFolder = DatabaseFileStructure.getMainNeedFileFolder(SysFileStructure.getDataFileFolder(),
                GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
        ArrayList<File> fsfileList = DataSourceEditHolder.getCorrespondingModelFileSelectFolderList(needFileFolder, modelList);
        DataSourceEditHolder.delRedundantFiles(fsfileList, needFileFolder);

        //删除多余的图片组件模型对应的文件夹
        File pictureFolder = DatabaseFileStructure.getMainPictureFolder(SysFileStructure.getDataFileFolder(),
                GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
        ArrayList<File> psfileList = DataSourceEditHolder.getCorrespondingModelFilePictureList(pictureFolder, modelList);
        DataSourceEditHolder.delRedundantFiles(psfileList, pictureFolder);

    }


    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (mainInputEditPane.check() == true) {
            if (mainCodePane.check() == true) {
                if (mainSetCodeEditPane.check() == false) {
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


}
