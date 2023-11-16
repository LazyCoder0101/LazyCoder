package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode;

import com.lazycoder.database.model.ImportCode;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class ImportCodeInputPane extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 7604375485833281168L;

    private JTextArea textArea;

    private ImportLable importLable;

    // private ImportMarkButton markButton;

    private int ordinal = 0;

    private CodeLabelCombobox codeLabelCombobox;

    private ImportCodeInputPane() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 新建
     *
     * @param ordinal
     */
    public ImportCodeInputPane(int ordinal) {
        this();
        this.ordinal = ordinal;
        init();
        importLable.setLabelText(ordinal);
    }

    /**
     * 还原
     *
     * @param importCode
     */
    public ImportCodeInputPane(ImportCode importCode) {
        this();
        this.ordinal = importCode.getOrdinal();
        init();
        importLable.setLabelText(importCode.getOrdinal());
        // markButton.setText(importCode.getMarkName());
        textArea.setText(importCode.getCode());
        codeLabelCombobox.setSelectedCodeLabel(importCode.getCodeLabelId());
    }

    private void init() {

        Box hbox1 = Box.createHorizontalBox();
        importLable = new ImportLable();
        hbox1.add(importLable);

        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(documentListener);
        JScrollPane scrollPane = new JScrollPane(textArea);
        hbox1.add(scrollPane);

        Box hbox2 = Box.createHorizontalBox();
        JLabel clLabel = new JLabel("代码标签：");
        clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hbox2.add(clLabel);
        codeLabelCombobox = new CodeLabelCombobox();
        codeLabelCombobox.addPopupMenuListener(popupMenuListener);
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

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            importLable.getMarkElement().setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    };

    private DocumentListener documentListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            Window window = SwingUtilities.getWindowAncestor(ImportCodeInputPane.this);
            if (window != null) {
                window.validate();

            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            Window window = SwingUtilities.getWindowAncestor(ImportCodeInputPane.this);
            if (window != null) {
                window.validate();

            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            Window window = SwingUtilities.getWindowAncestor(ImportCodeInputPane.this);
            if (window != null) {
                window.validate();

            }
        }
    };


    /**
     * 获取引入代码
     *
     * @return
     */
    public ImportCode getImportCode() {
        ImportCode importCode = new ImportCode();
        // importCode.setMarkName(markButton.getText().trim());
        importCode.setCode(textArea.getText());
        importCode.setOrdinal(ordinal);
        importCode.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        return importCode;
    }

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
