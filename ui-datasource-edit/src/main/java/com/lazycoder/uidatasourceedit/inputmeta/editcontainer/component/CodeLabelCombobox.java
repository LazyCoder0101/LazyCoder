package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class CodeLabelCombobox extends JComboBox<CodeLabel> {

    public CodeLabelCombobox() {
        setRenderer(new CodeUsePropetyComboboxCellRenderer());
        addPopupMenuListener(popupMenuListener);
        updateData(SysService.CODE_LABEL_SERVICE.getCodeLabelList());
        setSelectedIndex(0);
    }

    private void updateData(List<CodeLabel> codeLabelList) {
        CodeLabel originalCodeLabel = getSelectedItem() == null ? null : (CodeLabel) getSelectedItem();
        removeAllItems();
        DefaultComboBoxModel<CodeLabel> model = new DefaultComboBoxModel<>();
        model.addElement(CodeLabel.NO_CODE_LABEL);
        if (codeLabelList != null) {
            for (CodeLabel codeLabelTemp : codeLabelList) {
                model.addElement(codeLabelTemp);
            }
        }
        setModel(model);
        if (originalCodeLabel != null) {
            setSelectedCodeLabel(originalCodeLabel.getCodeLabelId());
        }
    }

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            updateData(SysService.CODE_LABEL_SERVICE.getCodeLabelList());
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    };

    public void setSelectedCodeLabel(String codeLabelId) {
        if (codeLabelId != null) {
            ComboBoxModel<CodeLabel> model = getModel();
            CodeLabel temp;
            for (int i = 0; i < model.getSize(); i++) {
                temp = model.getElementAt(i);
                if (temp != CodeLabel.NO_CODE_LABEL &&
                        null != temp.getCodeLabelId()) {
                    if (temp.getCodeLabelId().equals(codeLabelId)) {
                        setSelectedIndex(i);
                        break;
                    }
                }
            }
        } else {
            setSelectedIndex(0);
        }
    }

    public boolean isNoCodeLabel() {
        return getSelectedIndex() == 0 ? true : false;
    }

    /**
     * 获取选择的代码标签
     *
     * @return
     */
    public String getCodeLabelId() {
        String codeLabelStr = null;
        Object selectedCodeLabel = getSelectedItem();
        if (selectedCodeLabel != null && selectedCodeLabel instanceof CodeLabel) {
            CodeLabel selectedCodeLabelTemp = (CodeLabel) selectedCodeLabel;

            if (CodeLabel.NO_CODE_LABEL.getCodeLabelId() == selectedCodeLabelTemp.getCodeLabelId()) {
                codeLabelStr = CodeLabel.NO_CODE_LABEL.getCodeLabelId();
            } else {
                codeLabelStr = selectedCodeLabelTemp.getCodeLabelId();
            }
        }
        return codeLabelStr;
    }


    // 此类用于显示提示信息
    @SuppressWarnings("rawtypes")
    class CodeUsePropetyComboboxCellRenderer implements ListCellRenderer {

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        JLabel renderer;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            // 下拉列表每个选项显示的就是这个
            renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            CodeLabel codeLabel = (CodeLabel) value;
            renderer.setText(codeLabel.getCodeLabelShowText());

            if (codeLabel == CodeLabel.NO_CODE_LABEL) {
                renderer.setForeground(new Color(32, 73, 238));
                Font oriFont = renderer.getFont();
                Font rFont = new Font(oriFont.getName(), Font.BOLD, oriFont.getSize());
                renderer.setFont(rFont);
            }
            return renderer;
        }

    }

}
