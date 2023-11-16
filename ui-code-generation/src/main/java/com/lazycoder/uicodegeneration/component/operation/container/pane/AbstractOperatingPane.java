package com.lazycoder.uicodegeneration.component.operation.container.pane;


import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.element.lable.control.CorrespondingAdditionalDefaultFileControl;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.OperatingPaneBusinessTraverse;
import com.lazycoder.uicodegeneration.component.operation.component.CodeGenerationComponentInterface;
import com.lazycoder.uicodegeneration.component.operation.component.CodeInputBox;
import com.lazycoder.uicodegeneration.component.operation.component.ConstantMutipleInoutBox;
import com.lazycoder.uicodegeneration.component.operation.component.ContentChooseMultiSelectCombobox;
import com.lazycoder.uicodegeneration.component.operation.component.ContentChooseRadioCombobox;
import com.lazycoder.uicodegeneration.component.operation.component.CorrespondingAdditionalDefaultFileCombobox;
import com.lazycoder.uicodegeneration.component.operation.component.CustomMethodNameTextField;
import com.lazycoder.uicodegeneration.component.operation.component.CustomVariableMutipleInputBox;
import com.lazycoder.uicodegeneration.component.operation.component.CustomVariableRadioTextField;
import com.lazycoder.uicodegeneration.component.operation.component.FileSelectorTextFieldWithPicture;
import com.lazycoder.uicodegeneration.component.operation.component.FunctionAddInputPaneForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.component.InfrequentlyUsedSettingButton;
import com.lazycoder.uicodegeneration.component.operation.component.MethodChoiceMenu;
import com.lazycoder.uicodegeneration.component.operation.component.NoteButton;
import com.lazycoder.uicodegeneration.component.operation.component.PictureButton;
import com.lazycoder.uicodegeneration.component.operation.component.TextInputTextArea;
import com.lazycoder.uicodegeneration.component.operation.component.TextInputTextField;
import com.lazycoder.uicodegeneration.component.operation.component.VariableComboBox;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.AbstractLabelMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CodeInputMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.ConstantMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.ContentChooseMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CorrespondingAdditionalDefaultFileMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CustomMethodMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CustomVariableMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.FileSelectorMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.FunctionAddMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.InfrequentlyUsedSettingMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.MethodChooseMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.NoteMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.PictureMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.StringMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.TextInputMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.VariableMeta;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public abstract class AbstractOperatingPane extends JTextPane
        implements OperatingPaneBusinessTraverse {

    /**
     *
     */
    private static final long serialVersionUID = -6850293082222442208L;

    protected SimpleAttributeSet attrset = new SimpleAttributeSet();

    private StyledDocument doc;

    private OpratingContainerInterface opratingContainer;

    public AbstractOperatingPane(OpratingContainerInterface opratingContainer) {
        // TODO Auto-generated constructor stub
        doc = this.getStyledDocument();
        StyleConstants.setFontSize(attrset, 18);
        this.setEditable(false);

        this.opratingContainer = opratingContainer;
    }

    /**
     * 生成
     *
     * @param codeGenerationalOpratingContainerParam
     */
    public void generateOperationalContent(GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        codeGenerationalOpratingContainerParam.setOperatingComponentPlacePane(this);
        if ("".equals(codeGenerationalOpratingContainerParam.getControlStatementFormat()) == false) {
            StringElement stringElement;
            ArrayList<BaseElementInterface> list = DeserializeElementMethods.getControlPaneElmentList(codeGenerationalOpratingContainerParam.getControlStatementFormat());
            for (BaseElementInterface element : list) {
                setCaretPosition(doc.getLength());
                if (element instanceof StringElement) {
                    stringElement = (StringElement) element;
                    insertString(stringElement.getText());
                } else if (element instanceof BaseLableElement) {
                    generateOperationalComponent(codeGenerationalOpratingContainerParam, element);
                }
            }
            setCaretPosition(doc.getLength());
        }
    }

    /**
     * 获取最大的行宽
     *
     * @return
     */
    public int getMaxLineWidth(int fontSize) {
        int end = 0, currentLineWidth = 0;

        String stringTemp;
        Component cTemp;
        Element e0;

        ArrayList<Integer> lineListTemp = new ArrayList<Integer>();// 统计最大宽度的数组
        // 开始计算宽度数组
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("content".equals(e0.getName())) {
                try {
                    stringTemp = e0.getDocument().getText(end, e0.getEndOffset() - end);
                    if ("\n".equals(stringTemp)) {
                        lineListTemp.add(currentLineWidth);
                        currentLineWidth = 0;

                    } else if (stringTemp.contains("\n")) {
                        currentLineWidth = currentLineWidth + stringTemp.length() * fontSize;
                        lineListTemp.add(currentLineWidth);
                        currentLineWidth = 0;

                    } else {// 无换行
                        currentLineWidth = currentLineWidth + stringTemp.length() * fontSize;
                    }
                } catch (BadLocationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());
                if (cTemp instanceof CodeGenerationComponentInterface) {
                    currentLineWidth = currentLineWidth
                            + ((CodeGenerationComponentInterface) cTemp).getComponentWidth();
                }
            }
            end = e0.getEndOffset();
            if (end == doc.getLength()) {
                lineListTemp.add(currentLineWidth);
            }
        }

        int maxWidth = 0;// 从数组那里拿到最大值
        for (Integer lineTemp : lineListTemp) {
            if (maxWidth < lineTemp) {
                maxWidth = lineTemp;
            }
        }
        return maxWidth;
    }

    /**
     * 获取显示出来的行数
     *
     * @param containerMaxWidth
     * @param fontSize
     * @return
     */
    public int getShowLineNum(int containerMaxWidth, int fontSize) {
        int end = 0, currentLineWidth = 0, showLineNum = 0;

        String stringTemp;
        Component cTemp;
        Element e0;

        // 开始计算宽度数组
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("content".equals(e0.getName())) {
                try {
                    stringTemp = e0.getDocument().getText(end, e0.getEndOffset() - end);
                    if ("\n".equals(stringTemp)) {

                        if (currentLineWidth % containerMaxWidth == 0) {//刚好全部写满
                            showLineNum = showLineNum + currentLineWidth / containerMaxWidth;
                        } else {//
                            showLineNum = showLineNum + currentLineWidth / containerMaxWidth + 1;
                        }

                        currentLineWidth = 0;

                    } else if (stringTemp.contains("\n")) {
                        currentLineWidth = currentLineWidth + stringTemp.length() * fontSize;

                        if (currentLineWidth % containerMaxWidth == 0) {//刚好全部写满
                            showLineNum = showLineNum + currentLineWidth / containerMaxWidth;
                        } else {//
                            showLineNum = showLineNum + currentLineWidth / containerMaxWidth + 1;
                        }

                        currentLineWidth = 0;

                    } else {// 无换行
                        currentLineWidth = currentLineWidth + stringTemp.length() * fontSize;
                    }
                } catch (BadLocationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());
                if (cTemp instanceof CodeGenerationComponentInterface) {
                    currentLineWidth = currentLineWidth
                            + ((CodeGenerationComponentInterface) cTemp).getComponentWidth();
                }
            }
            end = e0.getEndOffset();
            if (end == doc.getLength()) {
                if (currentLineWidth % containerMaxWidth == 0) {//刚好全部写满
                    showLineNum = showLineNum + currentLineWidth / containerMaxWidth;
                } else {//
                    showLineNum = showLineNum + currentLineWidth / containerMaxWidth + 1;
                }
            }
        }
        return showLineNum;
    }

    /**
     * 获取行数
     * http://cn.voidcc.com/question/p-clgiwrqg-ban.html
     *
     * @return
     */
    public int getLineNum() {
//        int totalCharacters = doc.getLength();
//        int lineCount = (totalCharacters == 0) ? 1 : 0;
//        try {
//            int offset = totalCharacters;
//            while (offset > 0) {
//                offset = Utilities.getRowStart(this, offset) - 1;
//                lineCount++;
//            }
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }
//        return lineCount;
        Element element = doc.getDefaultRootElement();
        return element.getElementCount();
    }

    protected void generateOperationalComponent(GeneralContainerComponentParam codeGenerationalOpratingContainerParam, BaseElementInterface element) {
        if (element instanceof TextInputControl) {
            TextInputControl controlElement = (TextInputControl) element;

            if (TextInputControl.TEXT_FIELD_FORM == controlElement.getPresentForm()) {
                TextInputTextField field = new TextInputTextField(codeGenerationalOpratingContainerParam,
                        controlElement);
                setCaretPosition(doc.getLength());
                insertComponent(field);

            } else if (TextInputControl.TEXT_AREA_FORM == controlElement.getPresentForm()) {
                TextInputTextArea area = new TextInputTextArea(codeGenerationalOpratingContainerParam, controlElement);
                setCaretPosition(doc.getLength());
                insertComponent(area);
            }

        } else if (element instanceof ContentChooseControl) {
            ContentChooseControl chooseControl = (ContentChooseControl) element;
            OptionDataModel option = SysService.OPTION_SERVICE.getOptionById(chooseControl.getOptionId());
            if (option==null){
                String text = "有个功能需要添加一个\"" + chooseControl.getOptionName() + "\"内容选择组件，可这个选项已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（添加功能异常）要添加\"" + chooseControl.getOptionName() + "\"这个选项，该选项早已被删除";
                CodeGenerationFrameHolder.errorLogging(text, logtext);
            }else {
                if (OptionDataModel.EXECLUSIVE == option.getOptionType()) {
                    ContentChooseRadioCombobox combobox = new ContentChooseRadioCombobox(
                            codeGenerationalOpratingContainerParam, chooseControl);
                    setCaretPosition(doc.getLength());
                    insertComponent(combobox);

                } else if (OptionDataModel.MULTIPLE == option.getOptionType()) {
                    ContentChooseMultiSelectCombobox combobox = new ContentChooseMultiSelectCombobox(
                            codeGenerationalOpratingContainerParam, chooseControl);
                    setCaretPosition(doc.getLength());
                    insertComponent(combobox);
                }
            }

        } else if (element instanceof FunctionAddControl) {
            FunctionAddInputPaneForCodeGeneration functionAddInputPaneForCodeGeneration = new FunctionAddInputPaneForCodeGeneration(codeGenerationalOpratingContainerParam, (FunctionAddControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(functionAddInputPaneForCodeGeneration);

        } else if (element instanceof CustomVariableControl) {
            CustomVariableControl controlElement = (CustomVariableControl) element;
            if (controlElement.getOnlyAddOne() == CustomVariableControl.TRUE_) {
                CustomVariableRadioTextField inputBox = new CustomVariableRadioTextField(
                        codeGenerationalOpratingContainerParam, controlElement);
                setCaretPosition(doc.getLength());
                insertComponent(inputBox);

            } else if (controlElement.getOnlyAddOne() == CustomVariableControl.FALSE_) {
                CustomVariableMutipleInputBox inputBox = new CustomVariableMutipleInputBox(
                        codeGenerationalOpratingContainerParam, controlElement);
                setCaretPosition(doc.getLength());
                insertComponent(inputBox);
            }

        } else if (element instanceof VariableControl) {
            VariableComboBox variableComboBox = new VariableComboBox(codeGenerationalOpratingContainerParam, (VariableControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(variableComboBox);

        } else if (element instanceof ConstantControl) {
            ConstantMutipleInoutBox constantMutipleInoutBox = new ConstantMutipleInoutBox(codeGenerationalOpratingContainerParam, (ConstantControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(constantMutipleInoutBox);

        } else if (element instanceof FileSelectorControl) {
            FileSelectorTextFieldWithPicture fileSelectorTextFieldWithPicture = new FileSelectorTextFieldWithPicture(codeGenerationalOpratingContainerParam, (FileSelectorControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(fileSelectorTextFieldWithPicture);

        } else if (element instanceof NoteControl) {
            NoteButton noteButton = new NoteButton(codeGenerationalOpratingContainerParam, (NoteControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(noteButton);

        } else if (element instanceof PictureControl) {
            PictureControl pictureControl = (PictureControl) element;

            OpratingContainerInterface opratingContainer = codeGenerationalOpratingContainerParam
                    .getThisOpratingContainer();
            File pitureFolder = new File(opratingContainer.getImageRootPath().getAbsolutePath() + File.separator
                    + pictureControl.getPitureFolderName());
            if (pitureFolder.isDirectory() == true) {
                PictureButton pictureButton = new PictureButton(codeGenerationalOpratingContainerParam, pictureControl);
                setCaretPosition(doc.getLength());
                insertComponent(pictureButton);
            } else {
                String text = "有个功能要添加一个图片组件，可组件对应的文件夹已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（添加功能异常）有个图片组件，对应文件夹早已被删除，对应路径：" + pitureFolder.getAbsolutePath();
                CodeGenerationFrameHolder.errorLogging(text, logtext);
            }

        } else if (element instanceof CodeInputControl) {
            CodeInputBox codeInputBox = new CodeInputBox(codeGenerationalOpratingContainerParam, (CodeInputControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(codeInputBox);

        } else if (element instanceof InfrequentlyUsedSettingControl) {
            InfrequentlyUsedSettingButton infrequentlyUsedSettingButton = new InfrequentlyUsedSettingButton(
                    codeGenerationalOpratingContainerParam.getThisOpratingContainer(),
                    codeGenerationalOpratingContainerParam, (InfrequentlyUsedSettingControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(infrequentlyUsedSettingButton);
//            if (PathFind.COMMAND_TYPE == codeGenerationalOpratingContainerParam.getParentPathFind().getMetaType()) {
//
//                InfrequentlyUsedSettingButton infrequentlyUsedSettingButton = new InfrequentlyUsedSettingButton(
//                        codeGenerationalOpratingContainerParam.getFirstCommandOpratingContainer(),
//                        codeGenerationalOpratingContainerParam, (InfrequentlyUsedSettingControl) element);
//                setCaretPosition(doc.getLength());
//                insertComponent(infrequentlyUsedSettingButton);
//
//            } else if (PathFind.FORMAT_TYPE == codeGenerationalOpratingContainerParam.getParentPathFind().getMetaType()) {
//
//                InfrequentlyUsedSettingButton infrequentlyUsedSettingButton = new InfrequentlyUsedSettingButton(
//                        codeGenerationalOpratingContainerParam.getFormatContainer(),
//                        codeGenerationalOpratingContainerParam, (InfrequentlyUsedSettingControl) element);
//                setCaretPosition(doc.getLength());
//                insertComponent(infrequentlyUsedSettingButton);
//
//            }

        } else if (element instanceof CustomMethodNameControl) {
            CustomMethodNameTextField customMethodNameTextField = new CustomMethodNameTextField(
                    codeGenerationalOpratingContainerParam, (CustomMethodNameControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(customMethodNameTextField);

        } else if (element instanceof MethodChooseControl) {

            MethodChoiceMenu methodChoiceMenu = new MethodChoiceMenu(codeGenerationalOpratingContainerParam,
                    (MethodChooseControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(methodChoiceMenu);

        } else if (element instanceof CorrespondingAdditionalDefaultFileControl) {
            CorrespondingAdditionalDefaultFileCombobox correspondingAdditionalDefaultFileCombobox = new CorrespondingAdditionalDefaultFileCombobox(
                    codeGenerationalOpratingContainerParam,
                    (CorrespondingAdditionalDefaultFileControl) element);
            setCaretPosition(doc.getLength());
            insertComponent(correspondingAdditionalDefaultFileCombobox);
        }
    }

    /**
     * 添加文字
     *
     * @param text
     */
    private void insertString(String text) {
        try {
            doc.insertString(doc.getLength(), text, attrset);
        } catch (BadLocationException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public OpratingContainerInterface getOpratingContainer() {
        return opratingContainer;
    }

    public ArrayList<AbstractOpratingPaneElement> getComponentList() {
        int end = 0;
        ArrayList<AbstractOpratingPaneElement> list = new ArrayList<>();
        String stringTemp;
        Component cTemp;
        StringMeta stringMeta;
        while (end < doc.getLength()) { // 把内容依次获取
            Element e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("content".equals(e0.getName())) {
                try {
                    stringTemp = e0.getDocument().getText(end, e0.getEndOffset() - end);
                    stringMeta = new StringMeta(stringTemp);
                    list.add(stringMeta);

                } catch (BadLocationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof CodeGenerationFormatUIComonentInterface) {
                    list.add(((CodeGenerationFormatUIComonentInterface) cTemp).getFormatStructureModel());
                }
            }
            end = e0.getEndOffset();
        }
        return list;
    }

    /**
     * 还原之前的内容
     *
     * @param paneElementList
     * @param codeGenerationalOpratingContainerParam
     */
    public void restoreContent(ArrayList<AbstractOpratingPaneElement> paneElementList,
                               GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {

        codeGenerationalOpratingContainerParam.setOperatingComponentPlacePane(this);
        if (paneElementList != null) {
            StringMeta stringMeta;
            for (FormatStructureModelInterface element : paneElementList) {
                if (element instanceof StringMeta) {
                    stringMeta = (StringMeta) element;
                    insertString(stringMeta.getText());

                } else if (element instanceof AbstractLabelMeta) {
                    reductionControlLabelComponent(codeGenerationalOpratingContainerParam, (AbstractLabelMeta) element);
                }
            }
        }
    }

    /**
     * 还原标签组件
     *
     * @param codeGenerationalOpratingContainerParam
     * @param metaElement
     */
    private void reductionControlLabelComponent(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                                AbstractLabelMeta metaElement) {
        if (metaElement instanceof TextInputMeta) {
            TextInputControl controlElement = (TextInputControl) metaElement.getControlElement();
            if (TextInputControl.TEXT_FIELD_FORM == controlElement.getPresentForm()) {
                TextInputTextField field = new TextInputTextField(codeGenerationalOpratingContainerParam,
                        (TextInputMeta) metaElement);
                setCaretPosition(doc.getLength());
                insertComponent(field);

            } else if (TextInputControl.TEXT_AREA_FORM == controlElement.getPresentForm()) {
                TextInputTextArea area = new TextInputTextArea(codeGenerationalOpratingContainerParam,
                        (TextInputMeta) metaElement);
                setCaretPosition(doc.getLength());
                insertComponent(area);
            }
        } else if (metaElement instanceof ContentChooseMeta) {
            ContentChooseControl chooseControl = (ContentChooseControl) metaElement.getControlElement();
            OptionDataModel option = SysService.OPTION_SERVICE.getOptionById(chooseControl.getOptionId());
            if (option==null){
                String text = "有个功能需要添加一个\"" + chooseControl.getOptionName() + "\"内容选择组件，可这个选项已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（打开文件异常）要添加\"" + chooseControl.getOptionName() + "\"这个选项，该选项早已被删除";
                CodeGenerationFrameHolder.errorLogging(text, logtext);
            }else {
                if (OptionDataModel.EXECLUSIVE == option.getOptionType()) {
                    ContentChooseRadioCombobox combobox = new ContentChooseRadioCombobox(
                            codeGenerationalOpratingContainerParam, (ContentChooseMeta) metaElement);
                    setCaretPosition(doc.getLength());
                    insertComponent(combobox);

                } else if (OptionDataModel.MULTIPLE == option.getOptionType()) {
                    ContentChooseMultiSelectCombobox combobox = new ContentChooseMultiSelectCombobox(
                            codeGenerationalOpratingContainerParam, (ContentChooseMeta) metaElement);
                    setCaretPosition(doc.getLength());
                    insertComponent(combobox);
                }
            }
        } else if (metaElement instanceof FunctionAddMeta) {
            FunctionAddInputPaneForCodeGeneration functionAddInputPaneForCodeGeneration = new FunctionAddInputPaneForCodeGeneration(
                    codeGenerationalOpratingContainerParam, (FunctionAddMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(functionAddInputPaneForCodeGeneration);

        } else if (metaElement instanceof CustomVariableMeta) {
            CustomVariableControl controlElement = (CustomVariableControl) metaElement.getControlElement();
            if (controlElement.getOnlyAddOne() == CustomVariableControl.TRUE_) {
                CustomVariableRadioTextField inputBox = new CustomVariableRadioTextField(
                        codeGenerationalOpratingContainerParam, (CustomVariableMeta) metaElement);
                setCaretPosition(doc.getLength());
                insertComponent(inputBox);

            } else if (controlElement.getOnlyAddOne() == CustomVariableControl.FALSE_) {
                CustomVariableMutipleInputBox inputBox = new CustomVariableMutipleInputBox(
                        codeGenerationalOpratingContainerParam, (CustomVariableMeta) metaElement);
                setCaretPosition(doc.getLength());
                insertComponent(inputBox);
            }

        } else if (metaElement instanceof VariableMeta) {
            VariableComboBox variableComboBox = new VariableComboBox(
                    codeGenerationalOpratingContainerParam, (VariableMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(variableComboBox);

        } else if (metaElement instanceof ConstantMeta) {
            ConstantMutipleInoutBox constantMutipleInoutBox = new ConstantMutipleInoutBox(
                    codeGenerationalOpratingContainerParam, (ConstantMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(constantMutipleInoutBox);

        } else if (metaElement instanceof FileSelectorMeta) {
            FileSelectorTextFieldWithPicture fileSelectorTextFieldWithPicture = new FileSelectorTextFieldWithPicture(
                    codeGenerationalOpratingContainerParam, (FileSelectorMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(fileSelectorTextFieldWithPicture);

        } else if (metaElement instanceof NoteMeta) {
            NoteButton noteButton = new NoteButton(codeGenerationalOpratingContainerParam, (NoteMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(noteButton);

        } else if (metaElement instanceof PictureMeta) {
            PictureButton pictureButton = new PictureButton(codeGenerationalOpratingContainerParam,
                    (PictureMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(pictureButton);

            PictureControl pictureControl = ((PictureMeta) metaElement).getControlElement();
            OpratingContainerInterface opratingContainer = codeGenerationalOpratingContainerParam
                    .getThisOpratingContainer();
            File pitureFolder = new File(opratingContainer.getImageRootPath().getAbsolutePath() + File.separator
                    + pictureControl.getPitureFolderName());
            if (pitureFolder.isDirectory() == false) {
                String text = "有个功能要添加一个图片组件，可组件对应的文件夹已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（打开文件异常）有个图片组件，对应文件夹早已被删除，对应路径：" + pitureFolder.getAbsolutePath();
                CodeGenerationFrameHolder.errorLogging(text, logtext);
            }

        } else if (metaElement instanceof CodeInputMeta) {
            CodeInputBox codeInputBox = new CodeInputBox(codeGenerationalOpratingContainerParam,
                    (CodeInputMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(codeInputBox);

        } else if (metaElement instanceof InfrequentlyUsedSettingMeta) {
            InfrequentlyUsedSettingButton infrequentlyUsedSettingButton = new InfrequentlyUsedSettingButton(
                    codeGenerationalOpratingContainerParam.getThisOpratingContainer(),
                    codeGenerationalOpratingContainerParam, (InfrequentlyUsedSettingMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(infrequentlyUsedSettingButton);
//            if (PathFind.COMMAND_TYPE == codeGenerationalOpratingContainerParam.getParentPathFind().getMetaType()) {
//
//                InfrequentlyUsedSettingButton infrequentlyUsedSettingButton = new InfrequentlyUsedSettingButton(
//                        codeGenerationalOpratingContainerParam.getFirstCommandOpratingContainer(),
//                        codeGenerationalOpratingContainerParam, (InfrequentlyUsedSettingMeta) metaElement);
//                setCaretPosition(doc.getLength());
//                insertComponent(infrequentlyUsedSettingButton);
//
//            } else if (PathFind.FORMAT_TYPE == codeGenerationalOpratingContainerParam.getParentPathFind().getMetaType()) {
//
//                InfrequentlyUsedSettingButton infrequentlyUsedSettingButton = new InfrequentlyUsedSettingButton(
//                        codeGenerationalOpratingContainerParam.getFormatContainer(),
//                        codeGenerationalOpratingContainerParam, (InfrequentlyUsedSettingMeta) metaElement);
//                setCaretPosition(doc.getLength());
//                insertComponent(infrequentlyUsedSettingButton);
//
//            }

        } else if (metaElement instanceof CustomMethodMeta) {
            CustomMethodNameTextField customMethodNameTextField = new CustomMethodNameTextField(
                    codeGenerationalOpratingContainerParam, (CustomMethodMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(customMethodNameTextField);

        } else if (metaElement instanceof MethodChooseMeta) {
//            MethodChooseCombobox methodChooseCombobox = new MethodChooseCombobox(codeGenerationalOpratingContainerParam,
//                    (MethodChooseMeta) metaElement);
//            setCaretPosition(doc.getLength());
//            insertComponent(methodChooseCombobox);

            MethodChoiceMenu methodChoiceMenu = new MethodChoiceMenu(codeGenerationalOpratingContainerParam,
                    (MethodChooseMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(methodChoiceMenu);

        } else if (metaElement instanceof CorrespondingAdditionalDefaultFileMeta) {
            CorrespondingAdditionalDefaultFileCombobox correspondingAdditionalDefaultFileCombobox = new CorrespondingAdditionalDefaultFileCombobox(
                    codeGenerationalOpratingContainerParam,
                    (CorrespondingAdditionalDefaultFileMeta) metaElement);
            setCaretPosition(doc.getLength());
            insertComponent(correspondingAdditionalDefaultFileCombobox);
        }
    }


    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof FunctionAddInputPaneForCodeGeneration) {
                    ((FunctionAddInputPaneForCodeGeneration) cTemp).delModuleOpratingContainerFromComponent(moduleId);
                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    ((InfrequentlyUsedSettingButton) cTemp).delModuleOpratingContainerFromComponent(moduleId);
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 删除该面板里面所有代码是调用
     */
    @Override
    public void delThis() {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof CodeGenerationComponentInterface) {
                    ((CodeGenerationComponentInterface) cTemp).delThis();
                }
            }
            end = e0.getEndOffset();
        }
    }

    @Override
    public void collapseThis() {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());
                if (cTemp instanceof CodeGenerationComponentInterface) {
                    ((CodeGenerationComponentInterface) cTemp).collapseThis();
                }
            }
            end = e0.getEndOffset();
        }
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof FunctionAddInputPaneForCodeGeneration) {
                    opratingContainerList
                            .addAll(((FunctionAddInputPaneForCodeGeneration) cTemp).getAllOpratingContainer());

                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    opratingContainerList.addAll(((InfrequentlyUsedSettingButton) cTemp).getAllOpratingContainer());
                }
            }
            end = e0.getEndOffset();
        }
        return opratingContainerList;
    }

    @Override
    public void functionNameSynchronousChange(int functionNameId) {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof MethodChoiceMenu) {
                    ((MethodChoiceMenu) cTemp).synchronousChange(functionNameId);

                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    ((InfrequentlyUsedSettingButton) cTemp).functionNameSynchronousChange(functionNameId);
                }
            }
            end = e0.getEndOffset();
        }
    }

    @Override
    public void functionNameSynchronousDelete(int functionNameId) {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof MethodChoiceMenu) {
                    ((MethodChoiceMenu) cTemp).synchronousDelete(functionNameId);

                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    ((InfrequentlyUsedSettingButton) cTemp).functionNameSynchronousDelete(functionNameId);
                }
            }
            end = e0.getEndOffset();
        }
    }

    @Override
    public void variableSynchronousChange(int variableId) {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof VariableComboBox) {
                    ((VariableComboBox) cTemp).synchronousChange(variableId);

                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    ((InfrequentlyUsedSettingButton) cTemp).variableSynchronousChange(variableId);
                }
            }
            end = e0.getEndOffset();
        }
    }

    @Override
    public void variableSynchronousDelete(int variableId) {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof VariableComboBox) {
                    ((VariableComboBox) cTemp).synchronousDelete(variableId);

                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    ((InfrequentlyUsedSettingButton) cTemp).variableSynchronousDelete(variableId);
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 设置变量组件里面自动选择的值（仅在第一次创建代码生成界面的时候使用）
     */
    public void setNoUserSelectionIsRequiredValue() {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof VariableComboBox) {
                    ((VariableComboBox) cTemp).setNoUserSelectionIsRequiredValue();

                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    ((InfrequentlyUsedSettingButton) cTemp).setNoUserSelectionIsRequiredValue();
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 打开项目文件的时候才用，选择之前选好的值
     */
    public void showSelectedValue() {
        int end = 0;
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof VariableComboBox) {
                    ((VariableComboBox) cTemp).showSelectedValue();

                } else if (cTemp instanceof InfrequentlyUsedSettingButton) {
                    ((InfrequentlyUsedSettingButton) cTemp).showSelectedValue();
                }
            }
            end = e0.getEndOffset();
        }
    }

}
