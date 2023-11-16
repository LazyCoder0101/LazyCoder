package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.MainSetMarkScutcheon;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import lombok.Getter;
import lombok.Setter;

public class MainDeafaultFormatCodePane extends AbstractFormatCodeInputPane implements BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = -7517312515779480205L;

    /**
     * 是否写入了默认文件名
     */
    @Getter
    @Setter
    private int defaultFilenameSetting = FALSE_;

    private JMenuItem mainSetMenuItem, setDeafaultCodeFileNameMenuItem;

    public MainDeafaultFormatCodePane(int serialNumber) {
        super(serialNumber);
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
        setDeafaultCodeFileNameMenuItem = new JMenuItem("设置默认文件名");
        setDeafaultCodeFileNameMenuItem.setForeground(Color.BLUE);
        setDeafaultCodeFileNameMenuItem.addActionListener(menuItemActionListener);
        theMenu.add(setDeafaultCodeFileNameMenuItem, 3);
        addMarkmenu.add(mainSetMenuItem);
        mainSetMenuItem.addActionListener(menuItemActionListener);
        mainSetMenuItem.addMouseListener(mainSetMarkMenuItemMouseAdapter);
    }

    private ActionListener menuItemActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == mainSetMenuItem) {
                MainSetMarkScutcheon scutcheon = new MainSetMarkScutcheon();
                MainDeafaultFormatCodePane.this.insertComponent(scutcheon);

                if (FormatEditPaneHolder.mainSetCodeEditPane != null) {
                    FormatEditPaneHolder.mainSetCodeEditPane.setUIResponse(false);
                }
            } else if (e.getSource() == setDeafaultCodeFileNameMenuItem) {
                setDeafaultCodeFileName();
            }
        }
    };

    /**
     * 设置当前默认文件名
     */
    public void setDeafaultCodeFileName() {
    }

    /**
     * 获取主代码格式
     *
     * @return
     */
    public GeneralFileFormat getMainCodeFormat(int codeOrdinal) {
        GeneralFileFormat mainCodeFormat = GeneralFileFormat.createMainFormatFile();
        mainCodeFormat.setFileType(GeneralFileFormat.TYPE_DEFAULT_FORMAT_FILE);
        mainCodeFormat.setDefaultFilenameSetting(defaultFilenameSetting);
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
