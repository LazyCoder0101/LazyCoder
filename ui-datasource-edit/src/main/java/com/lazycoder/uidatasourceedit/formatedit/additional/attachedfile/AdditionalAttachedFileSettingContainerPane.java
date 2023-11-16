package com.lazycoder.uidatasourceedit.formatedit.additional.attachedfile;

import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.component.filesetting.AbstractFileSettingContainerPane;
import java.util.ArrayList;
import java.util.List;

public class AdditionalAttachedFileSettingContainerPane extends AbstractFileSettingContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = 2040019949039419769L;

    private int additionalSerialNumber = 0;

    public AdditionalAttachedFileSettingContainerPane(int additionalSerialNumber) {
        // TODO Auto-generated method stub
        super();
        this.additionalSerialNumber = additionalSerialNumber;
        List<AttachedFile> attachedFileList = SysService.ATTACHED_FILE_SERVICE.getAdditionalAttachedFileList(additionalSerialNumber);

        if (attachedFileList != null) {
            AttachedFile attachedFile;
            AdditionalAttachedFileSettingContainer attachedFileSettingContainer;
            for (int i = 0; i < attachedFileList.size(); i++) {
                attachedFile = attachedFileList.get(i);
                attachedFileSettingContainer = new AdditionalAttachedFileSettingContainer(additionalSerialNumber, attachedFile.getOrdinal(),
                        attachedFile);
                addContainer(attachedFileSettingContainer);
            }
        }
    }

    @Override
    public void addAttachedFileSettingContainer() {
        AdditionalAttachedFileSettingContainer attachedFileSettingContainer = new AdditionalAttachedFileSettingContainer(
                this.additionalSerialNumber, getComponentCount() + 1);
        addContainer(attachedFileSettingContainer);

    }

    public void save() {
        AdditionalAttachedFileSettingContainer attachedFileSettingContainer;
        List<AttachedFile> list = new ArrayList<>();
        for (int i = 0; i < getComponentCount(); i++) {
            attachedFileSettingContainer = (AdditionalAttachedFileSettingContainer) getComponent(i);
            list.add(attachedFileSettingContainer.getAttachedFile());
        }

        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setAdditionalSerialNumber(additionalSerialNumber);
        additionalInfo.setNumOfAttachedFile(getComponentCount());
        SysService.INPUT_DATA_SAVE_SERVICE.saveAdditionalAttachedFileInputData(additionalInfo, list);
    }

}
