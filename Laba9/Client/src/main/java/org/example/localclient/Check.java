package org.example.localclient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
    private static final Pattern pattern = Pattern.compile("^\\s*$");
    //true если строка не состоит из пробелов
    public static boolean isStringNotEmpty(String s){
        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()){
            return true;
        } else {
            return false;
        }
    }
}