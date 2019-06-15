package model.utils;

public class StringUtil {
    public static String clearSpace(String str) {
        return str.replaceAll(" ", "");
    }

    public static String[] clearSpace(String... str) {
        String[] temps = new String[str.length];
        for (int i = 0; i < str.length; ++i) {
            temps[i] = str[i].replaceAll(" ", "");
        }
        return temps;
    }

    /**
     * 　　* 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     * 　　* @param sourceDate
     * 　　* @param formatLength
     * 　　* @return 重组后的数据
     */
    public static String cloneRepStrWithZore(int sourceDate, int formatLength) {
        /*
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String tmp = RandomUtils.randomNumeric(3);
            System.out.println(cloneRepStrWithZore(Integer.parseInt(tmp),6));
        }
    }
}