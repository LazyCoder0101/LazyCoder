package com.lazycoder.uidatasourceedit.moduleedit;

import com.lazycoder.database.model.ImportCode;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


public class GeneralImportCodeInputPane extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 7604375485833281168L;

    protected JTextArea textArea;

//    protected ImportLable importLable;

    // private ImportMarkButton markButton;

    protected int ordinal = 0;

    protected CodeLabelCombobox codeLabelCombobox;

    protected Box hbox1, hbox2;

    protected GeneralImportCodeInputPane() {
        // TODO Auto-generated constructor stub
        hbox1 = Box.createHorizontalBox();
        hbox2 = Box.createHorizontalBox();
    }

    /**
     * 新建
     *
     * @param ordinal
     */
    public GeneralImportCodeInputPane(int ordinal) {
        this();
        this.ordinal = ordinal;
        init();
    }

    /**
     * 还原
     *
     * @param importCode
     */
    public GeneralImportCodeInputPane(ImportCode importCode) {
        this();
        this.ordinal = importCode.getOrdinal();
        init();
        // markButton.setText(importCode.getMarkName());
        textArea.setText(importCode.getCode());
        codeLabelCombobox.setSelectedCodeLabel(importCode.getCodeLabelId());
    }

    protected void init() {
        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(documentListener);
        JScrollPane scrollPane = new JScrollPane(textArea);
        hbox1.add(scrollPane);

        JLabel clLabel = new JLabel("代码标签：");
        clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hbox2.add(clLabel);
        codeLabelCombobox = new CodeLabelCombobox();
        Dimension dimension = new Dimension(150, 30);
        codeLabelCombobox.setPreferredSize(dimension);
        codeLabelCombobox.setSize(dimension);
//        codeLabelCombobox.setMinimumSize(dimension);
        codeLabelCombobox.setMaximumSize(dimension);
        hbox2.add(codeLabelCombobox);

        Box vbox = Box.createVerticalBox();
        vbox.add(hbox1);
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(10));

        setViewportView(vbox);
    }

    private DocumentListener documentListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            Window window = SwingUtilities.getWindowAncestor(GeneralImportCodeInputPane.this);
            if (window != null) {
                window.validate();
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            Window window = SwingUtilities.getWindowAncestor(GeneralImportCodeInputPane.this);
            if (window != null) {
                window.validate();

            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            Window window = SwingUtilities.getWindowAncestor(GeneralImportCodeInputPane.this);
            if (window != null) {
                window.validate();
            }
        }
    };


    public int getOrdinal() {
        return ordinal;
    }

    /**
     * 获取代码
     *
     * @return
     */
    public String getCode() {
        return textArea.getText();
    }

    public String getCodeLabelId() {
        return codeLabelCombobox.getCodeLabelId();
    }

}
