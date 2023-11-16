package com.lazycoder.uicodegeneration.moduleselect;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeFormatServiceHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.dbselectframe.DataSourceSelectFrame;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrame;
import com.lazycoder.uicodegeneration.generalframe.SelectedModuleParam;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class ModuleSelectPane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -1594190417162650510L;

    //						创建代码生成界面状态		添加模块状态
    private final boolean createFrameState = true, addModuleState = false;

    private boolean state = createFrameState;

    /**
     * 模块的三种状态 1：一般的被选中 2：失能（该模块不能选） 之前已经选中，不能再选了
     */

//    private JPanel contentPane;

    private ModuleSelectListPane moduleSelectListPane;

    private JTextField proNameTextField, putPathTextField;

    private MyButton okbt, cancelBt;

    private JLabel dataSourceLabel, exLabel, paneFileNameLabel;

    private JTextField mainFileNameTextField;

    private OperatingTipButton operatingTip;

    /**
     * Create the frame.
     */
    private ModuleSelectPane() {
//        contentPane = new JPanel();
        setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
        setLayout(null);

        JLabel dbsLabel = new JLabel("当前数据源：");
        dbsLabel.setBounds(90, 20, 120, 30);
        Font font1 = new Font("微软雅黑", Font.PLAIN, 16);
        dbsLabel.setFont(font1);
        add(dbsLabel);

        dataSourceLabel = new JLabel("");
        dataSourceLabel.setBounds(208, 20, 600, 30);
        Font font2 = new Font("微软雅黑", Font.BOLD, 18);
        dataSourceLabel.setFont(font2);
        add(dataSourceLabel);

        moduleSelectListPane = new ModuleSelectListPane();
        JScrollPane scrollPane = new JScrollPane(moduleSelectListPane);
        scrollPane.setBounds(90, 65, 320, 450);
        add(scrollPane);

        JLabel proNameLabel = new JLabel("项目名：");
        proNameLabel.setFont(font1);
        proNameLabel.setBounds(430, 100, 70, 30);
        add(proNameLabel);

        proNameTextField = new JTextField();
        proNameTextField.setEditable(false);
        proNameTextField.setFont(font1);
        proNameTextField.setBounds(500, 100, 200, 30);
        add(proNameTextField);

        JLabel pathLabel = new JLabel("存放位置：");
        pathLabel.setFont(font1);
        pathLabel.setBounds(430, 180, 100, 30);
        add(pathLabel);

        putPathTextField = new JTextField();
        putPathTextField.setEditable(false);
        putPathTextField.setFont(font1);
        putPathTextField.setBounds(500, 180, 300, 30);
        add(putPathTextField);

        paneFileNameLabel = new JLabel("必填模板默认源文件名：");
        paneFileNameLabel.setFont(font1);
        paneFileNameLabel.setBounds(430, 260, 180, 30);
        add(paneFileNameLabel);

        mainFileNameTextField = new JTextField();
        mainFileNameTextField.setFont(font1);
        mainFileNameTextField.setBounds(610, 260, 200, 30);
        add(mainFileNameTextField);

        exLabel = new JLabel();
        exLabel.setFont(font1);
        exLabel.setBounds(815, 260, 40, 30);
        add(exLabel);

        operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "生成程序" + File.separator + "模块选择界面")
                .getAbsolutePath());
        operatingTip.setLocation(paneFileNameLabel.getX()+10, exLabel.getY() + 3+50);
        add(operatingTip);

        okbt = new MyButton("确定");
        okbt.addMouseListener(mouseAdapter);
        okbt.setBounds(470, 430, 100, 30);
        add(okbt);

        cancelBt = new MyButton("取消");
        cancelBt.setBounds(630, 430, 100, 30);
        add(cancelBt);

//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setModal(true);
    }

    /**
     * 创建代码生成界面之前选择模块的窗口
     *
     * @param proName
     * @param putPath
     */
    public static JFrame ModuleSelectFrame(String proName, String putPath) {
        LazyCoderCommonFrame frame = new LazyCoderCommonFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ModuleSelectPane contentPane = new ModuleSelectPane();
        frame.setContentPane(contentPane);
        frame.setTitle("生成程序");
        Dimension dimension = SysUtil.SCREEN_SIZE;
        frame.setBounds(dimension.width / 2 - 450, dimension.height / 2 - 350, 900, 600);

        contentPane.moduleSelectListPane.setUsingObject(UsingObject.MAIN_USING_OBJECT);
        contentPane.state = contentPane.createFrameState;

        contentPane.proNameTextField.setText(proName);
        contentPane.putPathTextField.setText(putPath);

        contentPane.dataSourceLabel.setText(GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
        contentPane.moduleSelectListPane.showModuleList(UsingObject.MAIN_USING_OBJECT, null, ModuleSelectListPane.FIRST_SELECTION);

        GeneralFileFormat defaultCodeFormat = SysService.MAIN_FORMAT_CODE_FILE_SERVICE
                .getMainDefaultFormatFile();// 获取默认主格式代码文件
        if (defaultCodeFormat != null) {
            contentPane.mainFileNameTextField.setText(FileUtil.getFileNameNoEx(defaultCodeFormat.getFileName()));
            contentPane.exLabel.setText(FileUtil.getFileEx(defaultCodeFormat.getFileName()));
        }
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int show = LazyCoderOptionPane.showConfirmDialog(null, "(oﾟ▽ﾟ)o  亲，确定要退出系统吗？", "系统提示", JOptionPane.YES_NO_OPTION);
                if (show == JOptionPane.YES_OPTION) {
                    System.exit(0);// 正常退出
                }
            }
        });
        ActionListener createFrameListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == contentPane.okbt) {
                    if (contentPane.check() == true) {
                        ArrayList<Module> list = contentPane.moduleSelectListPane.getSelectedModuleList();// 获取选中的所有模块

                        ArrayList<Module> sortList = SysService.MODULE_SERVICE.sortModuleListByNeedModuleParam(list);

                        ArrayList<ModuleRelatedParam> moduleRelatedParaList = SysService.MODULE_SERVICE.getCurrentModuleInfoListBy(sortList);// 根据模块列表拿到所有模块的模块信息

                        String projectParentPath = contentPane.putPathTextField.getText().trim(),
                                projectName = contentPane.proNameTextField.getText().trim(),
                                mainFileName = contentPane.mainFileNameTextField.getText().trim() + contentPane.exLabel.getText();
                        new CodeGenerationFrame(projectParentPath, projectName, moduleRelatedParaList, mainFileName);
                        frame.dispose();
                    }
                } else if (e.getSource() == contentPane.cancelBt) {
                    SysService.DB_CHANGE_SERVICE.changeSYSDB();
                    new DataSourceSelectFrame(contentPane.proNameTextField.getText(), contentPane.putPathTextField.getText());
                    frame.dispose();
                }
            }
        };
        contentPane.okbt.addActionListener(createFrameListener);
        contentPane.cancelBt.addActionListener(createFrameListener);


        frame.setVisible(true);
        new CodeFormatServiceHolder();//先初始化代码格式化服务
        return frame;
    }

    /**
     * 再选择模块时出现的窗口
     *
     * @param selectedModuleParam
     */
    public static JDialog ModuleSelectFrame(SelectedModuleParam selectedModuleParam) {
        LazyCoderCommonDialog dialog = new LazyCoderCommonDialog();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = SysUtil.SCREEN_SIZE;
        dialog.setBounds(dimension.width / 2 - 450, dimension.height / 2 - 350, 900, 600);

        ModuleSelectPane contentPane = new ModuleSelectPane();
        dialog.setContentPane(contentPane);
        dialog.setTitle("添加模块：" + selectedModuleParam.getCorrespondingPaneFileName());
        contentPane.moduleSelectListPane.setUsingObject(selectedModuleParam.getUsingObject());

        contentPane.state = contentPane.addModuleState;
        //       contentPane.paneFileNameLabel.setText("必填模板文件名：");

        contentPane.proNameTextField.setText(selectedModuleParam.getProjectName());
        contentPane.putPathTextField.setText(selectedModuleParam.getProjectParentPath());

        contentPane.dataSourceLabel.setText(GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
        contentPane.moduleSelectListPane.showModuleList(selectedModuleParam.getUsingObject(), selectedModuleParam.getModuleRelatedParamList(), ModuleSelectListPane.RESELECT);

        contentPane.mainFileNameTextField.setEditable(false);
        String fileNameNoEx = FileUtil.getFileNameNoEx(selectedModuleParam.getCorrespondingPaneFileName().trim()), ex = FileUtil.getFileEx(selectedModuleParam.getCorrespondingPaneFileName().trim());
        contentPane.mainFileNameTextField.setText(fileNameNoEx);
        contentPane.exLabel.setText(ex);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });
        ActionListener addModuleListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == contentPane.okbt) {
                    if (CodeGenerationFrameHolder.currentFormatControlPane != null) {
                        if (contentPane.check() == true) {
                            CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空

                            ArrayList<Module> list = contentPane.moduleSelectListPane.getSelectedModuleList();// 获取选中的所有模块
                            ArrayList<Module> sortList = SysService.MODULE_SERVICE.sortModuleListByNeedModuleParam(list);

                            ArrayList<ModuleRelatedParam> moduleRelatedParaList = SysService.MODULE_SERVICE.getCurrentModuleInfoListBy(sortList);// 根据模块列表拿到所有模块的模块信息
                            CodeGenerationFrameHolder.currentFormatControlPane.addInit(moduleRelatedParaList);

                            CodeGenerationFrameHolder.currentFormatControlPane.setNoUserSelectionIsRequiredValue();

                            dialog.dispose();
                            CodeGenerationFrameHolder.showErrorListIfNeed("这个功能有点异常喔   (=ω=；)");
                        }
                    }
                } else if (e.getSource() == contentPane.cancelBt) {
                    dialog.dispose();
                }
            }
        };
        contentPane.okbt.addActionListener(addModuleListener);
        contentPane.cancelBt.addActionListener(addModuleListener);

        dialog.setVisible(true);
        return dialog;
    }


    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            setTooTip();
            super.mouseEntered(e);
        }
    };


    private boolean check() {
        boolean flag = true;
        if (state == createFrameState) {
            String mainNameText = mainFileNameTextField.getText().trim();
            if ("".equals(mainNameText)) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	必填模板文件名还没写喔，没法生成程序");
            } else {
                if (FileUtil.haveExOrNot(mainNameText) == true) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	默认有文件后缀了，别写后缀了");
                } else {
                    String mainFileName = mainNameText + exLabel.getText();
                    if (FileUtil.isValidFileName(mainFileName) == false
                            || StringUtil.isSpecialChar(mainNameText)) {//判断名字是否合法，并且有没有特殊字符
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	换个名称吧，这名字。。。我感觉不合适");
                    } else {
                        //在模板路径的必填模板默认路径那里看看有没有同名文件
                        File tempFolder = DatabaseFileStructure.getTemplateFolder(SysFileStructure.getDataFileFolder(), GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
                        GeneralFileFormat mainDefaultFormatFile = SysService.MAIN_FORMAT_CODE_FILE_SERVICE.getMainDefaultFormatFile();
                        if (mainDefaultFormatFile != null) {
                            String relativePath = mainDefaultFormatFile.getPath().trim();
                            if (relativePath == null) {
                                relativePath = "";
                            }
                            String path = tempFolder.getAbsolutePath();
                            if ("".equals(relativePath) == false) {
                                path = path + File.separator + relativePath;
                            }
                            File file = new File(path + File.separator + mainFileName);
                            if (file.exists() == true) {
                                flag = false;
                                LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	换个名称吧，有这文件了");
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }


    private void setTooTip() {
        ArrayList<Module> selectedModuleList = moduleSelectListPane.getSelectedModuleList();

        HTMLText htmlText = new HTMLText();
        HtmlPar par = new HtmlPar();
        par.addColorText("当前选中模块", HtmlPar.YELLOW, 3, true);
        par.nextLine();
        htmlText.addPar(par);

        for (Module module : selectedModuleList) {
            par = new HtmlPar();
            par.addColorText(module.getModuleName(), HtmlPar.Orange, true);//"#E00000"
            par.addText("模块，所属分类：", false);
            par.addColorText(module.getClassName(), HtmlPar.Munsell, true);
            par.nextLine();
            htmlText.addPar(par);
        }

        okbt.setToolTipText(htmlText.getHtmlContent());
    }


}
