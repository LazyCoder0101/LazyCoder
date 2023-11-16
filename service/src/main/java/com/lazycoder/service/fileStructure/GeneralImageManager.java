package com.lazycoder.service.fileStructure;

import java.io.File;

public class GeneralImageManager {

	private static final String BASE_CODE_INPUT_IMAGE_PATH = SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit"
			+ File.separator + "base_code_content_edit"

			+ File.separator + "code_input_pane";

	public static final String

			ICON_EXPANDED = BASE_CODE_INPUT_IMAGE_PATH + File.separator + "code_label" + File.separator
					+ "expanded.png",
			ICON_FOLDERED = BASE_CODE_INPUT_IMAGE_PATH + File.separator + "code_label" + File.separator
					+ "foldered.png",
			HOVERED_EXPANDED = BASE_CODE_INPUT_IMAGE_PATH + File.separator + "code_label" + File.separator
					+ "hovered_expanded.png",
			HOVERED_FOLDERED = BASE_CODE_INPUT_IMAGE_PATH + File.separator + "code_label" + File.separator
					+ "hovered_foldered.png";
}
