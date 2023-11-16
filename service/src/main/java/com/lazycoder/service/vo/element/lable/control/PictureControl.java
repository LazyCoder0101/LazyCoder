package com.lazycoder.service.vo.element.lable.control;

import com.lazycoder.service.vo.element.lable.PictureElement;
import java.io.File;
import java.util.ArrayList;
import lombok.Data;

/**
 * 图片
 *
 * @author Administrator
 */
@Data
public class PictureControl extends PictureElement {

	/**
	 * 图片名
	 */
	private String pitureName = "";

	/**
	 * 图片文件夹名（自动生成）
	 */
	private String pitureFolderName = "";

	private ArrayList<String> pitureNoteList = new ArrayList<>();

	public PictureControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 获取该图片模型对应的文件夹
	 *
	 * @param parentFolder
	 * @return
	 */
	public File getCorrespondingFolder(File parentFolder) {
		return new File(parentFolder.getAbsolutePath() + File.separator + pitureFolderName);
	}

	@Override
	public PictureElement controlComponentInformation() {
		PictureElement element = new PictureElement();
		element.setThisName(getThisName());
		return element;
	}
}
