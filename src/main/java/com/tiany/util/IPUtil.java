package com.tiany.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {
    /**
     * 得到本机计算机的名称
     * @return
     */
    public static String getHostName(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostName=addr.getHostName().toString(); //获取本机计算机名称
            return hostName;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
    /**
     * 得到本机计算机的名称
     * @return
     */
    public static String getHostIP(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip=addr.getHostAddress().toString(); //获取本机ip
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
