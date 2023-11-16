package com.lazycoder.service.vo.element.lable.code;

import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.vo.element.lable.FunctionAddElement;
import lombok.Data;

/**
 * 功能拓展
 *
 * @author Administrator
 */
@Data
public class FunctionAddCodeElement extends FunctionAddElement {

    /**
     * 代码标签
     */
    private String codeLabelId = CodeLabel.NO_CODE_LABEL.getCodeLabelId();

    /**
     * 缩进符
     */
    private String indent = "";

    public FunctionAddCodeElement() {
        super();
    }


    /**
     * 判断codeLabelId有没有设置标记
     * @return
     */
    public boolean ifCodeLabelIdIsNull() {
        boolean flag = false;
        if (this.codeLabelId == null) {
            flag = true;
        }
        return flag;
    }

    /**
     * 匹配
     *
     * @param opratingLabel 操作层标签
     */
//    @Override
//    public boolean match(BaseLableElement opratingLabel) {
//        boolean flag = super.match(opratingLabel);
//        if (flag && opratingLabel instanceof FunctionAddCodeElement) {
//            FunctionAddCodeElement functionAddCodeElementTemp = (FunctionAddCodeElement) opratingLabel;
//            if (!this.codeLabelId.equals(functionAddCodeElementTemp.getCodeLabelId())) {
//                flag = false;
//            }
//        }
//        return flag;
//    }

}
