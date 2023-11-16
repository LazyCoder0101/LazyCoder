package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.database.common.EncodingTypeEnum;
import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.database.model.SysParam;
import com.lazycoder.lazycodercommon.vo.ProgramingLanguage;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.GeneralSettingPaneHolder;
import com.lazycoder.uidatasourceedit.component.AbstractGetPathDialog;
import com.lazycoder.uidatasourceedit.component.EncodingCombobox;
import com.lazycoder.uidatasourceedit.component.ProgramingLanguageCombobox;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipAnimatedCarouselPane;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.UUIDUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class GeneralSettingPane extends JPanel {


    /**
     *
     */
    private static final long serialVersionUID = -4954807336026969805L;

    private static ImageIcon setPathIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "SysSettingPane" + File.separator + "DBEditPane" + File.separator + "set_path.png");

    private JTextField authorTextField, moduleCodeFilePath, moduleAttachedFilePath;
    private MyButton addTemplateBt, exportTemplateBt, addUsingDocumentButton, exportUsingDocumentBt,
            setModuleCodeFilePath, setModuleAttachedFilePath, addRenameFile, addTongyongBt, addCodeLabelBt;
    private CodeLabelPane codeLabelPane;
    private ProgramingLanguageCombobox programmingLanguageCombobox;
    private GeneralOptionPane generalOptionPane;
    private RenameFilePane renameFilePane;
    private EncodingCombobox encodingCombobox;
    private TemplatePane templatePane;
    private UsingDocumentPane usingDocumentPane;
    private JCheckBox autoPositionCheckBox;

    private OperatingTipButton exportTemplateOperatingTips, usingDocumentOperatingTips, codeLabelOperatingTips, renameProFileOperatingTips, generalOptionOperatingTips;

    private JTextArea sysNoteTextArea;

    private MyButton guideButton;

    public GeneralSettingPane() {
        setLayout(null);
        Dimension dd = SysUtil.SCREEN_SIZE;
        this.setBounds(0, 0, 1366, 768);

//        MyFlatButtonUI buttonUI = new MyFlatButtonUI();
        addTemplateBt = new MyButton("添加模版");
//        addModuleBt.setUI(buttonUI);
        addTemplateBt.addActionListener(listener);
        addTemplateBt.setBounds((int) (0.0344 * dd.width), (int) (0.04 * dd.height), 100, 30);
        add(addTemplateBt);

        exportTemplateBt = new MyButton("导出模板查看");
//        exportModuleBt.setUI(buttonUI);
        exportTemplateBt.addActionListener(listener);
        exportTemplateBt.setBounds(addTemplateBt.getLocation().x + addTemplateBt.getWidth() + 60, (int) (0.04 * dd.height), 130, 27);
        add(exportTemplateBt);

        exportTemplateOperatingTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "通用管理" + File.separator + "添加模版")
                .getAbsolutePath());
        exportTemplateOperatingTips.setLocation(exportTemplateBt.getX() + exportTemplateBt.getWidth() + 50, exportTemplateBt.getY());
        add(exportTemplateOperatingTips);

        // 添加模板
        templatePane = new TemplatePane();
        templatePane.setBounds((int) (0.0344 * dd.width), (int) (0.09 * dd.height), (int) (0.25 * dd.width),
                (int) (0.4 * dd.height));
        add(templatePane);

        addUsingDocumentButton = new MyButton("添加使用文档");
//        addUsingDocumentButton.setUI(buttonUI);
        addUsingDocumentButton.addActionListener(listener);
        addUsingDocumentButton.setBounds((int) (0.0344 * dd.width), (int) (0.54 * dd.height), 130, 30);
        add(addUsingDocumentButton);

        exportUsingDocumentBt = new MyButton("导出文档查看");
//        exportUsingDocumentBt.setUI(buttonUI);
        exportUsingDocumentBt.addActionListener(listener);
        exportUsingDocumentBt.setBounds(addUsingDocumentButton.getLocation().x + addUsingDocumentButton.getWidth() + 50, (int) (0.54 * dd.height), 130, 30);
        add(exportUsingDocumentBt);

        usingDocumentOperatingTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "通用管理" + File.separator + "使用文档")
                .getAbsolutePath());
        usingDocumentOperatingTips.setLocation(exportUsingDocumentBt.getX() + exportUsingDocumentBt.getWidth() + 50, exportUsingDocumentBt.getY());
        add(usingDocumentOperatingTips);

        usingDocumentPane = new UsingDocumentPane();
        JScrollPane usingDocumentPaneJsp = new JScrollPane(usingDocumentPane);
        usingDocumentPaneJsp.setBounds((int) (0.0344 * dd.width), (int) (0.59 * dd.height), (int) (0.25 * dd.width),
                (int) (0.16 * dd.height));
        add(usingDocumentPaneJsp);

        addCodeLabelBt = new MyButton("添加代码标签");
//        addCodeLabelBt.setUI(buttonUI);
        addCodeLabelBt.addActionListener(listener);
        addCodeLabelBt.setBounds((int) (0.0344 * dd.width), (int) (0.79 * dd.height), 130, 30);
        add(addCodeLabelBt);

        codeLabelOperatingTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "通用管理" + File.separator + "代码标签")
                .getAbsolutePath());
        codeLabelOperatingTips.setLocation(addCodeLabelBt.getX() + 30, addCodeLabelBt.getY() + addCodeLabelBt.getHeight() + 20);
        add(codeLabelOperatingTips);

        codeLabelPane = new CodeLabelPane();
        JScrollPane codeLabelPaneJsp = new JScrollPane(codeLabelPane);
        codeLabelPaneJsp.setBounds(addCodeLabelBt.getLocation().x + addCodeLabelBt.getWidth() + 20, (int) (0.79 * dd.height), (int) (0.19 * dd.width), (int) (0.25 * dd.height));
        add(codeLabelPaneJsp);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds((int) (0.35 * dd.width), 0, (int) (0.4165 * dd.width), 9999);
        add(separator);

        JLabel aiLabel = new JLabel("作者信息：");
        aiLabel.setBounds((int) (0.4 * dd.width), (int) (0.04 * dd.height), 166, 30);
        add(aiLabel);

        authorTextField = new JTextField(SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.AUTHOR));
        authorTextField.addFocusListener(focusListener);
        authorTextField.setBounds(aiLabel.getLocation().x + 176, aiLabel.getLocation().y, 350, 30);
        add(authorTextField);

        JLabel snLabel = new JLabel("项目备注：");
        snLabel.setBounds(aiLabel.getLocation().x, aiLabel.getLocation().y + aiLabel.getHeight() + 20, 166, 30);
        add(snLabel);
        sysNoteTextArea = new JTextArea(SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.DATA_SOURCE_ANNOTATION));
        sysNoteTextArea.addFocusListener(focusListener);
        JScrollPane sysNoteScrollPane = new JScrollPane(sysNoteTextArea);
        sysNoteScrollPane.setBounds(aiLabel.getLocation().x + 176, snLabel.getLocation().y, 350, 60);
        add(sysNoteScrollPane);

        JLabel plLabel = new JLabel("使用编程语言：");
        plLabel.setBounds(aiLabel.getLocation().x, snLabel.getLocation().y + sysNoteScrollPane.getHeight() + 20, 166, 30);
        add(plLabel);
        programmingLanguageCombobox = new ProgramingLanguageCombobox();
        ProgramingLanguage useProgramingLanguage = SysService.SYS_PARAM_SERVICE.getUseProgramingLanguage();
        programmingLanguageCombobox.setSelectedItem(useProgramingLanguage);
        programmingLanguageCombobox.addPopupMenuListener(popupMenuListener);
        programmingLanguageCombobox.setBounds(aiLabel.getLocation().x + 176, plLabel.getLocation().y, 150, 30);
        add(programmingLanguageCombobox);

        JLabel mppLabel = new JLabel("默认编码格式：");
        mppLabel.setBounds(aiLabel.getLocation().x, plLabel.getLocation().y + plLabel.getHeight() + 20, 166, 30);
        add(mppLabel);
        encodingCombobox = new EncodingCombobox();
        EncodingTypeEnum useEncodingType = SysService.SYS_PARAM_SERVICE.getEncoding();
        encodingCombobox.setSelectedItem(useEncodingType);
        encodingCombobox.addPopupMenuListener(popupMenuListener);
        encodingCombobox.setBounds(authorTextField.getLocation().x, mppLabel.getLocation().y, 150, 30);
        add(encodingCombobox);

        JLabel mcfLabel = new JLabel("模块代码文件常用路径：");
        mcfLabel.setBounds(aiLabel.getLocation().x, mppLabel.getLocation().y + mppLabel.getHeight() + 20, 166, 30);
        add(mcfLabel);
        moduleCodeFilePath = new JTextField(SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.MODULE_CODE_FILE_PATH));
        moduleCodeFilePath.setBounds(aiLabel.getLocation().x + 176, mcfLabel.getLocation().y, 200, 30);
        moduleCodeFilePath.setEnabled(false);
        moduleCodeFilePath.setEditable(false);
        moduleCodeFilePath.setBackground(Color.white);
        add(moduleCodeFilePath);
        setModuleCodeFilePath = new MyButton("设置模块代码文件常用路径", setPathIcon);
//        setModuleCodeFilePath.setUI(buttonUI);
        setModuleCodeFilePath.addActionListener(listener);
        setModuleCodeFilePath.setHorizontalAlignment(JButton.LEFT);
        setModuleCodeFilePath.setBounds(moduleCodeFilePath.getLocation().x + 230, mcfLabel.getLocation().y, 200,
                30);
        add(setModuleCodeFilePath);

        JLabel mafLabel = new JLabel("模块附加文件常用路径：");
        mafLabel.setBounds(aiLabel.getLocation().x, mcfLabel.getLocation().y + mcfLabel.getHeight() + 20, 166, 30);
        add(mafLabel);
        moduleAttachedFilePath = new JTextField(
                SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.MODULE_ATTACHED_FILE_PATH));
        moduleAttachedFilePath.setBounds(authorTextField.getLocation().x, mafLabel.getLocation().y, 200, 30);
        moduleAttachedFilePath.setEnabled(false);
        moduleAttachedFilePath.setEditable(false);
        moduleAttachedFilePath.setBackground(Color.white);
        add(moduleAttachedFilePath);
        setModuleAttachedFilePath = new MyButton("设置模块附加文件常用路径", setPathIcon);
//        setModuleAttachedFilePath.setUI(buttonUI);
        setModuleAttachedFilePath.addActionListener(listener);
        setModuleAttachedFilePath.setHorizontalAlignment(JButton.LEFT);
        setModuleAttachedFilePath.setBounds(setModuleCodeFilePath.getLocation().x, mafLabel.getLocation().y,
                200, 30);
        add(setModuleAttachedFilePath);

        JLabel label = new JLabel("重命名为工程名的模板文件：");
        label.setBounds(aiLabel.getLocation().x, mafLabel.getLocation().y + mafLabel.getHeight() + 15, 198, 30);
        add(label);

        // 重命名
        renameFilePane = new RenameFilePane();
        GeneralSettingPaneHolder.renameFilePane = renameFilePane;
        JScrollPane renameScrollPane = new JScrollPane(renameFilePane);
        renameScrollPane.setBounds(authorTextField.getLocation().x, label.getLocation().y + label.getHeight() + 5,
                (int) (0.2 * dd.width), 130);
        add(renameScrollPane);

        addRenameFile = new MyButton("添加重命名文件");
//        addRenameFile.setUI(buttonUI);
        addRenameFile.addActionListener(listener);
        addRenameFile.setBounds(aiLabel.getLocation().x, label.getLocation().y + label.getHeight() + 10, 150, 30);
        add(addRenameFile);

        renameProFileOperatingTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "通用管理" + File.separator + "重命名文件")
                .getAbsolutePath());
        renameProFileOperatingTips.setLocation(addRenameFile.getX() + 30, addRenameFile.getY() + addRenameFile.getHeight() + 20);
        add(renameProFileOperatingTips);

        autoPositionCheckBox = new JCheckBox("自动定位到对应位置");
        autoPositionCheckBox.setFocusPainted(false);

        boolean ff = SysService.SYS_PARAM_SERVICE.getAutoPositionState();
        autoPositionCheckBox.setSelected(ff);
        DataSourceEditHolder.autoPositionCheckBox = autoPositionCheckBox;
        autoPositionCheckBox.addItemListener(generalSettingItemListener);
        autoPositionCheckBox.setBounds(renameProFileOperatingTips.getLocation().x, renameScrollPane.getLocation().y + renameScrollPane.getHeight() + 30, 150, 30);
        add(autoPositionCheckBox);

        addTongyongBt = new MyButton("添加通用选项");
//        addTongyongBt.setUI(buttonUI);
        addTongyongBt.setBounds(aiLabel.getLocation().x, autoPositionCheckBox.getLocation().y + autoPositionCheckBox.getHeight() + 30, 150, 30);
        addTongyongBt.addActionListener(listener);
        add(addTongyongBt);

        generalOptionOperatingTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "通用管理" + File.separator + "通用选项")
                .getAbsolutePath());
        generalOptionOperatingTips.setLocation(addTongyongBt.getLocation().x + 30, addTongyongBt.getLocation().y + addTongyongBt.getHeight() + 20);
        add(generalOptionOperatingTips);

        // 通用选项
        generalOptionPane = new GeneralOptionPane();
        GeneralSettingPaneHolder.generalOptionPane = generalOptionPane;
        JScrollPane tongyongScrollPane = new JScrollPane(generalOptionPane);
        tongyongScrollPane.setBounds(authorTextField.getLocation().x, addTongyongBt.getY(),
                (int) (0.19 * dd.width), (int) (0.25 * dd.height));
        add(tongyongScrollPane);

        guideButton = new MyButton("新手指引");
        guideButton.setBounds(separator.getX() - 90, exportTemplateBt.getY(), 80, 30);
        guideButton.addActionListener(guideListener);
        add(guideButton);
    }

    private ActionListener guideListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            OperatingTipAnimatedCarouselPane.creatHelpAnimatedCarouselFrame(
                    SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "开发者新手指引")
                            .getAbsolutePath()
            );
        }
    };

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addTongyongBt) {
                new ContentChooseFrameAddForBaseControlTextFrame();
            } else if (e.getSource() == addRenameFile) {
                new RenameFileSelectDialog();
            } else if (e.getSource() == setModuleCodeFilePath) {
                new ModuleCodeFilePathDialog();
            } else if (e.getSource() == setModuleAttachedFilePath) {
                new ModuleAttachedFilePathDialog();
            } else if (e.getSource() == exportTemplateBt) {
                exportTemplate();

            } else if (e.getSource() == exportUsingDocumentBt) {
                exportUsingDocument();

            } else if (e.getSource() == addTemplateBt) {
                addModule();

            } else if (e.getSource() == addUsingDocumentButton) {
                addUsingDocument();

            } else if (e.getSource() == addCodeLabelBt) {
                addCodeLabel();
            }
        }
    };

    private FocusListener focusListener = new FocusListener() {

        @Override
        public void focusLost(FocusEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == authorTextField) {
                SysService.SYS_PARAM_SERVICE.updateSysParam(SysParam.AUTHOR, authorTextField.getText());
            } else if (e.getSource() == sysNoteTextArea) {
                SysService.SYS_PARAM_SERVICE.updateSysParam(SysParam.DATA_SOURCE_ANNOTATION,
                        sysNoteTextArea.getText());
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            // TODO Auto-generated method stub

        }
    };

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == programmingLanguageCombobox) {
                SysService.SYS_PARAM_SERVICE.setUseProgramingLanguage(programmingLanguageCombobox.getSelectedItem());
            } else if (e.getSource() == encodingCombobox) {
                SysService.SYS_PARAM_SERVICE.setEncoding(encodingCombobox.getSelectedItem());
            }
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub
        }
    };

    private ItemListener generalSettingItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == autoPositionCheckBox) {
                SysService.SYS_PARAM_SERVICE.setAutoPositionState(autoPositionCheckBox.isSelected());
            }
        }
    };


    private void addCodeLabel() {
        String showText = LazyCoderOptionPane.showInputDialog(null, "请输入标签名称名称：\n", "添加标签名称", JOptionPane.PLAIN_MESSAGE);
        if (showText != null && ("".equals(showText.trim()) == false)) {
            showText = showText.trim();
            String codeLabelId = UUIDUtil.getUUID();
            CodeLabel codeLabel = new CodeLabel(codeLabelId, showText);
            SysService.CODE_LABEL_SERVICE.addCodeLabel(codeLabel);
            LazyCoderOptionPane.showMessageDialog(null, "(*^▽^*) 已添加对应标签");
            codeLabelPane.updateList();
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o 添加标签时，标签名称一定要写的");
        }
    }

    /**
     * 添加模板
     */
    private void addModule() {
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();

        File[] fileList = FileUtil.selectFileList(FileUtil.FILE_AND_DIRECTORY_MODEL, "请选择生成代码时需要生成的模版文件");
        File toFile;
        if (fileList != null && fileList.length > 0) {
            for (File temp : fileList) {
                if (temp.isFile() == true) {
                    toFile = new File(DatabaseFileStructure
                            .getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()).getAbsolutePath()
                            + File.separator + temp.getName());
                    try {
                        FileUtil.fileCopyNormal(temp, toFile);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (temp.isDirectory() == true) {
                    try {
                        FileUtil.copyDir(temp.getAbsolutePath(),
                                DatabaseFileStructure
                                        .getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName())
                                        .getAbsolutePath());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            templatePane.updatePane();
        }
    }

    /**
     * 添加使用文档
     */
    private void addUsingDocument() {
        File[] files = FileUtil.selectFileList(FileUtil.FILE_ONLY_MODEL, "请选择生成代码时需要给用户观看的使用文档");
        File sysDataFileFolder = SysFileStructure.getDataFileFolder(), toFile;
        if (files != null || files.length > 0) {
            for (File temp : files) {
                if (temp.isFile() == true) {
                    toFile = new File(
                            DatabaseFileStructure.getUserDocFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName())
                                    .getAbsolutePath() + File.separator + temp.getName());
                    try {
                        FileUtil.fileCopyNormal(temp, toFile);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            usingDocumentPane.updateList();
        }
    }

    /**
     * 导出模板里面的文件
     */
    private void exportTemplate() {
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();
        File[] fileList = DatabaseFileStructure.getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName())
                .listFiles();
        if (fileList == null || fileList.length == 0) {
            LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，你还没添加生成源码的模板啊", "系统信息", JOptionPane.PLAIN_MESSAGE);
        } else {
            File toFolder = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL, "请选择需要导出的路径");
            if (toFolder != null) {
                try {
                    FileUtil.copyDirIn(DatabaseFileStructure
                                    .getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()).getAbsolutePath(),
                            toFolder.getAbsolutePath());
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   已导出模板文件！", "系统信息", JOptionPane.PLAIN_MESSAGE);
                    File[] tempList = toFolder.listFiles();
                    if (tempList != null) {
                        if (tempList.length == 1) {
                            FileUtil.openAndSelect(tempList[0]);
                        } else if (tempList.length > 1) {
                            Desktop.getDesktop().open(toFolder);
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出使用文档里面的文件
     */
    private void exportUsingDocument() {
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();
        File[] fileList = DatabaseFileStructure.getUserDocFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName())
                .listFiles();
        if (fileList == null || fileList.length == 0) {
            LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，你还没添加这个数据源的使用文档啊", "系统信息", JOptionPane.PLAIN_MESSAGE);
        } else {
            File toFolder = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL, "请选择需要导出的路径");
            if (toFolder != null) {
                try {
                    FileUtil.copyDirIn(DatabaseFileStructure
                                    .getUserDocFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()).getAbsolutePath(),
                            toFolder.getAbsolutePath());
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   已导出使用文档！", "系统信息", JOptionPane.PLAIN_MESSAGE);
                    File[] tempList = toFolder.listFiles();
                    if (tempList != null) {
                        if (tempList.length == 1) {
                            FileUtil.openAndSelect(tempList[0]);
                        } else if (tempList.length > 1) {
                            Desktop.getDesktop().open(toFolder);
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class ModuleCodeFilePathDialog extends AbstractGetPathDialog {

        /**
         *
         */
        private static final long serialVersionUID = -4410837919611262941L;

        public ModuleCodeFilePathDialog() {
            // TODO Auto-generated constructor stub
            super(moduleCodeFilePath.getText(), "生成源码目录");
            setTitle("选择的路径将作为添加的模块，其代码文件的默认路径");
            setVisible(true);
        }

        @Override
        protected void ok() {
            // TODO Auto-generated method stub
            moduleCodeFilePath.setText(getPathStr());
            SysService.SYS_PARAM_SERVICE.updateSysParam(SysParam.MODULE_CODE_FILE_PATH, getPathStr());
            this.dispose();
        }

    }

    class ModuleAttachedFilePathDialog extends AbstractGetPathDialog {

        /**
         *
         */
        private static final long serialVersionUID = -3624967013038206618L;

        public ModuleAttachedFilePathDialog() {
            // TODO Auto-generated constructor stub
            super(moduleAttachedFilePath.getText(), "生成源码目录");
            setTitle("选择的路径将作为添加的模块，其附带文件的默认路径");
            setVisible(true);
        }

        @Override
        protected void ok() {
            // TODO Auto-generated method stub
            moduleAttachedFilePath.setText(getPathStr());
            SysService.SYS_PARAM_SERVICE.updateSysParam(SysParam.MODULE_ATTACHED_FILE_PATH, getPathStr());
            this.dispose();
        }

    }
}
