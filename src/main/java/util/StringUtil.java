package util;

public class StringUtil {

    public static String[] split(String value, String regex) {
        value = value.replaceAll("\u200B", "");
        return value.split(regex);
    }
}
