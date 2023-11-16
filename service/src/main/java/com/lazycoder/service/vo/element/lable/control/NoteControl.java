package com.lazycoder.service.vo.element.lable.control;

import com.lazycoder.service.vo.element.lable.NoteElement;
import lombok.Data;

/**
 * 备注
 *
 * @author Administrator
 */
@Data
public class NoteControl extends NoteElement {

    /**
     * 备注内容
     */
    private String note;

    private boolean asAUsageReminder = false;

    public NoteControl() {
        // TODO Auto-generated constructor stub
        super();
    }

    @Override
    public NoteElement controlComponentInformation() {
        NoteElement element = new NoteElement();
        element.setThisName(getThisName());
        return element;
    }

}
