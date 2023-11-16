package com.lazycoder.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

//	/**
//	 * 统计行数
//	 *
//	 * @param str
//	 * @return
//	 */
//	public static int countLines(String str) {
//		String[] lines = str.split("\r\n|\r|\n");
//		return lines.length;
//	}

    /**
     * 判断java变量名是否合法（在系统通过该方法简单粗暴地判断变量名和方法名是否合法）
     *
     * @param name
     * @return
     */
    public static boolean checkVariableName(String name) {
        boolean flag = true;
        if (name == null || name.length() == 0 || name.trim() == "" || isSpecialChar(name) == true || isContainChinese(name) == true) {

            flag = false;
        } else {
            char first = name.charAt(0);
            if (!isFirstChar(first)) {
                flag = false;
            } else {
                char c;
                for (int i = 1; i < name.length(); i++) {
                    c = name.charAt(i);
                    if ((!Character.isLetterOrDigit(c)) && (c != '_')) {
//					if (!Character.isLetterOrDigit(c)) {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }


    /**
     * 摘自 blog.csdn.net/BrucegaoChina/article/details/84003454
     *
     * @param c character
     * @return true if the char contains in the list else return false
     */
    private static boolean isFirstChar(char c) {
        switch (c) {
            case 'A':
                return true;
            case 'B':
                return true;
            case 'C':
                return true;
            case 'D':
                return true;
            case 'E':
                return true;
            case 'F':
                return true;
            case 'G':
                return true;
            case 'H':
                return true;
            case 'I':
                return true;
            case 'J':
                return true;
            case 'K':
                return true;
            case 'L':
                return true;
            case 'M':
                return true;
            case 'N':
                return true;
            case 'O':
                return true;
            case 'P':
                return true;
            case 'Q':
                return true;
            case 'R':
                return true;
            case 'S':
                return true;
            case 'T':
                return true;
            case 'U':
                return true;
            case 'V':
                return true;
            case 'W':
                return true;
            case 'X':
                return true;
            case 'Y':
                return true;
            case 'Z':
                return true;
            case 'a':
                return true;
            case 'b':
                return true;
            case 'c':
                return true;
            case 'd':
                return true;
            case 'e':
                return true;
            case 'f':
                return true;
            case 'g':
                return true;
            case 'h':
                return true;
            case 'i':
                return true;
            case 'j':
                return true;
            case 'k':
                return true;
            case 'l':
                return true;
            case 'm':
                return true;
            case 'n':
                return true;
            case 'o':
                return true;
            case 'p':
                return true;
            case 'q':
                return true;
            case 'r':
                return true;
            case 's':
                return true;
            case 't':
                return true;
            case 'u':
                return true;
            case 'v':
                return true;
            case 'w':
                return true;
            case 'x':
                return true;
            case 'y':
                return true;
            case 'z':
                return true;
            case '_':
                return true;
            case '$':
                return true;
            default:
                return false;
        }
    }

    /**
     * 判断是否含有特殊字符（摘自https://blog.csdn.net/iblade/article/details/70892276?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522162350625716780357283921%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=162350625716780357283921&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_v2~rank_v29-10-70892276.first_rank_v2_pc_rank_v29&utm_term=java++%E9%9D%9E%E6%B3%95%E5%AD%97%E7%AC%A6+%E5%88%A4%E6%96%AD&spm=1018.2226.3001.4187）
     *
     * @param str 校验的字符串
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
//        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        String regEx = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符  false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {
        if (str == null) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
            Matcher m = pattern.matcher(str);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }


}
