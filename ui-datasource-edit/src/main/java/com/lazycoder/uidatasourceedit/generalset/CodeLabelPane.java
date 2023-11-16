package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class CodeLabelPane extends JPanel {

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
    public CodeLabelPane() {
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

        List<CodeLabel> allCodeLabelList = SysService.CODE_LABEL_SERVICE.getCodeLabelList();
        if (allCodeLabelList != null) {
            CodeLabelLabel codeLabelLabel;
            for (CodeLabel codeLabel : allCodeLabelList) {
                codeLabelLabel = new CodeLabelLabel(codeLabel);
                codeLabelLabel.addMouseListener(thisMouse);
                vBox.add(codeLabelLabel);
            }
        }
        updateUI();
        repaint();
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
            CodeLabelLabel codeLabelLabel = ((CodeLabelLabel) menu.getInvoker());
            String originalShowText = codeLabelLabel.getShowText();

            if (e.getSource() == del) {
                boolean flag = SysService.INPUT_DATA_SAVE_SERVICE.selectExistCodeLabelIdUsed(codeLabelLabel.getCodeLabel().getCodeLabelId());
                if (flag == true) {
                    LazyCoderOptionPane.showMessageDialog(null, "(*^▽^*) 亲，我查了一下，有些代码设置了就是这个代码标签的喔，\n要不对对应代码进行更改后再删除这个标签吧");
                } else {
                    int ii = LazyCoderOptionPane.showConfirmDialog(null, "确定要删除\"" + originalShowText + "\"这个代码标签吗？", "删除代码标签", JOptionPane.YES_NO_OPTION);
                    if (ii == JOptionPane.YES_OPTION) {
                        SysService.CODE_LABEL_SERVICE.delCodeLabelById(codeLabelLabel.getCodeLabel().getCodeLabelId());
                        LazyCoderOptionPane.showMessageDialog(null,
                                "已删除\"" + originalShowText + "\"这个代码标签！\n（ps：记得检查一下还有没有哪里设置用到这个代码标签的，一定要改过来，否则生成代码时会出错的）",
                                "系统提示",JOptionPane.INFORMATION_MESSAGE);
                        updateList();
                    }
                }
            }
            if (e.getSource() == update) {
                String newShowText = LazyCoderOptionPane.showInputDialog(null, "请输入新的标签名称名称：\n", "更改\"" + originalShowText + "\"标签名称", JOptionPane.PLAIN_MESSAGE);
                if (newShowText != null && ("".equals(newShowText.trim()) == false)) {
                    newShowText = newShowText.trim();
                    if (newShowText.equals(originalShowText)) {
                        LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o 怎么新的名字和原来的一样的？");
                    } else {
                        boolean flag = SysService.CODE_LABEL_SERVICE.selectExistByShowText(newShowText);
                        if (flag == true) {//有这名字的标签
                            LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆ 换个名字吧，有这标签了");
                        } else {//没有这标签
                            CodeLabel newCodeLabel = new CodeLabel(codeLabelLabel.getCodeLabel().getCodeLabelId(), newShowText);
                            SysService.CODE_LABEL_SERVICE.updateCodeLabel(newCodeLabel);
                            LazyCoderOptionPane.showMessageDialog(null, "(*^▽^*) 已经改了标签名了");
                            updateList();
                        }
                    }
                }
            }
        }
    };

}
