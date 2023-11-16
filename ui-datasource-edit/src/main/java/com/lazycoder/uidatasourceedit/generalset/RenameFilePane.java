package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.service.service.SysService;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


public class RenameFilePane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1814985979019819288L;

//    private static ImageIcon renameIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
//            + "SysSettingPane" + File.separator + "DBEditPane" + File.separator + "rename_file.png");

    private JPopupMenu menu;

    private JMenuItem del;
    private Box vBox;

    /**
     * Create the panel.
     */
    public RenameFilePane() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        this.setLayout(fl);
        vBox = Box.createVerticalBox();
        this.add(vBox);

        updateList();
        addMenu();
        this.setBackground(Color.white);
        this.setSize(200, 500);
        this.setVisible(true);
    }

    /**
     * 获取当前的重命名文件
     *
     * @return
     */
    public ArrayList<String> getRenameFileList() {
        ArrayList<String> list = new ArrayList<String>();
        RenameFileLabel label;
        for (int i = 0; i < vBox.getComponentCount(); i++) {
            if (vBox.getComponent(i) instanceof RenameFileLabel) {
                label = (RenameFileLabel) vBox.getComponent(i);
                list.add(label.getRenameFilePath());
            }
        }
        return list;
    }

    public void updateList() {
        vBox.removeAll();

        RenameFileLabel renameFileLabel;
        ArrayList<String> xxlist = SysService.SYS_PARAM_SERVICE.getRenameFileList();
        for (String renameFilePath : xxlist) {
            renameFileLabel = new RenameFileLabel(renameFilePath);
            renameFileLabel.addMouseListener(thisMouse);
            vBox.add(renameFileLabel);
        }
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.validate();
        }
    }

    private void addMenu() {
        menu = new JPopupMenu();
        del = new JMenuItem("删除");
        del.addActionListener(menuLisener);
        menu.add(del);
    }

    private ActionListener menuLisener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == del) {
                RenameFileLabel theLabel = ((RenameFileLabel) menu.getInvoker());
                vBox.remove(theLabel);
                ArrayList<String> list = getRenameFileList();
                SysService.SYS_PARAM_SERVICE.setRenameFileList(list);
                updateList();
            }
        }
    };

    private MouseAdapter thisMouse = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.isMetaDown()) {// 检测鼠标右键单击
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    };

}
