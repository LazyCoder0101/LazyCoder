package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code;

import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.MainSetMarkScutcheon;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

/**
 * 必填模板的副格式代码
 *
 * @author admin
 */
public class MainSubFormatCodePane extends ImportCodeTextPane {

    /**
     *
     */
    private static final long serialVersionUID = 7190192429280826341L;

    private JMenuItem mainSetMenuItem;

    public MainSubFormatCodePane(int sort) {
        super(sort);
        CodeCondition codeCondition = new CodeCondition(false, true);
        this.menuInit(codeCondition);
    }

    @Override
    protected void setMenuItemText() {
        // TODO Auto-generated method stub
        super.setMenuItemText();

        HTMLText mainsetMarkHtmlText = new HTMLText();
        HtmlPar mainsetMarkPar = new HtmlPar();
        mainsetMarkPar.addText("在此填写", false);
        mainsetMarkPar.addColorText("必填模板设置", HtmlPar.RED, false);
        mainsetMarkPar.addText("代码", false);
        mainsetMarkHtmlText.addPar(mainsetMarkPar);
        mainSetMenuItem = new JMenuItem(mainsetMarkHtmlText.getHtmlContent());
    }

    @Override
    public void menuInit(CodeCondition codeCondition) {
        super.menuInit(codeCondition);

        addMarkmenu.add(mainSetMenuItem);
        mainSetMenuItem.addActionListener(mainSetMenuItemActionListener);
        mainSetMenuItem.addMouseListener(mainSetMarkMenuItemMouseAdapter);
    }

    private ActionListener mainSetMenuItemActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == mainSetMenuItem) {
                MainSetMarkScutcheon scutcheon = new MainSetMarkScutcheon();
                MainSubFormatCodePane.this.insertComponent(scutcheon);

                if (FormatEditPaneHolder.mainSetCodeEditPane != null) {
                    FormatEditPaneHolder.mainSetCodeEditPane.setUIResponse(false);
                }
            }
        }
    };

    /**
     * 获取主代码格式
     *
     * @return
     */
    public GeneralFileFormat getMainCodeFormat(int codeOrdinal) {
        GeneralFileFormat mainCodeFormat = GeneralFileFormat.createMainFormatFile();
        mainCodeFormat.setFileType(GeneralFileFormat.TYPE_ADDITIONAL_FORMAT_FILE);
        setCodeModel(mainCodeFormat, codeOrdinal);
        return mainCodeFormat;
    }

    private MouseAdapter mainSetMarkMenuItemMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            if (e.getSource() == mainSetMenuItem) {
                if (FormatEditPaneHolder.mainSetCodeEditPane != null) {
                    FormatEditPaneHolder.mainSetCodeEditPane.setUIResponse(true);
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            if (e.getSource() == mainSetMenuItem) {
                if (FormatEditPaneHolder.mainSetCodeEditPane != null) {
                    FormatEditPaneHolder.mainSetCodeEditPane.setUIResponse(false);
                }
            }
        }
    };


}
