package com.lazycoder.uidatasourceedit;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.service.vo.datasourceedit.format.CorrespondingFormatCodePaneInterface;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.service.vo.element.mark.HarryingMark;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.service.vo.element.mark.MainSetMarkElement;
import com.lazycoder.service.vo.element.mark.MarkStaticMethod;
import com.lazycoder.service.vo.element.mark.SetMarkElement;
import com.lazycoder.uidatasourceedit.component.AbstractGetPathDialog;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.BaseFunctionTextPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.AdditionalDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.MainDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.ModuleFileFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.AdditionalSetMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.BaseMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.FunctionMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.ImportMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.InitMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.MainSetMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.MarkComponentInterface;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.SetMarkScutcheon;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalFunctionPane;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipMenuItem;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.utils.EncodingAsUTF8;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用格式代码输入面板
 *
 * @author Administrator
 */
public abstract class AbstractFormatCodeInputPane extends BaseFunctionTextPane implements CorrespondingFormatCodePaneInterface, BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 6116974313575207431L;

    @Getter
    @Setter
    private String codeFileId;

    /**
     * 文件参数
     */
    @Getter
    @Setter
    protected CodeFormatFlagParam fileParam = null;

    protected JMenu addMarkmenu = new JMenu("添加标记");

    private JMenuItem importMarkMenuItem, initMarkMenuItem, setMarkMenuItem, functionMarkMenuItem, setCurrentCodeFilePathMenuItem;

    private JCheckBoxMenuItem codeFormatOrNotMenuItem;

    private OperatingTipMenuItem operatingTipMenuItem;

    /**
     * 存储路径
     */
    @Getter
    private String path = "";

    protected AbstractFormatCodeInputPane(int serialNumber) {
        super(serialNumber);
        setPath(path);
    }

    protected void setMenuItemText() {
        HTMLText importHtmlText = new HTMLText();
        HtmlPar importPar = new HtmlPar();
        importPar.addText("在此填写", false);
        importPar.addColorText("引入", HtmlPar.RED, false);
        importPar.addText("代码", false);
        importHtmlText.addPar(importPar);
        importMarkMenuItem = new JMenuItem(importHtmlText.getHtmlContent());

        HTMLText initHtmlText = new HTMLText();
        HtmlPar initPar = new HtmlPar();
        initPar.addText("在此填写", false);
        initPar.addColorText("初始化", HtmlPar.RED, false);
        initPar.addText("代码", false);
        initHtmlText.addPar(initPar);
        initMarkMenuItem = new JMenuItem(initHtmlText.getHtmlContent());

        HTMLText setMarkHtmlText = new HTMLText();
        HtmlPar setMarkPar = new HtmlPar();
        setMarkPar.addText("在此填写", false);
        setMarkPar.addColorText("设置", HtmlPar.RED, false);
        setMarkPar.addText("代码", false);
        setMarkHtmlText.addPar(setMarkPar);
        setMarkMenuItem = new JMenuItem(setMarkHtmlText.getHtmlContent());
    }


    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == importMarkMenuItem) {
                ImportMarkScutcheon importMarkScutcheon = new ImportMarkScutcheon();
                AbstractFormatCodeInputPane.this.insertComponent(importMarkScutcheon);

                if (ModuleEditPaneHolder.importCodeFolder != null) {
                    ModuleEditPaneHolder.importCodeFolder.setUIResponse(false);
                }

            } else if (e.getSource() == initMarkMenuItem) {
                InitMarkScutcheon initMarkScutcheon = new InitMarkScutcheon();
                AbstractFormatCodeInputPane.this.insertComponent(initMarkScutcheon);

                if (ModuleEditPaneHolder.initCodeFolder != null) {
                    ModuleEditPaneHolder.initCodeFolder.setUIResponse(false);
                }
            } else if (e.getSource() == setMarkMenuItem) {
                SetMarkScutcheon scutcheon = new SetMarkScutcheon();
                AbstractFormatCodeInputPane.this.insertComponent(scutcheon);

                if (ModuleEditPaneHolder.moduleSetCodeEditPane != null) {
                    ModuleEditPaneHolder.moduleSetCodeEditPane.setUIResponse(false);
                }
            } else if (e.getSource() == functionMarkMenuItem) {
                FunctionMarkScutcheon functionMarkScutcheon = new FunctionMarkScutcheon();
                AbstractFormatCodeInputPane.this.insertComponent(functionMarkScutcheon);

                if (ModuleEditPaneHolder.moduleFunctionEditPane != null) {
                    ModuleEditPaneHolder.moduleFunctionEditPane.setUIResponse(false);
                }
                if (AbstractFormatCodeInputPane.this instanceof AdditionalDeafaultFormatCodePane) {
                    int additionalSerialNumber = ((AdditionalDeafaultFormatCodePane) AbstractFormatCodeInputPane.this).getAdditionalSerialNumber();
                    if (FormatEditPaneHolder.additionalEditPane != null && additionalSerialNumber != 0) {
                        AdditionalFunctionPane additionalFunctionPane = FormatEditPaneHolder.additionalEditPane.getAdditionalFunctionPane(additionalSerialNumber);
                        if (additionalFunctionPane != null) {
                            additionalFunctionPane.setUIResponse(false);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void showPopupMenu(Component invoker, int x, int y) {
        // TODO Auto-generated method stub
        if (DataSourceEditHolder.dataSourceEditFrame.getCurrentSelectedIndex() == 3) {// 如果当前选中的是模块内容编辑面板
            if (this instanceof ModuleFileFormatCodePane && DataSourceEditHolder.currentModule != null) {
                ModuleFileFormatCodePane moduleFileFormatCodePane = (ModuleFileFormatCodePane) this;
                if (moduleFileFormatCodePane.getFileParam().getModuleId().equals(DataSourceEditHolder.currentModule.getModuleId()) == false) {
                    setAsNeedUseInputCodeFilePane();
                }
            } else {
                setAsNeedUseInputCodeFilePane();
            }
        } else if (DataSourceEditHolder.dataSourceEditFrame.getCurrentSelectedIndex() == 1) {
            setAsGeneralInputCodeFilePane();
        }
        super.showPopupMenu(invoker, x, y);
    }

    @Override
    public void menuInit(CodeCondition codeCondition) {
        super.menuInit(codeCondition);
        theMenu.add(addMarkmenu, 1);
        setCurrentCodeFilePathMenuItem = new JMenuItem("设置当前代码文件路径");
        setCurrentCodeFilePathMenuItem.addActionListener(setCurrentCodeFilePathActionListener);
        theMenu.add(setCurrentCodeFilePathMenuItem, 2);

        codeFormatOrNotMenuItem = new JCheckBoxMenuItem("自动代码格式化", true);
        theMenu.add(codeFormatOrNotMenuItem, 3);

        setMenuItemText();
        addMarkmenu.add(importMarkMenuItem);
        importMarkMenuItem.addActionListener(listener);
        addMarkmenu.add(initMarkMenuItem);
        initMarkMenuItem.addActionListener(listener);
        addMarkmenu.add(setMarkMenuItem);
        setMarkMenuItem.addActionListener(listener);

        importMarkMenuItem.addMouseListener(markMenuItemMouseAdapter);
        initMarkMenuItem.addMouseListener(markMenuItemMouseAdapter);
        setMarkMenuItem.addMouseListener(markMenuItemMouseAdapter);

        if (this instanceof MainDeafaultFormatCodePane || this instanceof AdditionalDeafaultFormatCodePane) {
            HTMLText functionMarkHtmlText = new HTMLText();
            HtmlPar functionMarkPar = new HtmlPar();
            functionMarkPar.addText("在此填写", false);
            functionMarkPar.addColorText("功能", HtmlPar.RED, false);
            functionMarkPar.addText("代码", false);
            functionMarkHtmlText.addPar(functionMarkPar);

            functionMarkMenuItem = new JMenuItem(functionMarkHtmlText.getHtmlContent());
            functionMarkMenuItem.addActionListener(listener);
            functionMarkMenuItem.addMouseListener(markMenuItemMouseAdapter);
            addMarkmenu.add(functionMarkMenuItem);
        }

        theMenu.addSeparator();
        operatingTipMenuItem = new OperatingTipMenuItem(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "标记说明")
                .getAbsolutePath());
        operatingTipMenuItem.setText("标记操作提示");
        theMenu.add(operatingTipMenuItem);
    }

    private MouseAdapter markMenuItemMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            if (e.getSource() == importMarkMenuItem) {//引入标记
                if (ModuleEditPaneHolder.importCodeFolder != null) {
                    ModuleEditPaneHolder.importCodeFolder.setUIResponse(true);
                }
            } else if (e.getSource() == initMarkMenuItem) {//初始化标记
                if (ModuleEditPaneHolder.initCodeFolder != null) {
                    ModuleEditPaneHolder.initCodeFolder.setUIResponse(true);
                }
            } else if (e.getSource() == setMarkMenuItem) {//设置标记
                if (ModuleEditPaneHolder.moduleSetCodeEditPane != null) {
                    ModuleEditPaneHolder.moduleSetCodeEditPane.setUIResponse(true);
                }
            } else if (e.getSource() == functionMarkMenuItem) {//功能标记
                if (ModuleEditPaneHolder.moduleFunctionEditPane != null) {
                    ModuleEditPaneHolder.moduleFunctionEditPane.setUIResponse(true);
                }
                if (AbstractFormatCodeInputPane.this instanceof AdditionalDeafaultFormatCodePane) {
                    int additionalSerialNumber = ((AdditionalDeafaultFormatCodePane) AbstractFormatCodeInputPane.this).getAdditionalSerialNumber();
                    if (FormatEditPaneHolder.additionalEditPane != null && additionalSerialNumber != 0) {
                        AdditionalFunctionPane additionalFunctionPane = FormatEditPaneHolder.additionalEditPane.getAdditionalFunctionPane(additionalSerialNumber);
                        if (additionalFunctionPane != null) {
                            additionalFunctionPane.setUIResponse(true);
                        }
                    }
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            if (e.getSource() == importMarkMenuItem) {//引入标记
                if (ModuleEditPaneHolder.importCodeFolder != null) {
                    ModuleEditPaneHolder.importCodeFolder.setUIResponse(false);
                }
            } else if (e.getSource() == initMarkMenuItem) {//初始化标记
                if (ModuleEditPaneHolder.initCodeFolder != null) {
                    ModuleEditPaneHolder.initCodeFolder.setUIResponse(false);
                }
            } else if (e.getSource() == setMarkMenuItem) {//设置标记
                if (ModuleEditPaneHolder.moduleSetCodeEditPane != null) {
                    ModuleEditPaneHolder.moduleSetCodeEditPane.setUIResponse(false);
                }
            } else if (e.getSource() == functionMarkMenuItem) {//功能标记
                if (ModuleEditPaneHolder.moduleFunctionEditPane != null) {
                    ModuleEditPaneHolder.moduleFunctionEditPane.setUIResponse(false);
                }
                if (AbstractFormatCodeInputPane.this instanceof AdditionalDeafaultFormatCodePane) {
                    int additionalSerialNumber = ((AdditionalDeafaultFormatCodePane) AbstractFormatCodeInputPane.this).getAdditionalSerialNumber();
                    if (FormatEditPaneHolder.additionalEditPane != null && additionalSerialNumber != 0) {
                        AdditionalFunctionPane additionalFunctionPane = FormatEditPaneHolder.additionalEditPane.getAdditionalFunctionPane(additionalSerialNumber);
                        if (additionalFunctionPane != null) {
                            additionalFunctionPane.setUIResponse(false);
                        }
                    }
                }
            }
        }
    };

    private ActionListener setCurrentCodeFilePathActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == setCurrentCodeFilePathMenuItem) {
                new CodeFilePathDialog(getPath(), "设置代码文件\"" + getName() + "\"放置路径");
            }
        }
    };


    /**
     * 定位到对应标签
     *
     * @param thanMarkElement
     * @param navigateOrNot
     */
    public boolean navigateToTheCorrespondingMark(BaseMarkElement thanMarkElement, boolean navigateOrNot) {
        boolean flag = false;
        ArrayList<HarryingMark> allMarkScutcheon = getAllMarkScutcheon();// 获取所有标记
        ArrayList<HarryingMark> list = MarkStaticMethod
                .getHarryingMarkWithCorrespondingQualifyingMarkList(thanMarkElement, allMarkScutcheon);
        if (list != null) {
            if (list.size() > 0) {
                flag = true;
                for (HarryingMark harryingMark : list) {//让符合特征的所有标签都响应改变颜色
                    ((BaseMarkScutcheon) harryingMark).setNavigate(navigateOrNot);
                }
                if (DataSourceEditHolder.autoPositionCheckBox != null && DataSourceEditHolder.autoPositionCheckBox.isSelected() == true) {//如果有设置自动定位到对应组件，定位过去
                    scrollToComponent(list.get(0));
                }
            }
        }
        return flag;
    }

    /**
     * 获取所有的标记
     *
     * @return
     */
    private ArrayList<HarryingMark> getAllMarkScutcheon() {
        ArrayList<HarryingMark> list = new ArrayList<>();
        int end = 0;
        Component a;
        BaseMarkScutcheon temp;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof Component) {
                    if (a instanceof MarkComponentInterface) {
                        temp = (BaseMarkScutcheon) a;
                        list.add(temp);
                    }
                }
            }
            end = e0.getEndOffset();
        }
        return list;
    }

    @Override
    public void reductionContent(ArrayList<BaseElementInterface> list) {
        if (list != null) {
            StringElement stringElement;
            for (BaseElementInterface element : list) {
                setCaretPosition(doc.getLength());
                if (element instanceof StringElement) {
                    stringElement = (StringElement) element;
                    insertString(stringElement.getText());
                } else if (element instanceof BaseLableElement) {
                    reductionControlLabelComponent(element);
                } else if (element instanceof BaseMarkElement) {
                    reductionMarkComponent(element);
                }
            }
        }
    }

    /**
     * 还原标签组件
     *
     * @param markElement
     */
    protected void reductionMarkComponent(BaseElementInterface markElement) {
        if (markElement instanceof FunctionMarkElement) {
            FunctionMarkScutcheon scutcheon = new FunctionMarkScutcheon((FunctionMarkElement) markElement);
            AbstractFormatCodeInputPane.this.insertComponent(scutcheon);

        } else if (markElement instanceof SetMarkElement) {
            SetMarkScutcheon scutcheon = new SetMarkScutcheon((SetMarkElement) markElement);
            AbstractFormatCodeInputPane.this.insertComponent(scutcheon);

        } else if (markElement instanceof InitMarkElement) {
            InitMarkScutcheon scutcheon = new InitMarkScutcheon((InitMarkElement) markElement);
            AbstractFormatCodeInputPane.this.insertComponent(scutcheon);

        } else if (markElement instanceof ImportMarkElement) {
            ImportMarkScutcheon scutcheon = new ImportMarkScutcheon((ImportMarkElement) markElement);
            AbstractFormatCodeInputPane.this.insertComponent(scutcheon);

        } else if (markElement instanceof MainSetMarkElement) {
            MainSetMarkScutcheon scutcheon = new MainSetMarkScutcheon((MainSetMarkElement) markElement);
            AbstractFormatCodeInputPane.this.insertComponent(scutcheon);

        } else if (markElement instanceof AdditionalSetMarkElement) {
            AdditionalSetMarkScutcheon scutcheon = new AdditionalSetMarkScutcheon((AdditionalSetMarkElement) markElement);
            AbstractFormatCodeInputPane.this.insertComponent(scutcheon);
        }
    }


    /**
     * 新建空白源文件
     */
    public void createNewBlankSourceFile(String fileName) {
        newDocument("");
        setName(fileName);
    }

    /**
     * 打开某个源文件
     */
    public void openSourceFile(File file) {
        String temp;
        try {
            temp = EncodingAsUTF8.encodingAsUTF8(SysService.SYS_PARAM_SERVICE.getEncoding().getEncodingDetectParam(), file.getAbsolutePath());
            newDocument(temp);
            setName(file.getName());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 新建文档
     *
     * @param content
     */
    private void newDocument(String content) {
        try {
            getDoc().insertString(getDoc().getLength(), content, attr);
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取面板编号
     *
     * @return
     */
    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置代码模型
     *
     * @param formatCodeModel
     */
    public void setCodeModel(GeneralFileFormat formatCodeModel, int codeOrdinal) {
        try {
            formatCodeModel.setFormatContent(getCodeStatementFormat());
            formatCodeModel.setCodeOrdinal(codeOrdinal);
            fileParam.setCodeOrdinal(codeOrdinal);
            formatCodeModel.setFileName(getName());
            formatCodeModel.setId(codeFileId);
            if (path == null) {
                formatCodeModel.setPath("");
            } else {
                formatCodeModel.setPath(path);
            }
            formatCodeModel.setCodeFormatOrNot(codeFormatOrNotMenuItem.isSelected() ? TRUE_ : FALSE_);
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 把该代码文件编辑面板设置为模块需要使用面板（对系统而言直接把添加标签的菜单失能即可，避免用户把模块编辑的那些控制层和其混淆）
     */
    public void setAsNeedUseInputCodeFilePane() {
        addLabelMenu.setEnabled(false);
    }

    /**
     * 设为一般的录入代码文件的面板（对系统而言仅是把添加菜单的标签使能）
     */
    public void setAsGeneralInputCodeFilePane() {
        addLabelMenu.setEnabled(true);
    }

    public void setAttribute(GeneralFileFormat fileFormatInfo) {
        setCodeFormatOrNot(fileFormatInfo.getCodeFormatOrNot());
        setPath(fileFormatInfo.getPath());
    }

    protected void setCodeFormatOrNot(int codeFormatOrNot) {
        if (codeFormatOrNot == TRUE_) {
            codeFormatOrNotMenuItem.setSelected(true);
        } else if (codeFormatOrNot == FALSE_) {
            codeFormatOrNotMenuItem.setSelected(false);
        }
    }

    public void setPath(String path) {
        this.path = path;
        showThisCodePaneAttribute();
    }

    public void showThisCodePaneAttribute() {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addText("生成路径：", false);
        par1.addColorText(path, HtmlPar.YELLOW, true);
        htmlText.addPar(par1);
        setToolTipText(htmlText.getHtmlContent());
    }


    class CodeFilePathDialog extends AbstractGetPathDialog {

        /**
         *
         */
        private static final long serialVersionUID = 7891984621683504997L;

        public CodeFilePathDialog(String codeFilePath, String tittle) {
            // TODO Auto-generated constructor stub
            super(codeFilePath, "生成源码目录");
            setTitle(tittle);
            setVisible(true);
        }

        @Override
        protected void ok() {
            // TODO Auto-generated method stub
            String path = getPathStr();
            setPath(path);
            this.dispose();
        }
    }

}
