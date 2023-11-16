package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.formdev.flatlaf.FlatLightLaf;
import com.lazycoder.lazycodercommon.vo.ProgramingLanguage;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.ProgramingLanguageCombobox;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CodeInputControlLabel;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.CodeShowTextArea;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;

public class CodeInputEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8108312765531408563L;

    private RTextScrollPane jsp;
    private CodeShowTextArea textArea;
    // private PassingComponentParams passingComponentParams;
    private CodeInputControlLabel controlLabel;

    private ProgramingLanguageCombobox programmingLanguageCombobox = null;

    private OperatingTipButton operatingTip;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new CodeInputEditFrame().setVisible(true);
    }

    private CodeInputEditFrame() {
        super();
        getContentPane().setLayout(null);

        JLabel label1 = new JLabel("输入代码对应编程语言：");
        label1.setBounds(20, 10, 140, 30);
        getContentPane().add(label1);
        programmingLanguageCombobox = new ProgramingLanguageCombobox();
        programmingLanguageCombobox.setBounds(160, 10, 150, 30);
        getContentPane().add(programmingLanguageCombobox);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "10.png").getAbsolutePath()
        );
        operatingTip.setLocation(350, 10);
        getContentPane().add(operatingTip);

        JLabel label2 = new JLabel("默认输入代码：");
        label2.setBounds(20, 50, 200, 30);
        getContentPane().add(label2);

        textArea = new CodeShowTextArea();
        // textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        // textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
        textArea.setCodeFoldingEnabled(true);
        textArea.setFont(new Font("宋体", Font.PLAIN, 20));
        textArea.setEditable(true);
        textArea.setAntiAliasingEnabled(true);
        textArea.setText("");
        // textArea.setToolTipText("请输入默认填写代码");
        // textArea.setSize(300, 240);
        jsp = new RTextScrollPane(textArea);
        jsp.setBounds(20, 80, 560, 250);
        getContentPane().add(jsp);
        ok.setBounds(149, 350, 80, 30);
        cancel.setBounds(333, 350, 80, 30);
        getContentPane().add(ok);
        getContentPane().add(cancel);
        cancel.addActionListener(cancelListener);
        this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 350, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 300, 620, 440);

    }

    // 更改代码组件内容
    public CodeInputEditFrame(CodeInputControlLabel controlLabel) {
        this();
        this.controlLabel = controlLabel;
        this.setTitle("更改代码输入组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");
        this.textArea.setText(controlLabel.getDefaultCode());
        programmingLanguageCombobox.setSelectedItem(ProgramingLanguage.getEncodingTypeBy(this.controlLabel.getControl().getInputProgramingLanguageDictionaryValue()));
        programmingLanguageCombobox.addPopupMenuListener(popupMenuListener);
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 自动生成的方法存根
                ok();
            }
        });
        this.setVisible(true);

    }

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            ProgramingLanguage programingLanguage = programmingLanguageCombobox.getSelectedItem();
            if (programingLanguage != null) {
                textArea.setSyntaxEditingStyle(programingLanguage.getRsyntaxTextAreaEditingStyle());
            }
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    };

    ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            CodeInputEditFrame.this.dispose();
        }
    };

    /**
     * Set the font for all token types.
     *
     * @param textArea The text area to modify.
     * @param font     The font to use.
     */
    public static void setFont(RSyntaxTextArea textArea, Font font) {
        if (font != null) {
            SyntaxScheme ss = textArea.getSyntaxScheme();
            ss = (SyntaxScheme) ss.clone();
            for (int i = 0; i < ss.getStyleCount(); i++) {
                if (ss.getStyle(i) != null) {
                    ss.getStyle(i).font = font;
                }
            }
            textArea.setSyntaxScheme(ss);
            textArea.setFont(font);
        }
    }


    @Override
    public void ok() {
        controlLabel.setDefaultCode(textArea.getText());
        controlLabel.setInputProgramingLanguageDictionaryValue(programmingLanguageCombobox.getSelectedItem().getSysDictionaryValue());
        CodeInputEditFrame.this.dispose();
    }


}
