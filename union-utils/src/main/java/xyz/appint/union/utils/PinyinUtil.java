package xyz.appint.union.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 中文及拼音的装换工具
 *
 * @author vesslan
 */
public class PinyinUtil {
    // 存储百家姓 key:姓的hashcode value:姓的拼音
    public static Map<Integer, String> bjxDic = new HashMap<Integer, String>();

    static {
        try {
            final InputStream in = PinyinUtil.class
                    .getResourceAsStream("baijiaxing.dic");
            BufferedReader b = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));

            String s = null;
            while (true) {
                s = b.readLine();
                if (s == null) {
                    break;
                }
                final String[] split = s.split("\t");
                String cn = split[0];
                String pinyin = split[1];
                bjxDic.put(cn.hashCode(), pinyin);// put
            }
            IOUtils.closeQuietly(b);
            IOUtils.closeQuietly(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";

        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {

            if (nameChar[i] > 128) {

                // System.out.println(nameChar[i]);
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0);
                } catch (Exception e) {
                    pinyinName += nameChar[i];
                    e.printStackTrace();
                }

            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 获取chines中传过来的首字母
     *
     * @param chines
     * @return
     */
    public static String getFirstSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        if (null == chines || chines.trim().length() == 0)
            return "";
        // 仅取第一个汉字
        chines = chines.trim().substring(0, 1);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {// 中文
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if (strs != null) {
                        if (strs.length > 1) {// 如果是多音字则从字典获取
                            String pingyin = bjxDic.get(String.valueOf(nameChar[i]).hashCode());
                            // 如果字典没有,则获取一个默认的值
                            if (null == pingyin || pingyin.length() == 0) {
                                pinyinName.append(strs[0].charAt(0));
                            } else {
                                // 取首字母
                                pinyinName.append(pingyin.charAt(0));
                            }

                        } else {// 不是多音字
                            pinyinName.append(strs[0].charAt(0));
                        }
                        /*
                         * for (int j = 0; j < strs.length; j++) { // 取首字母
						 * pinyinName.append(strs[j].charAt(0)); if (j !=
						 * strs.length - 1) { pinyinName.append(","); } }
						 */
                    }// inner end if
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {// 英文
                pinyinName.append(nameChar[i]);
            }
            // pinyinName.append(" ");
        }// end for
        return pinyinName.toString();
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 字符串集合转换字符串(逗号分隔)
     *
     * @param stringSet
     * @return
     * @author wyh
     */
    public static String makeStringByStringSet(Set<String> stringSet) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (String s : stringSet) {
            if (i == stringSet.size() - 1) {
                str.append(s);
            } else {
                str.append(s + ",");
            }
            i++;
        }
        return str.toString().toLowerCase();
    }

    public static String getPinyinString(String src) {
        Set<String> pinyins = getPinyin(src);
        if (pinyins != null && pinyins.isEmpty() == false) {
            return ConvertUtils.toString(pinyins.toArray()[0]);
        }
        return new String();
    }

    /**
     * 获取拼音集合
     *
     * @param src
     * @return Set<String>
     * @author wyh
     */
    public static Set<String> getPinyin(String src) {
        if (src != null && !src.trim().equalsIgnoreCase("")) {
            char[] srcChar;
            srcChar = src.toCharArray();
            // 汉语拼音格式输出类
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

            // 输出设置，大小写，音标方式等
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

            String[][] temp = new String[src.length()][];
            for (int i = 0; i < srcChar.length; i++) {
                char c = srcChar[i];
                // 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    try {
                        temp[i] = PinyinHelper.toHanyuPinyinStringArray(
                                srcChar[i], hanYuPinOutputFormat);
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else if (((int) c >= 65 && (int) c <= 90)
                        || ((int) c >= 97 && (int) c <= 122)) {
                    temp[i] = new String[]{String.valueOf(srcChar[i])};
                } else {
                    temp[i] = new String[]{""};
                }
            }
            String[] pingyinArray = Exchange(temp);
            Set<String> pinyinSet = new HashSet<String>();
            pinyinSet.add(pingyinArray[0]);
            return pinyinSet;
        }
        return null;
    }

    public static String replaceString(String chines) {
        return chines.replace("《", "").replace("》", "").replace("！", "")
                .replace("￥", "").replace("【", "").replace("】", "").replace(
                        "（", "").replace("）", "").replace("－", "").replace("；",
                        "").replace("：", "").replace("”", "").replace("“", "")
                .replace("。", "").replace("，", "").replace("、", "").replace(
                        "？", "").replace(" ", "").replace("-", "");
    }

    public static String getPinyinBysrc(String src) {

        src = replaceString(src);

        String chines = src.substring(findFirstChineseChar(src));

        String englishs = src.substring(0, findFirstChineseChar(src));


        if (chines != null && !chines.trim().equalsIgnoreCase("")) {
            char[] srcChar;
            srcChar = chines.toCharArray();
            // 汉语拼音格式输出类
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

            // 输出设置，大小写，音标方式等
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

            String[][] temp = new String[chines.length()][];
            for (int i = 0; i < srcChar.length; i++) {
                char c = srcChar[i];
                // 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    try {
                        temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else if (((int) c >= 65 && (int) c <= 90)
                        || ((int) c >= 97 && (int) c <= 122)) {
                    temp[i] = new String[]{String.valueOf(srcChar[i])};
                } else {
                    temp[i] = new String[]{""};
                }
            }
            String[] pingyinArray = Exchange(temp);
            String s = null;
            Set<String> pinyinSet = new HashSet<String>();
//            for (int i = 0; i < pingyinArray.length; i++) {
//                pinyinSet.add(pingyinArray[i]);
//            }
            if (pinyinSet != null && pinyinSet.size() > 0) {
                pinyinSet.add(pingyinArray[0]);
            }
            s = pinyinSet.toString();
            s = s.substring(1, s.length() - 1);
            System.out.println(s);

            return englishs.toUpperCase() + s.trim();
        }
        return null;
    }

    public static int findFirstChineseChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x4e00 && c <= 0x9fa5) {
                return i;
            }
        }
        return 1;
    }

    /**
     * 递归
     *
     * @param strJaggedArray
     * @return
     * @author wyh
     */
    public static String[] Exchange(String[][] strJaggedArray) {
        String[][] temp = DoExchange(strJaggedArray);
        return temp[0];
    }

    /**
     * 递归
     *
     * @param strJaggedArray
     * @return
     * @author wyh
     */
    private static String[][] DoExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len >= 2) {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newlen = len1 * len2;
            String[] temp = new String[newlen];
            int Index = 0;
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
                    Index++;
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = strJaggedArray[i];
            }
            newArray[0] = temp;
            return DoExchange(newArray);
        } else {
            return strJaggedArray;
        }
    }

    public static void main(String args[]) {
        // String s="百乐～电脑";
        String ss = "张家俊";// 单/解放/查/幺/繁/曾
        System.out.println("值为：" + getPinyin(ss));
    }

}

