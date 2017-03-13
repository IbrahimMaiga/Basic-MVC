package ml.kanfa.parser;


import ml.kanfa.execption.InvalidFormatException;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ibrahim Maïga.
 */
public class YMLParser {

    private static final String SPACES = "    ";
    private static final String TAB = "  ";
    private static final String DCF = "dependency-config-file";
    private final String CLASS = "class";
    private static final String DEFAULT_PATH = "/ml/kanfa/config/connection/default.yml";

    private Reader reader;
    private BufferedReader buf;

    public YMLParser(Reader reader){
        this.reader = reader;
        this.buf = new BufferedReader(this.reader);
    }

    public YMLParser() throws FileNotFoundException, URISyntaxException {
        this(new InputStreamReader(YMLParser.class.getResourceAsStream(DEFAULT_PATH)));
    }

    private ArrayList<ArrayList<String>> load(ArrayList<String> array){

        ArrayList<ArrayList<String>> arrays = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        boolean first = true;
        for (String s : array) {
            if (first && !s.startsWith("default")){
                try {
                    throw new InvalidFormatException("Invalid yml format");
                } catch (InvalidFormatException e) {e.printStackTrace();}
            }
            else{
                if (first) first = !first;
                if (s.startsWith(SPACES + "name") || s.startsWith(TAB + "name")){
                    if (!list.isEmpty()){
                        arrays.add(list);
                        list = new ArrayList<>();
                    }
                    list.add(s.trim());
                }
                else{
                    if (is_correct_child(s)){
                        list.add(s.trim());
                    }
                }
            }
        }
        arrays.add(list);
        return arrays;
    }

    private boolean is_correct_child(String child){
        Pattern pattern = Pattern.compile(SPACES + "|\\n|" + TAB);
        Matcher matcher = pattern.matcher(child);
        return matcher.find();
    }

    private Map<String, Map<String, String>> parse(){

        Map<String, Map<String, String>> map = new HashMap<>();
        ArrayList<String> array = new ArrayList<>();
        this.buf.lines().forEach(array::add);
        ArrayList<ArrayList<String>> arrays = load(array);

        for (ArrayList<String> ar : arrays){

            String key = "";
            Map<String, String> strMap = new HashMap<>();
            boolean first = true;

            for (String s : ar){
                String[] strTab = s.split(":");
                if (first){
                    key = strTab[1].trim();
                    first = !first;
                }
                else{
                    strMap.put(strTab[0].trim(), strTab[1].trim());
                }
            }

            map.put(key, strMap);
        }

        return map;
    }

    private Map<String, String> parse(String value){
        return this.parse().get(value);
    }

    public String get(String value) {
        return this.parse(value).get(CLASS);
    }

    public String getDependencyPath(String value){
        String str = this.parse(value).get(DCF);
        str = str.replace("@", "/");
        return str;
    }
}