package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyIconLabel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import lombok.Getter;

public class CodeLabelLabel extends MyIconLabel {

    private Dimension dimension = new Dimension((int) (0.18 * SysUtil.SCREEN_SIZE.width), 40);

    private final static ImageIcon ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "SysSettingPane" + File.separator + "DBEditPane" + File.separator + "代码标签.png");

    @Getter
    private CodeLabel codeLabel = null;

    public CodeLabelLabel(CodeLabel codeLabel) {
        init(codeLabel.getCodeLabelShowText(), ICON);
        this.codeLabel = codeLabel;
        addMouseListener(thisMouse);
        setPreferredSize(dimension);
    }

    /**
     * 获取这个通用选项标签对应的通用选项名
     *
     * @return
     */
    public String getShowText() {
        return codeLabel.getCodeLabelShowText();
    }

    private MouseAdapter thisMouse = new MouseAdapter() {

        @Override
        public void mouseExited(MouseEvent e) {
            // 退出时字体变黑
            textLabel.setForeground(Color.black);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // 进入时字体变蓝
            textLabel.setForeground(Color.blue);
        }
    };

}
