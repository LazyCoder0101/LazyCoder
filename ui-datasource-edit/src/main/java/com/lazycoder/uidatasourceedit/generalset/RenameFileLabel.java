package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyIconLabel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;

public class RenameFileLabel extends MyIconLabel {

    private Dimension dimension = new Dimension( (int) (0.19* SysUtil.SCREEN_SIZE.width), 40);

    private final static ImageIcon ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "SysSettingPane" + File.separator + "DBEditPane" + File.separator + "rename_file.png");

    public RenameFileLabel(String text) {
        font = new Font("微软雅黑", Font.PLAIN, 16);
        init(text, ICON);
        addMouseListener(thisMouse);
        setPreferredSize(dimension);
    }

    /**
     * 获取这个标签对应的重命名文件的路径
     * @return
     */
    public String getRenameFilePath(){
        return textLabel.getText();
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
