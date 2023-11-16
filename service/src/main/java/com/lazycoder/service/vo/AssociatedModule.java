package com.lazycoder.service.vo;

import com.lazycoder.database.model.Module;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联模块
 */
@Data
@NoArgsConstructor
public class AssociatedModule {

    private Module module = null;

    private ArrayList<Module> currentAssociatedModuleList = new ArrayList<>();

    public void addAssociatedModuleList(AssociatedModule associatedModule) {
        if (associatedModule != null) {
            boolean flag = false;
            for (Module temp : associatedModule.currentAssociatedModuleList) {
                flag = false;
                for (Module thisTemp : this.currentAssociatedModuleList) {
                    if (thisTemp.getModuleId().equals(temp.getModuleId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    this.currentAssociatedModuleList.add(temp);
                }
            }
        }
    }

}
