package com.lazycoder.uicodegeneration.generalframe.palette.additional;

import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFeatureSelection;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFunctionFeatureSelectionModel;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.functionadd.FunctionAddCodeControlPane;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalFunctionOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.BusinessLogicCodeControlPane;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AdditionalOperationPane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 3441017022288841694L;

    private JPopupMenu menu;
    private JMenu functionMenu;
    private JMenuItem addAdditionalFormatControlPaneMenuItem;

    private Box vBox;

    /**
     * Create the panel.
     */
    public AdditionalOperationPane() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        this.setLayout(fl);
        vBox = Box.createVerticalBox();
        this.add(vBox);
        updateList();
        addMenu();
        this.setBackground(Color.white);
//        this.setSize(200, 500);
        this.setVisible(true);
    }

    private void updateList() {
        vBox.removeAll();
        List<AdditionalFeatureSelection> selectionList = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalFeatureSelectionList();
        if (selectionList != null) {
            AdditionalSelectionLabel label;
            for (AdditionalFeatureSelection featureSelectionModelTemp : selectionList) {
                label = new AdditionalSelectionLabel(featureSelectionModelTemp);
                label.addMouseListener(thisMouse);
                vBox.add(label);
//                vBox.add(Box.createVerticalStrut(10));
            }
        }
//        this.setSize(200, 500);
        this.repaint();
    }

    private void addMenu() {
        menu = new JPopupMenu();
        addAdditionalFormatControlPaneMenuItem = new JMenuItem("添加该可选模板面板");
        addAdditionalFormatControlPaneMenuItem.setForeground(Color.blue);
        addAdditionalFormatControlPaneMenuItem.addActionListener(menuItemListener);
        functionMenu = new JMenu("功能");
    }

    /**
     * 每次从数据库搜索对应模块的方法 待改
     *
     * @param label
     */
    public void addMenu(AdditionalSelectionLabel label) {
        functionMenu.setEnabled(true);
        menu.removeAll();
        functionMenu.removeAll();
        menu.add(addAdditionalFormatControlPaneMenuItem);// 加载添加这个格式的菜单
        addAdditionalFormatControlPaneMenuItem.setEnabled(true);

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            ArrayList<AdditionalOperating> formatControlPaneLableList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllAddedAdditionalOperatingList();
            for (AdditionalOperating additionalOperating : formatControlPaneLableList) {
                if (additionalOperating.getAdditionalSerialNumber() == label.getAdditionalFeatureSelection().getAdditionalSerialNumber()) {
                    if (additionalOperating.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue() ||
                            additionalOperating.getSetProperty() == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue() ||
                            additionalOperating.getSetProperty() == FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue()) {
                        addAdditionalFormatControlPaneMenuItem.setEnabled(false);
                    }
                }
            }
        }

        menu.add(functionMenu);// 方法菜单

        AdditionalFunctionOpratingContainer additionalFunctionOpratingContainer;
        ArrayList<OpratingContainerInterface> opratingContainerList =
                CodeGenerationFrameHolder.currentAdditiveMethodCodePane == null ?
                        new ArrayList<>() : CodeGenerationFrameHolder.currentAdditiveMethodCodePane.getAllOpratingContainerListInThisPane();


        AdditionalFeatureSelection featureSelection = label.getAdditionalFeatureSelection();
        List<AdditionalFunctionFeatureSelectionModel> additionalFunctionFeatureList = SysService.ADDITIONAL_FUNCTION_SERVICE
                .getFeatureList(featureSelection.getAdditionalSerialNumber());
        if (additionalFunctionFeatureList != null) {
            AdditionalFunctionFeatureMenuItem menuItem;
            for (AdditionalFunctionFeatureSelectionModel additionalFunctionFeatureSelectionModel : additionalFunctionFeatureList) {
                menuItem = new AdditionalFunctionFeatureMenuItem(additionalFunctionFeatureSelectionModel);
                functionMenu.add(menuItem);

                if (CodeGenerationFrameHolder.currentAdditiveMethodCodePane != null &&
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane instanceof BusinessLogicCodeControlPane) {//业务逻辑面板
                    if (additionalFunctionFeatureSelectionModel.getSetProperty() == FunctionUseProperty.onlyAddedItOnceToBusinessSchtcheonOfATemplate.getSysDictionaryValue()) {
                        //如果属性为添加到该面板，且只能使用一次。。。看看当前有没有添加，有的话菜单失能
                        for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                            if (opratingContainer instanceof AdditionalFunctionOpratingContainer) {
                                additionalFunctionOpratingContainer = (AdditionalFunctionOpratingContainer) opratingContainer;
                                if (additionalFunctionFeatureSelectionModel.getOrdinal() == additionalFunctionOpratingContainer.getOrdinalInUserDB()) {
                                    menuItem.setEnabled(false);
                                    break;
                                }
                            }
                        }
                    } else if (additionalFunctionFeatureSelectionModel.getSetProperty() == FunctionUseProperty.onlyBeAddedToFunctionAddLabel.getSysDictionaryValue()) {
                        //只能添加到方法组件的，菜单失能
                        menuItem.setEnabled(false);
                    }
                } else if (CodeGenerationFrameHolder.currentAdditiveMethodCodePane != null &&
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane instanceof FunctionAddCodeControlPane) {//当前添加方法的面板是功能拓展组件的面板

                    if (additionalFunctionFeatureSelectionModel.getSetProperty() == FunctionUseProperty.onlyAddedItOnceToBusinessSchtcheonOfATemplate.getSysDictionaryValue() ||
                            additionalFunctionFeatureSelectionModel.getSetProperty() == FunctionUseProperty.onlyBeAddedToBusinessSchtcheonOfATemplate.getSysDictionaryValue()) {
                        //如果属性是只能添加到业务方法面板那两个，菜单失能
                        menuItem.setEnabled(false);
//                            }else if (featureSelectionModel.getSetProperty() == FunctionUseProperty.onlyBeAddedToFunctionAddLabel.getSysDictionaryValue()) {
                    }
                }
            }

            if(additionalFunctionFeatureList.size()==0){
                functionMenu.setEnabled(false);
            }
        }else {
            functionMenu.setEnabled(false);
        }
        // MyOptionPane.showMessageDialog(null, "无法获取数据，请查看是否选中正确的数据库");
    }

    private MouseAdapter thisMouse = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            AdditionalSelectionLabel theLabel = (AdditionalSelectionLabel) e.getSource();

            addMenu(theLabel);
            menu.show(theLabel, e.getX(), e.getY());
        }
    };

    private ActionListener menuItemListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addAdditionalFormatControlPaneMenuItem) {
                openGetFileNameDialog();
            }
        }
    };


    /**
     * 打开获取文件名窗口，让用户输入文件名
     */
    private void openGetFileNameDialog() {
        AdditionalSelectionLabel selectionLabel = (AdditionalSelectionLabel) menu.getInvoker();
        int additionalSerialNumber = selectionLabel.getAdditionalFeatureSelection().getAdditionalSerialNumber();
        GeneralFileFormat defaultCodeFormat = SysService.ADDITIONAL_FORMAT_FILE_SERVICE
                .getAdditionalDeafaultFormatCodeFile(additionalSerialNumber);
        if (defaultCodeFormat != null) {
            new GetFileNameDialog(FileUtil.getFileNameNoEx(defaultCodeFormat.getFileName()),
                    FileUtil.getFileEx(defaultCodeFormat.getFileName()), additionalSerialNumber);
        }
    }


    class GetFileNameDialog extends LazyCoderCommonDialog {

        /**
         *
         */
        private static final long serialVersionUID = 613076864153477545L;

        protected JTextField fileNametextField;

        protected JLabel suffixLabel;

        protected MyButton okButton, cancelButton;

        private int additionalSerialNumber = 0;//要添加的可选模板的序号

        public GetFileNameDialog(String fileNameNoEx, String suffix, int additionalSerialNumber) {
            // TODO Auto-generated constructor stub
            setTitle("文件名输入");
            // 显示对话框（setVisible()方法会阻塞，直到对话框关闭）

            this.additionalSerialNumber = additionalSerialNumber;
            JPanel panel = new JPanel();
            setContentPane(panel);
            panel.setLayout(null);

            JLabel label1 = new JLabel("请输入要添加的文件名：\n");
            label1.setBounds(20, 20, 165, 30);
            panel.add(label1);

            // 添加控件到对话框
            fileNametextField = new JTextField();
            fileNametextField.setBounds(185, 20, 105, 30);
            fileNametextField.setText(fileNameNoEx);
            panel.add(fileNametextField);

            suffixLabel = new JLabel(suffix);
            suffixLabel.setBounds(295, 20, 60, 30);
            panel.add(suffixLabel);

            okButton = new MyButton("确定");
            okButton.setBounds(70, 70, 80, 30);
            panel.add(okButton);
            // 点击按钮时，关闭对话框
            okButton.addActionListener((e) -> {
                if (check() == true) {
                    CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空

                    String fileName = fileNametextField.getText().trim() + suffixLabel.getText();
                    AdditionalFormatControlPane additionalFormatControlPane = CodeGenerationFrameHolder.codeControlTabbedPane.addAdditionalRelatedContent(fileName,
                            additionalSerialNumber, true);
                    this.dispose();

                    //把不需要用户设置的值都自动选上
                    ArrayList<OpratingContainerInterface> allOpratingContainer = additionalFormatControlPane.getAllOpratingContainer();
                    for (OpratingContainerInterface opratingContainer : allOpratingContainer) {
                        opratingContainer.setNoUserSelectionIsRequiredValue();
                    }
                    CodeGenerationFrameHolder.showErrorListIfNeed("这个面板有点异常喔   (=ω=；)");

                }
            });

            cancelButton = new MyButton("取消");
            cancelButton.setBounds(180, 70, 80, 30);
            panel.add(cancelButton);
            // 点击按钮时，关闭对话框
            cancelButton.addActionListener((e) -> {
                this.dispose();
            });

            Dimension dimension = SysUtil.SCREEN_SIZE;
            setBounds(dimension.width / 2 - 190, dimension.height / 2 - 150, 380, 150);
            setVisible(true);
        }

        private boolean check() {
            boolean flag = true;
            String wriContent = fileNametextField.getText().trim();
            if ("".equals(wriContent)) {//判断有没有写内容
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "┗( ▔, ▔ )┛	文件名还没写喔，没法生成程序");
            } else {
                if (FileUtil.haveExOrNot(wriContent) == true) {//判断有没有写后缀
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "┗( ▔, ▔ )┛	 已经知道后缀了，写上文件叫什么名字就好了");
                } else {
                    String mainFileName = wriContent + suffixLabel.getText();
                    if (FileUtil.isValidFileName(mainFileName) == false
                            || StringUtil.isSpecialChar(fileNametextField.getText().trim())) {//判断文件名是否合法，有没有特殊字符
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null, "┗( ▔, ▔ )┛	换个名称吧，这名字。。。我感觉不合适");
                    } else {
                        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {//和现有的文件名比对，看有没有重名
                            String fileName = wriContent + suffixLabel.getText();
                            if (CodeGenerationFrameHolder.codeControlTabbedPane.checkHaveTheFileName(fileName) == true) {//检查代码控制面板，有没有和它同名的
                                flag = false;
                                LazyCoderOptionPane.showMessageDialog(null, "┗( ▔, ▔ )┛	看清楚嘛，之前起过这个文件名了，换个名字吧");
                            } else {

                                //查看这个可选模板的代码文件是要在哪里生成的，有没有同名文件，有同名文件不允许起这个名字
                                GeneralFileFormat additionalDeafaultFormatCodeFile = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalDeafaultFormatCodeFile(this.additionalSerialNumber);
                                if (additionalDeafaultFormatCodeFile != null) {
                                    String relativePath = additionalDeafaultFormatCodeFile.getPath().trim();
                                    if (relativePath == null) {
                                        relativePath = "";
                                    }
                                    String path = SourceGenerateFileStructure.getTheProjectPathInGenerateSourcePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName).getAbsolutePath();
                                    if ("".equals(relativePath) == false) {
                                        path = path + File.separator + relativePath;
                                    }
                                    File file = new File(path + File.separator + fileName);
                                    if (file.exists()) {
                                        flag = false;
                                        LazyCoderOptionPane.showMessageDialog(null, "┗( ▔, ▔ )┛	换个名字吧，有这文件了");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return flag;
        }

    }

}
