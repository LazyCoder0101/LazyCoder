package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.element.lable.FileSelectorElement;
import java.io.File;
import lombok.Data;

/**
 * 文件选择器
 *
 * @author Administrator
 */
@Data
public class FileSelectorControl extends FileSelectorElement implements BaseModel {


    /**
     * 是否复制文件到源码
     */
    private int copyFileToSourceOrNot = TRUE_;

    /**
     * 文件存储路径
     */
    private String filePath = "";

    /**
     * 代码输入屏蔽路径
     */
    private String codeInputShieldingPath = "";

    /**
     * 文件分隔符
     */
    private String fileSeparator = System.getProperty("file.separator");

    /**
     * 添加默认文件的文件夹名（自动生成）
     */
    private String folderName = "";

    public FileSelectorControl() {
        super();
    }


    @Override
    public FileSelectorElement controlComponentInformation() {
        FileSelectorElement element = new FileSelectorElement();
        element.setThisName(getThisName());
        return element;
    }

    /**
     * 获取该文件选择模型对应的文件夹
     *
     * @param parentFolder
     * @return
     */
    public File getCorrespondingFolder(File parentFolder) {
        return new File(parentFolder.getAbsolutePath() + File.separator + folderName);
    }

    /**
     * 获取最终输入到源码的路径
     *
     * @param filePath 文件路径
     * @param codeInputShieldingPath 屏蔽路径
     * @param fileSeparator
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public String getCodeInputPath(String filePath, String codeInputShieldingPath, String fileSeparator) {
        if (filePath == null) {
            filePath = "";
        }
        if (codeInputShieldingPath == null) {
            codeInputShieldingPath = "";
        }
        int index = codeInputShieldingPath.length();
        if ("".equals(codeInputShieldingPath) == false && codeInputShieldingPath.equals(filePath) == false) {
            index = index + 1;
        }
        if (filePath.length() < index) {
            index = 0;
        }
        String str1 = filePath.substring(index);
        return str1.replace(File.separator, fileSeparator);
    }


}
