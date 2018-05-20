package com.tiany.util.zip;

import java.io.File;

public class Execute {

    public void extractFiles(String strPath) {
        ZipUtil fileOperateUtil = new ZipUtil();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();

                if (!files[i].isDirectory() && (fileName.endsWith(".jar") || fileName.endsWith(".zip"))) { // 判断文件名是否以.jar结尾
                    String sourceFilePath = files[i].getAbsolutePath();
                    System.out.println("extract file: " + sourceFilePath);
                    fileOperateUtil.WriteStringToFile(fileOperateUtil.getLogPath(), "extract file: " + sourceFilePath);
                    String dirName = fileName.substring(0, fileName.length() - 4);
                    String resultPath = sourceFilePath.replace(fileName, dirName);

                    fileOperateUtil.unZipFiles(sourceFilePath, resultPath, true);
                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        ZipUtil fileOperateUtil = new ZipUtil();
        String rootPath = args[0];

        if ("".equals(rootPath) || rootPath == null) {
            System.out.println("please input extract path:");
            fileOperateUtil.WriteStringToFile(fileOperateUtil.getLogPath(), "please input extract path:");
        }

//        String rootPath = "D:\\SVNhome\\test";
//        fileOperateUtil.delAllFile(rootPath);

        if (rootPath.endsWith("\\")) {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
        fileOperateUtil.setLogPath(rootPath + "\\extractLog.txt");

        long startTime = System.currentTimeMillis();
        long endTime = 0;
        System.out.println("extract path: " + rootPath);
        fileOperateUtil.WriteStringToFile(fileOperateUtil.getLogPath(), "extract path: " + rootPath);

        Execute execute = new Execute();

        File dir = new File(rootPath);
        File[] files = dir.listFiles();
        if (files != null) {
            int filesNum = files.length;
            for (int i = 0; i < filesNum; i++) {
                String fileName = files[i].getName();
                if (!fileName.endsWith(".svn") && !fileName.endsWith("extractLog.txt") && !fileName.endsWith("target")) {
                    fileOperateUtil.WriteStringToFile(fileOperateUtil.getLogPath(), "copy path: " + rootPath + "\\" + fileName);
                    if (files[i].isDirectory()) {
                        fileOperateUtil.copyFolder(rootPath + "\\" + fileName, rootPath + "\\target\\" + fileName);
                        execute.extractFiles(rootPath + "\\target\\" + fileName);
                    } else {
                        fileOperateUtil.copyFile(rootPath + "\\" + fileName, rootPath + "\\target\\" + fileName);
                    }
                }
            }
        }

        endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) / 1000 + "s");    //输出程序运行时间
        fileOperateUtil.WriteStringToFile(fileOperateUtil.getLogPath(), "程序运行时间：" + (endTime - startTime) / 1000 + "s");
    }
}