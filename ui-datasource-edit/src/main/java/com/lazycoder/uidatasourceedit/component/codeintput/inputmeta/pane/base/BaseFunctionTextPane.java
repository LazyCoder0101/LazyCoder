package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import com.lazycoder.service.vo.element.lable.code.CodeInputCodeElement;
import com.lazycoder.service.vo.element.lable.code.ConstantCodeElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.service.vo.element.lable.code.CorrespondingAdditionalDefaultFileCodeElement;
import com.lazycoder.service.vo.element.lable.code.CustomMethodNameCodeElement;
import com.lazycoder.service.vo.element.lable.code.CustomVariableCodeElement;
import com.lazycoder.service.vo.element.lable.code.FileSelectorCodeElement;
import com.lazycoder.service.vo.element.lable.code.FunctionAddCodeElement;
import com.lazycoder.service.vo.element.lable.code.MethodChooseCodeElement;
import com.lazycoder.service.vo.element.lable.code.TextInputCodeElement;
import com.lazycoder.service.vo.element.lable.code.ThisFileNameCodeElement;
import com.lazycoder.service.vo.element.lable.code.VariableCodeElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.component.ContentChooseCodeMenuItem;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.CodeInputCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.ConstantCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.CorrespondingAdditionalDefaultFileCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.CustomMethodNameCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.CustomVariableCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.FileSelectorCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.FunctionAddCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.MethodChooseCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.MultiComboboxCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.RadioComboboxCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.TextInputCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.ThisFileNameCodeLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.VariableCodeLabel;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.utils.JsonUtil;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

;

public abstract class BaseFunctionTextPane extends BaseTextPane implements BaseCodePane {

    /**
     *
     */
    private static final long serialVersionUID = 242471767577599200L;

    protected int serialNumber;

    protected JMenu addLabelMenu = new JMenu("添加组件");

    /************** "添加"菜单项的内容 ********************************/
    private JMenu addTextInputMenu = new JMenu("内容输入"), addContentChooseMenu = new JMenu("选择"),
            addFunctionAddMenu = new JMenu("功能拓展"), addCustomVariableMenu = new JMenu("自定义变量"),
            addVariableMenu = new JMenu("变量选择"), addConstantMenu = new JMenu("常量数组"),
            addFileSelectorMenu = new JMenu("文件选择"), addCodeInputMenu = new JMenu("代码输入"),
            addCustomMethodNameMenu = new JMenu("自定义方法名"), addMethodChooseMenu = new JMenu("方法选择"),
            addDefaultFileMenu = new JMenu("所添加该可选模板的默认文件名");

    /****************************************************************/

    public BaseFunctionTextPane(int serialNumber) {
        // TODO Auto-generated constructor stub
        super();
        this.serialNumber = serialNumber;
    }

    /**
     * 本文件名的监听
     */
    private ActionListener thisFileNameListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            insertComponent(new ThisFileNameCodeLabel());
        }
    };


    /**
     * 从模型中重新获取添加对应菜单
     */
    private void updateAddMenuItem() {
        if (model != null) {
            BaseLableElement labelTemp;
            String typeTemp, thisName;
            clearAllMenuItem();
            if (model != null) {
                for (BaseElementInterface elementInterface : model.getControlComponentCorrespondingInformationList()) {
                    labelTemp = (BaseLableElement) elementInterface;
                    typeTemp = labelTemp.getLabelType();
                    thisName = labelTemp.getThisName();

                    JMenuItem menuItem = new JMenuItem(thisName);
                    menuItem.setName(thisName);

                    if (typeTemp.equals(LabelElementName.TEXT_INPUT)) {// 文本输入
                        addMenuItemAndListenerInMenu(addTextInputMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.CONTENT_CHOOSE)) {// 内容选择
                        ContentChooseElement element = (ContentChooseElement) labelTemp;
                        addContentChooseCodeLabel(element);

                    } else if (typeTemp.equals(LabelElementName.FUNCTION_ADD)) {// 功能拓展
                        addMenuItemAndListenerInMenu(addFunctionAddMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.CUSTOM_VARIABLE)) {// 自定义变量
                        addMenuItemAndListenerInMenu(addCustomVariableMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.VARIABLE)) {// 变量
                        addMenuItemAndListenerInMenu(addVariableMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.CONSTANT)) {// 常量
                        addMenuItemAndListenerInMenu(addConstantMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.FILE_SELECTOR)) {// 文件选择
                        addMenuItemAndListenerInMenu(addFileSelectorMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.CODE_INPUT)) {// 代码输入
                        addMenuItemAndListenerInMenu(addCodeInputMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.CUSTOM_METHOD_NAME)) {// 自定义方法名
                        addMenuItemAndListenerInMenu(addCustomMethodNameMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.METHOD_CHOOSE)) {// 方法选择
                        addMenuItemAndListenerInMenu(addMethodChooseMenu, menuItem);

                    } else if (typeTemp.equals(LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE)) {//对应的可选模板所添加的格式文件
                        addMenuItemAndListenerInMenu(addDefaultFileMenu, menuItem);
                    }
                }
            }
        }
    }

    /**
     * 添加一个内容选择代码标签
     *
     * @param element
     */
    private void addContentChooseCodeLabel(ContentChooseElement element) {
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(element.getOptionId());
        if (optionDataModel == null) {
            element.setOptionName(optionDataModel.getOptionName());

            String text = "有个代码内容面板要添加一个\"" + element.getOptionName() + "\"内容选择组件，可这个选项已经被删了！	(✪ω✪)";
            String logtext = getClass() + "（生成数据异常）————\"有个代码内容面板要添加" + element.getOptionName() + "\"这个选项的内容选择组件，该选项早已被删除";
            DataSourceEditHolder.errorLogging(text, logtext);

        } else {
            ArrayList<String> rowNoteList = JSON.parseObject(optionDataModel.getRowNoteParam(),
                    new TypeReference<ArrayList<String>>() {
                    });

            String titName = ContentChooseElement.getShowNameForOprating(element, optionDataModel);
            ContentChooseCodeMenuItem textPaneMenuItem;

            JMenu contentChooseMenu = new JMenu(titName);
            addContentChooseMenu.add(contentChooseMenu);

            ArrayList<String> noteList = JSON.parseObject(optionDataModel.getRowNoteParam(),
                    new TypeReference<ArrayList<String>>() {
                    });
            Integer thisNum = SysService.OPTION_SERVICE.getValueListGroupNumById(element.getOptionId());//查一下看看这个选项有多少组选项值
            if (thisNum != null && thisNum > 0) {
                for (int k = 1; k <= thisNum; k++) {
                    int groupNum = k - 1;
                    String textName = "", tipText;
                    if (noteList.size() > groupNum && !("".equals(noteList.get(groupNum)))) {
                        tipText = noteList.get(groupNum);
                    } else {
                        tipText = ContentChooseElement.getShowNameForCode(element, k, optionDataModel);
                    }

                    if (rowNoteList.size() >= thisNum) {
                        if ("".equals(rowNoteList.get(groupNum))) {
                            textName = tipText;

                        } else {

                            textName = HTMLText.createHtmlContent(rowNoteList.get(groupNum), HtmlPar.BLUE, false);
                        }
                    } else {
                        textName = tipText;//生成这个选项组对应的菜单项
                    }

                    textPaneMenuItem = new ContentChooseCodeMenuItem(textName, element, groupNum);
                    textPaneMenuItem.setToolTipText(tipText);
                    textPaneMenuItem.addActionListener(conentChooseCodeMenuItemListener);
                    contentChooseMenu.add(textPaneMenuItem);
                }
            }
        }
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
                }
            }
        }
    }

    /**
     * 根据标签元素还原为标签组件
     *
     * @param lableElement
     */
    protected void reductionControlLabelComponent(BaseElementInterface lableElement) {
        if (lableElement instanceof TextInputCodeElement) {
            TextInputCodeLabel label = new TextInputCodeLabel((TextInputCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof ContentChooseCodeElement) {
            ContentChooseCodeElement codeElement = (ContentChooseCodeElement) lableElement;
            OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(codeElement.getOptionId());
            if (optionDataModel == null) {
                String text = "有个代码内容面板要添加一个\"" + optionDataModel.getOptionName() + "\"内容选择组件，可这个选项已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（还原数据异常）————\"有个代码内容面板要添加" + optionDataModel.getOptionName() + "\"这个选项的内容选择组件，该选项早已被删除";
                DataSourceEditHolder.errorLogging(text, logtext);

            } else {
                codeElement.setOptionName(optionDataModel.getOptionName());

                if (OptionDataModel.EXECLUSIVE == optionDataModel.getOptionType()) {
                    RadioComboboxCodeLabel radioComboboxCodeLabel = new RadioComboboxCodeLabel(codeElement);
                    radioComboboxCodeLabel.setPassingComponentParams(passingComponentParams);
                    insertComponent(radioComboboxCodeLabel);
                    if (radioComboboxCodeLabel.getSelectedIndex() < 0 && radioComboboxCodeLabel.getModel().getSize() > 0) {
                        radioComboboxCodeLabel.setSelectedIndex(0);
                    }

                } else if (OptionDataModel.MULTIPLE == optionDataModel.getOptionType()) {
                    MultiComboboxCodeLabel multiComboboxCodeLabel = new MultiComboboxCodeLabel(codeElement);
                    multiComboboxCodeLabel.setPassingComponentParams(passingComponentParams);
                    insertComponent(multiComboboxCodeLabel);
                    if (multiComboboxCodeLabel.getSelectedIndexs().size() == 0 && multiComboboxCodeLabel.getModel().getSize() > 0) {
                        multiComboboxCodeLabel.addSelectedIndex(0);
                    }
                }
            }

        } else if (lableElement instanceof FunctionAddCodeElement) {
            FunctionAddCodeLabel label = new FunctionAddCodeLabel((FunctionAddCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof CustomVariableCodeElement) {
            CustomVariableCodeLabel label = new CustomVariableCodeLabel((CustomVariableCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof VariableCodeElement) {
            VariableCodeLabel label = new VariableCodeLabel((VariableCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof ConstantCodeElement) {
            ConstantCodeLabel label = new ConstantCodeLabel((ConstantCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof FileSelectorCodeElement) {
            FileSelectorCodeLabel label = new FileSelectorCodeLabel((FileSelectorCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof CodeInputCodeElement) {
            CodeInputCodeLabel label = new CodeInputCodeLabel((CodeInputCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof CustomMethodNameCodeElement) {
            CustomMethodNameCodeLabel label = new CustomMethodNameCodeLabel((CustomMethodNameCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof MethodChooseCodeElement) {
            MethodChooseCodeLabel label = new MethodChooseCodeLabel((MethodChooseCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof ThisFileNameCodeElement) {
            ThisFileNameCodeLabel label = new ThisFileNameCodeLabel((ThisFileNameCodeElement) lableElement);
            insertComponent(label);

        } else if (lableElement instanceof CorrespondingAdditionalDefaultFileCodeElement) {
            CorrespondingAdditionalDefaultFileCodeLabel label = new CorrespondingAdditionalDefaultFileCodeLabel((CorrespondingAdditionalDefaultFileCodeElement) lableElement);
            insertComponent(label);
        }
    }

    /**
     * 获取代码参数
     *
     * @return
     * @throws BadLocationException
     */
    public String getCodeParam() throws BadLocationException {
        ArrayList<BaseElementInterface> list = getStatementFormatParams();
        String out = JsonUtil.getJsonStr(list);
        return out;
    }

    /**
     * 删除标签
     */
    @Override
    public void delLabel(String labelType, String componentName) {
        // TODO Auto-generated method stub
        deleteLabel(componentName, labelType, CODE_PANE);
    }

    /**
     * 更新菜单
     */
    @Override
    public void updateMenu() {
        // TODO Auto-generated method stub
        updateAddMenuItem();
    }

    /**
     * 清空
     */
    @Override
    public void clear() {
        deleteAllComponents();
        setText("");
        updateAddMenuItem();
    }

    /**
     * 获取面板编号
     *
     * @return
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    public void menuInit(CodeCondition codeCondition) {
        theMenu.add(addLabelMenu);

        addLabelMenu.add(addTextInputMenu);
        addLabelMenu.add(addContentChooseMenu);
        addLabelMenu.add(addFunctionAddMenu);
        addLabelMenu.add(addCustomVariableMenu);
        addLabelMenu.add(addVariableMenu);
        addLabelMenu.add(addConstantMenu);
        addLabelMenu.add(addFileSelectorMenu);
        addLabelMenu.add(addCodeInputMenu);
        addLabelMenu.add(addCustomMethodNameMenu);
        addLabelMenu.add(addMethodChooseMenu);

        if (codeCondition.isUseOfThisFileName()) {
            JMenuItem thisFileNameMenuItem = new JMenuItem("本文件名");
            addLabelMenu.add(thisFileNameMenuItem);
            thisFileNameMenuItem.addActionListener(thisFileNameListener);
        }
        if (codeCondition.isUseOfAdditionalFileChooseComponent()) {
//			defaultFileMenu = new JMenu("所添加该可选模板的默认文件名");
            addLabelMenu.add(addDefaultFileMenu);
        }

        theMenu.addSeparator();

        theMenu.add(copyMenu);
        theMenu.add(cutMenu);
        theMenu.add(pasteMenu);

    }

    /**
     * 清除所有菜单选项
     */
    private void clearAllMenuItem() {
        addTextInputMenu.removeAll();
        addContentChooseMenu.removeAll();
        addFunctionAddMenu.removeAll();
        addCustomVariableMenu.removeAll();
        addVariableMenu.removeAll();
        addConstantMenu.removeAll();
        addFileSelectorMenu.removeAll();
        addCodeInputMenu.removeAll();
        addCustomMethodNameMenu.removeAll();
        addMethodChooseMenu.removeAll();
        addDefaultFileMenu.removeAll();
    }

    private void addMenuItemAndListenerInMenu(JMenu addMenu, JMenuItem menuItem) {
        addMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String name = ((JComponent) e.getSource()).getName();

                if (addMenu == addTextInputMenu) {
                    addCodeLabel(name, LabelElementName.TEXT_INPUT);

                } else if (addMenu == addContentChooseMenu) {

                } else if (addMenu == addFunctionAddMenu) {
                    addCodeLabel(name, LabelElementName.FUNCTION_ADD);

                } else if (addMenu == addCustomVariableMenu) {
                    addCodeLabel(name, LabelElementName.CUSTOM_VARIABLE);

                } else if (addMenu == addVariableMenu) {
                    addCodeLabel(name, LabelElementName.VARIABLE);

                } else if (addMenu == addConstantMenu) {
                    addCodeLabel(name, LabelElementName.CONSTANT);

                } else if (addMenu == addFileSelectorMenu) {
                    addCodeLabel(name, LabelElementName.FILE_SELECTOR);

                } else if (addMenu == addCodeInputMenu) {
                    addCodeLabel(name, LabelElementName.CODE_INPUT);

                } else if (addMenu == addCustomMethodNameMenu) {
                    addCodeLabel(name, LabelElementName.CUSTOM_METHOD_NAME);

                } else if (addMenu == addMethodChooseMenu) {
                    addCodeLabel(name, LabelElementName.METHOD_CHOOSE);

                } else if (addMenu == addContentChooseMenu) {
                    addCodeLabel(name, LabelElementName.CONTENT_CHOOSE);

                } else if (addMenu == addDefaultFileMenu) {
                    addCodeLabel(name, LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE);
                }
            }
        });
    }

    public void addCodeLabel(String name, String labelType) {
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            TextInputCodeLabel label = new TextInputCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            FunctionAddCodeLabel label = new FunctionAddCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            CustomVariableCodeLabel label = new CustomVariableCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            VariableCodeLabel label = new VariableCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            ConstantCodeLabel label = new ConstantCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            FileSelectorCodeLabel label = new FileSelectorCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            CodeInputCodeLabel label = new CodeInputCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            CustomMethodNameCodeLabel label = new CustomMethodNameCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            MethodChooseCodeLabel label = new MethodChooseCodeLabel(name);
            insertComponent(label);

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            CorrespondingAdditionalDefaultFileCodeLabel label = new CorrespondingAdditionalDefaultFileCodeLabel(name);
            insertComponent(label);
        }
    }

    /**
     * 点击选项菜单的监听
     */
    private ActionListener conentChooseCodeMenuItemListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            ContentChooseCodeMenuItem textPaneMenuItem = (ContentChooseCodeMenuItem) e.getSource();
            OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(textPaneMenuItem.getContentChooseElement().getOptionId());
            if (optionDataModel != null) {

                ContentChooseCodeElement codeElement =
                        new ContentChooseCodeElement(optionDataModel.getOptionId(), optionDataModel.getOptionName());
                codeElement.setSelectGroup(textPaneMenuItem.getGroupNum());
                codeElement.setUseNumbered(textPaneMenuItem.getContentChooseElement().getUseNumbered());
                String thisName = ContentChooseElement.getThisNameForCode(
                        textPaneMenuItem.getContentChooseElement(), textPaneMenuItem.getGroupNum());
                codeElement.setThisName(thisName);

                if (OptionDataModel.EXECLUSIVE == optionDataModel.getOptionType()) {

                    RadioComboboxCodeLabel radioComboboxCodeLabel = new RadioComboboxCodeLabel(codeElement);
                    radioComboboxCodeLabel.setPassingComponentParams(passingComponentParams);
                    radioComboboxCodeLabel.setCurrentCorrespondingControlLableSelectedIndex();
                    BaseFunctionTextPane.this.insertComponent(radioComboboxCodeLabel);

                } else if (OptionDataModel.MULTIPLE == optionDataModel.getOptionType()) {

                    MultiComboboxCodeLabel multiComboboxCodeLabel = new MultiComboboxCodeLabel(codeElement);
                    multiComboboxCodeLabel.setPassingComponentParams(passingComponentParams);
                    multiComboboxCodeLabel.setCurrentCorrespondingControlLableSelectedIndex();
                    BaseFunctionTextPane.this.insertComponent(multiComboboxCodeLabel);
                }
            }
        }
    };

    /**
     * 删除内容选择组件
     *
     * @param optionId    删除的选项组件的id
     * @param useNumbered 要删面板的第几个该选项的组件
     */
    @Override
    public void delContentChooseLable(String optionId, int useNumbered) {
        int end = 0;
        Component a;
        RadioComboboxCodeLabel radioComboboxLabel;
        MultiComboboxCodeLabel multiComboboxLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxCodeLabel) {
                    radioComboboxLabel = (RadioComboboxCodeLabel) a;
                    if (radioComboboxLabel.getCodeElement().getUseNumbered() == useNumbered) {
                        if (radioComboboxLabel.getCodeElement().getOptionId().equals(optionId)) {
                            radioComboboxLabel.deleteFromPanel();
                            try {
                                doc.remove(end, e0.getEndOffset() - end);
                            } catch (BadLocationException e1) {
                                // TODO 自动生成的 catch 块
                                e1.printStackTrace();
                            }
                        }
                    }
                } else if (a instanceof MultiComboboxCodeLabel) {
                    multiComboboxLabel = (MultiComboboxCodeLabel) a;
                    if (multiComboboxLabel.getCodeElement().getUseNumbered() == useNumbered) {
                        if (multiComboboxLabel.getCodeElement().getOptionId().equals(optionId)) {
                            multiComboboxLabel.deleteFromPanel();
                            try {
                                doc.remove(end, e0.getEndOffset() - end);
                            } catch (BadLocationException e1) {
                                // TODO 自动生成的 catch 块
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 删除内容选择组件
     *
     * @param optionId
     */
    @Override
    public void delContentChooseLable(String optionId) {
        int end = 0;
        Component a;
        RadioComboboxCodeLabel radioComboboxLabel;
        MultiComboboxCodeLabel multiComboboxLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxCodeLabel) {
                    radioComboboxLabel = (RadioComboboxCodeLabel) a;
                    if (radioComboboxLabel.getCodeElement().getOptionId().equals(optionId)) {
                        radioComboboxLabel.deleteFromPanel();
                        try {
                            doc.remove(end, e0.getEndOffset() - end);
                        } catch (BadLocationException e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }
                    }
                } else if (a instanceof MultiComboboxCodeLabel) {
                    multiComboboxLabel = (MultiComboboxCodeLabel) a;
                    if (multiComboboxLabel.getCodeElement().getOptionId().equals(optionId)) {
                        multiComboboxLabel.deleteFromPanel();
                        try {
                            doc.remove(end, e0.getEndOffset() - end);
                        } catch (BadLocationException e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }

    @Override
    public void updateComboboxItems(OptionDataModel option) {
        int end = 0;
        Component a;
        RadioComboboxCodeLabel radioComboboxLabel;
        MultiComboboxCodeLabel multiComboboxLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxCodeLabel) {
                    radioComboboxLabel = (RadioComboboxCodeLabel) a;
                    if (radioComboboxLabel.getCodeElement().getOptionId().equals(option.getOptionId())) {
                        radioComboboxLabel.updateComboboxItems(option);
                    }
                } else if (a instanceof MultiComboboxCodeLabel) {
                    multiComboboxLabel = (MultiComboboxCodeLabel) a;
                    if (multiComboboxLabel.getCodeElement().getOptionId().equals(option.getOptionId())) {
                        multiComboboxLabel.updateComboboxItems(option);
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 更改单选组件的显示值
     *
     * @param contentChooseControl
     * @param selectIndex
     */
    @Override
    public void updateRadioComboboxShowValue(ContentChooseControl contentChooseControl, int selectIndex) {
        int end = 0;
        Component a;
        RadioComboboxCodeLabel radioComboboxCodeLabel;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            Element e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxCodeLabel) {
                    radioComboboxCodeLabel = (RadioComboboxCodeLabel) a;
                    if (radioComboboxCodeLabel.match(contentChooseControl) == true) {
                        radioComboboxCodeLabel.setValue(selectIndex);
                    }
                }
            }
            end = e0.getEndOffset();
        }
        updateUI();
        repaint();
    }

    /**
     * 更改多选组件的显示值
     *
     * @param chooseControl
     * @param selectList
     */
    @Override
    public void updateMutiComboboxShowValue(ContentChooseControl chooseControl, ArrayList<Integer> selectList) {
        int end = 0;
        Component a;
        MultiComboboxCodeLabel multiComboboxCodeLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof MultiComboboxCodeLabel) {
                    multiComboboxCodeLabel = (MultiComboboxCodeLabel) a;
                    if (multiComboboxCodeLabel.match(chooseControl) == true) {
                        multiComboboxCodeLabel.setValue(selectList);
                    }
                }
            }
            end = e0.getEndOffset();
        }
        updateUI();
        repaint();
    }


    /**
     * 生成代码语句格式
     *
     * @return
     * @throws BadLocationException
     */
    public String getCodeStatementFormat() throws BadLocationException {
        String out = getStatementFormat();
        return out;
    }

    @Override
    public void makeCorrespondingLabelScutcheonRespond(BaseLableElement lableElement, Border border) {
        super.makeCorrespondingLabelScutcheonRespond(lableElement, border);
    }

}
