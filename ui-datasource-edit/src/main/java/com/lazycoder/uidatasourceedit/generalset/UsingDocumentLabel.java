package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyIconLabel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;

public class UsingDocumentLabel extends MyIconLabel {

    private Dimension dimension = new Dimension( (int) (0.24*SysUtil.SCREEN_SIZE.width)+3, 40);

    private static ImageIcon icon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "SysSettingPane" + File.separator + "DBEditPane" + File.separator + "document.png");

    public UsingDocumentLabel(String text) {
        init(text, icon);
        addMouseListener(thisMouse);
        setPreferredSize(dimension);
    }

    /**
     * 获取这个标签对应的使用文档的文件名
     * @return
     */
    public String getUsingDocFileName(){
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
