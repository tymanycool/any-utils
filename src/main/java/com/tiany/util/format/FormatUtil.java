package com.tiany.util.format;

public abstract class FormatUtil {
    /**
     * 增加缩进
     * @param str
     * @param tabNum
     * @return
     */
    public static String addTab(String str,int tabNum){
        str = str.replaceAll("\r\n","\n");
        String ret = "";
        String addTabStr ="";
        for(int i=0;i<tabNum;i++){
            addTabStr += "\t";
        }
        String[] split = str.split("[\n]");
        for(String s:split){
            ret += addTabStr + s + "\r\n";
        }
        return ret;
    }
}
