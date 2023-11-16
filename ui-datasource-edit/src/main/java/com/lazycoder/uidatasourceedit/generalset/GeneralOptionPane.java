package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.GeneralSettingPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameUpdateForBaseControlTextFrame;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class GeneralOptionPane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 5556722234335093338L;

    private JPopupMenu menu;

    private JMenuItem update, del;

    private Box vBox;

    /**
     * Create the panel.
     */
    public GeneralOptionPane() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        this.setLayout(fl);
        vBox = Box.createVerticalBox();
        this.add(vBox);

        updateList();
        addMenu();
        this.setBackground(Color.white);
        this.setSize(200, 500);
        //       this.setVisible(true);
    }

    public void updateList() {
        vBox.removeAll();

        List<OptionDataModel> generalOptionNameList = SysService.OPTION_SERVICE.getGeneralOptionNameList();
        if (generalOptionNameList != null) {
            GeneralOptionLabel generalOptionLabel;
            for (OptionDataModel option : generalOptionNameList) {
                generalOptionLabel = new GeneralOptionLabel(option);
                generalOptionLabel.addMouseListener(thisMouse);
                vBox.add(generalOptionLabel);
            }
        }
        updateUI();
        repaint();
//        Window window = SwingUtilities.getWindowAncestor(this);
//        if (window != null) {
//            window.validate();
//        }
    }

    private void addMenu() {
        menu = new JPopupMenu();
        del = new JMenuItem("删除");
        del.addActionListener(menuLisener);
        update = new JMenuItem("更改");
        update.addActionListener(menuLisener);
        menu.add(del);
        menu.add(update);
    }

    private MouseAdapter thisMouse = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.isMetaDown()) {// 检测鼠标右键单击
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    };

    private ActionListener menuLisener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == del) {
                String getName = ((GeneralOptionLabel) menu.getInvoker()).getOptionName();
                int ii = LazyCoderOptionPane.showConfirmDialog(GeneralOptionPane.this, "确定要删除\"" + getName + "\"选项吗？", "删除通用选项", JOptionPane.YES_NO_OPTION);
                if (ii == JOptionPane.YES_OPTION) {
                    ArrayList<AbstractEditContainerModel> allModelList = DataSourceEditHolder.dataSourceEditFrame.getAllEditContainerModel();
                    if (allModelList != null) {
                        OptionDataModel option = ((GeneralOptionLabel) menu.getInvoker()).getOptionDataModel();
                        if (option != null) {
                            for (AbstractEditContainerModel model : allModelList) {
                                model.delContentChoose(option.getOptionId());
                            }
                        }
                        SysService.OPTION_SERVICE.delOptionById(option.getOptionId());
                        OptionDataModelTempHolder.delOption(option.getOptionId());
                    }
                    if (GeneralSettingPaneHolder.generalOptionPane != null) {
                        GeneralSettingPaneHolder.generalOptionPane.updateList();
                    }
                    LazyCoderOptionPane.showMessageDialog(GeneralOptionPane.this, "已删除\"" + getName + "\"选项！");
                }
            }
            if (e.getSource() == update) {
                OptionDataModel option = ((GeneralOptionLabel) menu.getInvoker()).getOptionDataModel();
                if (option != null) {
                    new ContentChooseFrameUpdateForBaseControlTextFrame(option.getOptionId());
                }
            }
        }
    };

}
