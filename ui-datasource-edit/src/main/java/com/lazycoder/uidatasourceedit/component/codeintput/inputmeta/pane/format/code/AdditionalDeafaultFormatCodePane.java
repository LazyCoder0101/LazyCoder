package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.AdditionalSetMarkScutcheon;
import com.lazycoder.uidatasourceedit.formatedit.additional.settype.AdditionalSetCodeEditPane;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.text.BadLocationException;
import lombok.Getter;
import lombok.Setter;

//import com.lazycoder.uiutils.utils.StyteString;

public class AdditionalDeafaultFormatCodePane extends AbstractFormatCodeInputPane implements BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 2216185688720500317L;

    /**
     * 是否写入了默认文件名
     */
    @Getter
    @Setter
    private int defaultFilenameSetting = FALSE_;

    /**
     * 可选模板业务方法的标识编号
     */
    @Getter
    private int additionalSerialNumber = 0;

    private JMenuItem additionalSetMenuItem, setDeafaultCodeFileNameMenuItem;

    public AdditionalDeafaultFormatCodePane(int sort, int additionalSerialNumber) {
        super(sort);
        this.additionalSerialNumber = additionalSerialNumber;
        CodeCondition codeCondition = new CodeCondition(false, true);
        this.menuInit(codeCondition);
    }

    @Override
    protected void setMenuItemText() {
        // TODO Auto-generated method stub
        super.setMenuItemText();

        HTMLText htmlText = new HTMLText();
        HtmlPar htmlPar = new HtmlPar();
        htmlPar.addText("在此填写", false);
        htmlPar.addColorText("可选模板设置", HtmlPar.RED, false);
        htmlPar.addText("代码", false);
        htmlText.addPar(htmlPar);
        additionalSetMenuItem = new JMenuItem(htmlText.getHtmlContent());
    }

    @Override
    public void menuInit(CodeCondition codeCondition) {
        super.menuInit(codeCondition);
        setDeafaultCodeFileNameMenuItem = new JMenuItem("设置默认文件名");
        setDeafaultCodeFileNameMenuItem.setForeground(Color.BLUE);
        setDeafaultCodeFileNameMenuItem.addActionListener(menuItemActionListener);
        theMenu.add(setDeafaultCodeFileNameMenuItem, 3);
        addMarkmenu.add(additionalSetMenuItem);
        additionalSetMenuItem.addActionListener(menuItemActionListener);
        additionalSetMenuItem.addMouseListener(additionalSetMarkMenuItemMouseAdapter);
    }

    private ActionListener menuItemActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == additionalSetMenuItem) {
                AdditionalSetMarkScutcheon scutcheon = new AdditionalSetMarkScutcheon();
                AdditionalDeafaultFormatCodePane.this.insertComponent(scutcheon);

                if (FormatEditPaneHolder.additionalEditPane != null && additionalSerialNumber != 0) {
                    AdditionalSetCodeEditPane additionalSetCodeEditPane = FormatEditPaneHolder.additionalEditPane.getAdditionalSetCodeEditPane(additionalSerialNumber);
                    if (additionalSetCodeEditPane != null) {
                        additionalSetCodeEditPane.setUIResponse(false);
                    }
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
     * 获取其他代码格式
     *
     * @return
     */
    public GeneralFileFormat getAdditionalCodeFormat(int codeOrdinal) {
        GeneralFileFormat codeFormat = GeneralFileFormat.createAdditionalFormatFile();
        codeFormat.setPath("");
        codeFormat.setFileType(GeneralFileFormat.TYPE_DEFAULT_FORMAT_FILE);
        setCodeModel(codeFormat, codeOrdinal);
        codeFormat.setDefaultFilenameSetting(defaultFilenameSetting);
        try {
            codeFormat.setFormatContent(getCodeStatementFormat());
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return codeFormat;
    }

    private MouseAdapter additionalSetMarkMenuItemMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            if (e.getSource() == additionalSetMenuItem) {
                if (FormatEditPaneHolder.additionalEditPane != null && additionalSerialNumber != 0) {
                    AdditionalSetCodeEditPane additionalSetCodeEditPane = FormatEditPaneHolder.additionalEditPane.getAdditionalSetCodeEditPane(additionalSerialNumber);
                    if (additionalSetCodeEditPane != null) {
                        additionalSetCodeEditPane.setUIResponse(true);
                    }
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            if (e.getSource() == additionalSetMenuItem) {
                if (FormatEditPaneHolder.additionalEditPane != null && additionalSerialNumber != 0) {
                    AdditionalSetCodeEditPane additionalSetCodeEditPane = FormatEditPaneHolder.additionalEditPane.getAdditionalSetCodeEditPane(additionalSerialNumber);
                    if (additionalSetCodeEditPane != null) {
                        additionalSetCodeEditPane.setUIResponse(false);
                    }
                }
            }
        }
    };

}
