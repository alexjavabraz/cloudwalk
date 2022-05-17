package br.com.bjbraz.cloudwalk.util;

import java.util.StringTokenizer;

public class FileProcessorUtil {
    public static final String BEGIN = "InitGame:";
    public static final String END = "ShutdownGame:";
    public static final CharSequence KILL = "Kill:";
    public static final String WORLD = "<world>";

    public static String getTokenAtPoint(int point, String line){
        StringTokenizer tokenizer = new StringTokenizer(line);
        for(int i = 0; i < point; i++) {
            tokenizer.nextToken(" ");
        }
        return tokenizer.nextToken(" ");
    }
}
