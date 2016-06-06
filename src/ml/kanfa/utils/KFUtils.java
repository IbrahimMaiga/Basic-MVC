package ml.kanfa.utils;

/**
 * @author Kanfa.
 */
public class KFUtils {

    private KFUtils(){}

    public static String ucfirst(String str){
        if (str == null) throw new NullPointerException("Paramètre nul");
        else if (str.isEmpty()) return "";
        else return Character.toString(str.charAt(0)).toUpperCase().concat(str.substring(1));
    }

    public static String capitalize(String str){
       return format(str, ' ');
    }

    public static String _camlCase(String str){
        return format(str, '_');
    }

    private static String format(String str, char delimiter){
        String s = String.valueOf(delimiter);
        if (str.split(s).length == 1) return ucfirst(str);
        else{
            String current = (trim(str, delimiter)).substring(0, str.indexOf(s));
            str = trim(str.substring(str.indexOf(s)), delimiter);
            return ucfirst(current) + (delimiter == '_' ? "" : s) + format(str, delimiter);
        }
    }

    private static String trim(String str, char delimiter) {
        int len = str.length();
        int st = 0;
        char[] val = str.toCharArray();

        while ((st < len) && (val[st] <= delimiter)) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= delimiter)) {
            len--;
        }
        return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
    }

    private static String trim(String str){
        return trim(str, ' ');
    }

}
