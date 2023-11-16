package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.element.lable.code.FileSelectorCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.FileSelectorLabel;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileSelectorCodeLabel extends FileSelectorLabel implements CodeLabelInterface, BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = -4689004419061335823L;
    /**
     * 是否填写路径到源码
     */
    private int writePathToSourceOrNot = TRUE_;

    private FileSelectorCodeElement codeElement = new FileSelectorCodeElement();

    /**
     * 新建
     */
    public FileSelectorCodeLabel(String name) {
        // TODO Auto-generated constructor stub
        init(name);
    }

    /**
     * 还原
     *
     * @param codeElement
     */
    public FileSelectorCodeLabel(FileSelectorCodeElement codeElement) {
        // TODO Auto-generated constructor stub
        this.codeElement = codeElement;
        init(codeElement.getThisName());
    }

    private void init(String name) {
        setText(name);
        setName(name);
        setUI(new ControlLabelButtonUI());
        addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String[] str = {"是", "否"};
            String selectedStr = "是";
            if (false == codeElement.isWrittenWithPath()) {
                selectedStr = "否";
            }
            Object out = LazyCoderOptionPane.showInputDialog(null, "填写这里时，是否需要填写路径？", "更改组件属性", 1, null, str, selectedStr);
            if ("是".equals(out)) {
                codeElement.setWrittenWithPath(true);
            } else if ("否".equals(out)) {
                codeElement.setWrittenWithPath(false);
            }
        }
    };

    @Override
    public void setName(String name) {
        super.setName(name);
        codeElement.setThisName(name);
    }

    public int getWritePathToSourceOrNot() {
        return writePathToSourceOrNot;
    }

    public void setWritePathToSourceOrNot(int writePathToSourceOrNot) {
        this.writePathToSourceOrNot = writePathToSourceOrNot;
    }

    @Override
    public FileSelectorCodeElement property() {
        // TODO Auto-generated method stub
        return codeElement;
    }

}