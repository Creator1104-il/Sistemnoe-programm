package org.example.localserver;
public class Spliter {
    static AuthRequest splitNameAndPass(String s){
        int i = s.indexOf("$@?#");
        return new AuthRequest(s.substring(0, i), s.substring(i+4));
    }
}
