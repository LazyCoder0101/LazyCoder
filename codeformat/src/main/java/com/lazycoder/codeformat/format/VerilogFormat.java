package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.utils.FileUtil;
import java.io.File;
import net.ericsonj.verilog.FileFormat;
import net.ericsonj.verilog.FormatSetting;
import net.ericsonj.verilog.IndentationStyle;
import net.ericsonj.verilog.StyleImp;
import net.ericsonj.verilog.VerilogFile;
import net.ericsonj.verilog.decorations.AlignBlockingAssignments;
import net.ericsonj.verilog.decorations.AlignLineComment;
import net.ericsonj.verilog.decorations.AlignNoBlockingAssignments;
import net.ericsonj.verilog.decorations.ModuleAlign;
import net.ericsonj.verilog.decorations.SpacesBeforeIfStatement;
import net.ericsonj.verilog.decorations.SpacesBlockingAssignment;
import net.ericsonj.verilog.decorations.SpacesInParentheses;
import net.ericsonj.verilog.decorations.SpacesInSquareBrackets;
import net.ericsonj.verilog.decorations.SpacesNoBlockingAssignment;
import net.ericsonj.verilog.decorations.SpacesTrailingComment;

public class VerilogFormat implements FormatInterface {

	private static final String CONFIG_FOLDER = "verilog";// 放置配置文件的文件夹名

	private VerilogFile format = null;

	public VerilogFormat() {
		// TODO Auto-generated constructor stub
		File settingFile = FileUtil.getStaticResourceFile(CONFIG_FOLDER + File.separator + ".verilog-format.properties");
		if (settingFile!=null){
			FormatSetting formatSetting = new FormatSetting(settingFile);// .verilog-format.properties：配置文件文件名
			FileFormat fileFormat = new FileFormat(formatSetting);
			format = new VerilogFile(fileFormat);
			format.addStyle(new IndentationStyle());
			format.addStyle(new ModuleAlign());
			format.addStyle(new SpacesTrailingComment());
			format.addStyle(new SpacesBeforeIfStatement());
			format.addStyle(new SpacesBlockingAssignment());
			format.addStyle(new SpacesNoBlockingAssignment());
			format.addStyle(new SpacesInParentheses());
			format.addStyle(new SpacesInSquareBrackets());
			format.addStyle(new AlignBlockingAssignments());
			format.addStyle(new AlignNoBlockingAssignments());
			format.addStyle(new AlignLineComment());
		}
	}

	public VerilogFormat(File settingFile) {
		// TODO Auto-generated constructor stub
		if (settingFile!=null){
			FormatSetting formatSetting = new FormatSetting(settingFile);// .verilog-format.properties：配置文件文件名
			FileFormat fileFormat = new FileFormat(formatSetting);
			format = new VerilogFile(fileFormat);
			format.addStyle(new IndentationStyle());
			format.addStyle(new ModuleAlign());
			format.addStyle(new SpacesTrailingComment());
			format.addStyle(new SpacesBeforeIfStatement());
			format.addStyle(new SpacesBlockingAssignment());
			format.addStyle(new SpacesNoBlockingAssignment());
			format.addStyle(new SpacesInParentheses());
			format.addStyle(new SpacesInSquareBrackets());
			format.addStyle(new AlignBlockingAssignments());
			format.addStyle(new AlignNoBlockingAssignments());
			format.addStyle(new AlignLineComment());
		}
	}

	@Override
	public String formatter(String fromSource) {
		// TODO Auto-generated method stub
		format.memFile.clear();
		String[] tlist = fromSource.split("\n");
		for (String temp : tlist) {
			format.memFile.add(temp.trim());
		}
		for (StyleImp style : format.styles) {
			style.applyStyle(format.format, format.memFile);
		}
		StringBuilder out = new StringBuilder();
		for (String line : format.memFile) {
			out.append(line + FileFormat.LF);
		}

		return out.toString();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

}
