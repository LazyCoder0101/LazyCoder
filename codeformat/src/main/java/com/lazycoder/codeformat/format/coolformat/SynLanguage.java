package com.lazycoder.codeformat.format.coolformat;

public class SynLanguage {

	public static int getNLanguage(String languageType) {
		String LanguageType = languageType.toUpperCase();
		switch (LanguageType) {
			case "ACTIONSCRIPT":
				return 0;
			case "ADA":
				return 1;
			case "ASM":
				return 2;
			case "ASP":
				return 3;
			case "AUTOHOTKEY":
				return 4;
			case "AUTOIT":
				return 5;
			case "BATCH":
				return 6;
			case "COBOL":
				return 7;
			case "CPP":
				return 8;
			case "CS":
				return 9;
			case "CSS":
				return 10;
			case "D":
				return 11;
			case "FORTRAN":
				return 12;
			case "HASKELL":
				return 13;
			case "HTML":
				return 14;
			case "INI":
				return 15;
			case "JAVA":
				return 16;
			case "JAVASCRIPT":
				return 17;
			case "JSON":
				return 18;
			case "JSP":
				return 19;
			case "LISP":
				return 20;
			case "LUA":
				return 21;
			case "NORMALTEXT":
				return 22;
			case "OBJECTIVEC":
				return 23;
			case "PASCAL":
				return 24;
			case "PERL":
				return 25;
			case "PHP":
				return 26;
			case "PYTHON":
				return 27;
			case "RUBY":
				return 28;
			case "SQL":
				return 29;
			case "VB":
				return 30;
			case "VERILOG":
				return 31;
			case "VHDL":
				return 32;
			case "XML":
				return 33;
			default:
				return 34;
		}
	}
}
