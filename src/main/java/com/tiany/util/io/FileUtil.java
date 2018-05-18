package com.tiany.util.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
	/**
	 * 构造器私有化
	 */
	private FileUtil() {
	}

	/**
	 * 以文本方式读文件(文件较小的情况下)
	 * 
	 * @param filePath
	 *            文件的路径如:"C:\\file_test.txt" 或者
	 *            "src\\my\\util\\test\\file_test.txt" 或者
	 *            "src/my/util/test/file_test.txt" 或者
	 *            "src\\my\\util/test/file_test.txt"
	 * @return 返回读到的字符串
	 */
	public static String read(String filePath) throws IOException {
		File file = new File(filePath);
		return read(file);
	}
	/**
	 * 读取文件的内容以字符串的形式返回
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static String read(File file) throws IOException, FileNotFoundException {
		if (!file.exists()) {// 如果文件不存
			throw new IOException("指定的文件不存在...");
		}
		long length = file.length();
		if ((int) length != length) {
			throw new IOException("指定的文件太大...");
		}
		char[] charArray = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			charArray = new char[(int) length];// 一次性读完...
			br.read(charArray, 0, charArray.length);
		} finally {
			if (br != null)
				br.close();// 不关闭有警告...
		}
		return String.valueOf(charArray);
	}

	/**
	 * 以文本方式写文件(文件较小的情况下)
	 * 
	 * @param filePath
	 *            文件的路径如:"C:\\file_test.txt" 或者
	 *            "src\\my\\util\\test\\file_test.txt" 或者
	 *            "src/my/util/test/file_test.txt" 或者
	 *            "src\\my\\util/test/file_test.txt"
	 * @param content
	 *            需要写入文件的的字符串
	 */
	public static void write(String filePath, String content) throws IOException {
		File file = new File(filePath);
		write(file, content);
	}
	/**
	 * 以字符串的形式写文件
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public static void write(File file, String content) throws IOException {
		String path = file.getParent();// 得到文件的上层文件目录
		if (path != null) {// 如果没有改文件目录则创建该目录
			File dir = new File(path);
			dir.mkdirs();// 注意与mkdir()的区别
		}
		if (!file.exists()) {// 如果文件不存在这创建新的
			file.createNewFile();
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			if (file.canWrite()) {
				bw.write(content);
				bw.flush();
			}
		} finally {
			if (bw != null)
				bw.close();
		}
	}

	/**
	 * 返回指定目录下所有的文件
	 * 
	 * @param directory
	 *            要查询的目录
	 * @param list
	 *            list需要在调用前初始化
	 */
	public static void listAllFiles(File directory, List<File> list) {
		File[] listFiles = directory.listFiles();
		for (File f : listFiles) {
			if (f.isDirectory()) {
				listAllFiles(f, list);
			} else {
				list.add(f);
			}
		}
	}

	/**
	 * 返回指定目录下所有的文件
	 * 
	 * @param directory
	 *            要查询的目录
	 */
	public static List<File> listAllFiles(File directory) {
		if (!directory.exists()) {
			throw new IllegalArgumentException(directory + " does not exist");
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory + " is not a directory");
		}
		File[] listFiles = directory.listFiles();
		List<File> list = new ArrayList<File>();
		for (File f : listFiles) {
			if (f.isDirectory()) {
				List<File> sublist = listAllFiles(f);
				if (sublist != null)
					list.addAll(sublist);
			} else {
				list.add(f);
			}
		}
		return list;
	}

	/**
	 * 获取文件的大小或者目录的大小
	 * 
	 * @param file
	 * @return 返回文件的字节数或目录的字节数
	 */
	public static long sizeOf(File file) {
		if (!file.exists()) {
			throw new IllegalArgumentException(file + "does not exist");
		}
		if (file.isDirectory()) {
			long size = 0;
			File[] files = file.listFiles();
			if (files == null) { // null if security restricted
				return 0L;
			}
			for (File f : files) {
				size += sizeOf(f);
			}
			return size;
		} else {
			return file.length();
		}
	}

	/**
	 * 获取系统的临时目录路径
	 * 
	 * @return 获取系统的临时目录路径
	 */
	public static String getTempDirectoryPath() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 获取系统的临时目录路径对应的File
	 * 
	 * @return
	 */
	public static File getTempDirectory() {

		return new File(getTempDirectoryPath());

	}

	/**
	 * 获取用户的主目录路径
	 * 
	 * @return
	 */
	public static String getUserDirectoryPath() {

		return System.getProperty("user.home");

	}

	/**
	 * 获取用户的主目录路径
	 * 
	 * @return
	 */
	public static File getUserDirectory() {
		return new File(getUserDirectoryPath());

	}

	/**
	 * 根据指定的文件获取一个新的文件输入流
	 * 
	 * @param file
	 * @return
	 */
	public static FileInputStream openInputStream(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IllegalArgumentException("File '" + file + "' exists but is adirectory");
			}
			if (file.canRead() == false) {
				throw new IllegalArgumentException("File '" + file + "' cannot be read");
			}
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new IllegalArgumentException("File '" + file + "' does notexist");
		}
	}

	/**
	 * 根据指定的文件获取一个新的文件输出流
	 * 
	 * @param file
	 * @return
	 */
	public static FileOutputStream openOutputStream(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IllegalArgumentException("File'" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IllegalArgumentException("File '" + file + "' cannot be written to");
			}
			try {
				return new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null && parent.exists() == false) {
				if (parent.mkdirs() == false) {
					throw new IllegalArgumentException("File '" + file + "' could not be created");
				}
			}
		}
		return null;
	}

	/**
	 * 查找一个目录下面符合对应扩展名的文件的集合
	 * 用法：List<File> list = FileUtils.listFiles(new
	 * File(str),"md","edf","java");
	 * 
	 * @param directory
	 * @param extensions
	 * @return
	 */
	public static List<File> listFiles(File directory, final String... extensions) {
		if (!directory.exists()) {
			throw new IllegalArgumentException(directory + "does not exist");
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory + "is not a directory");
		}
		List<File> list = new ArrayList<File>();
		File[] listFiles = null;
		if (extensions.length == 0) {
			listFiles = directory.listFiles();
		} else {
			listFiles = directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					String name = pathname.getName();
					for (String s : extensions) {
						if (name.endsWith("." + s))
							return true;
					}
					return false;
				}
			});
		}
		for (File f : listFiles) {
			list.add(f);
		}
		return list;

	}

	/**
	 * 判断两个文件是否相等
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 */
	public static boolean contentEquals(File file1, File file2) {
		if (!file1.exists() || !file2.exists()) {
			// file is not exist
			throw new IllegalArgumentException("file is not exist");
		}
		if (file1.isDirectory() || file2.isDirectory()) {
			// don't want to compare directory contents
			throw new IllegalArgumentException("Can't compare directories, only files");
		}
		if (file1.length() != file2.length()) {
			// lengths differ, cannot be equal
			return false;
		}
		InputStream input1 = null;
		InputStream input2 = null;
		try {
			if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
				// same file
				return true;
			}

			input1 = new FileInputStream(file1);
			input2 = new FileInputStream(file2);
			return contentEquals(input1, input2);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (input1 != null) {
				try {
					input1.close();
				} catch (IOException e1) {
					// quiet
				}
			}
			if (input2 != null) {
				try {
					input2.close();
				} catch (IOException e1) {
					// quiet
				}
			}
		}
	}

	/**
	 * 判断两个InputStream是否相等
	 * 
	 * @param is1
	 * @param is2
	 * @return
	 */
	public static boolean contentEquals(InputStream is1, InputStream is2) {
		BufferedInputStream bis1 = new BufferedInputStream(is1);
		BufferedInputStream bis2 = new BufferedInputStream(is2);
		byte[] b1 = new byte[1024];
		byte[] b2 = new byte[1024];
		try {
			while (bis1.read(b1) != -1) {
				bis2.read(b2);
				if (!Arrays.equals(b1, b2))
					return false;
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (bis1 != null) {
				try {
					bis1.close();
				} catch (IOException e) {
					// quiet
				}

			}
			if (bis2 != null) {
				try {
					bis2.close();
				} catch (IOException e) {
					// quiet
				}
			}
		}
		return true;
	}

	public static boolean copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate) throws IOException {
		if (destDir == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (destDir.exists() && destDir.isDirectory() == false) {
			throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
		}
		File destFile = new File(destDir, srcFile.getName());
		return copyFile(srcFile, destFile, preserveFileDate);
	}

	/**
	 * 拷贝文件到新的文件中并且设置是否保存最近修改时间
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param preserveFileDate
	 */
	public static boolean copyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
		if (srcFile == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destFile == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (srcFile.exists() == false) {
			throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
		}
		if (srcFile.isDirectory()) {
			throw new IOException("Source '" + srcFile + "' exists but is a directory");
		}
		if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
			throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
		}
		if (destFile.getParentFile() != null && destFile.getParentFile().exists() == false) {
			if (destFile.getParentFile().mkdirs() == false) {
				throw new IOException("Destination '" + destFile + "' directory cannot becreated");
			}
		}
		if (destFile.exists() && destFile.canWrite() == false) {
			throw new IOException("Destination '" + destFile + "' exists but is read-only");
		}
		return doCopyFile(srcFile, destFile, preserveFileDate);
	}

	/**
	 * 拷贝文件到新的文件中并且设置是否保存最近修改时间
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param preserveFileDate
	 */
	private static boolean doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
		if (destFile.exists() && destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile + "' exists but is a directory");
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel input = null;
		FileChannel output = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			input = fis.getChannel();
			output = fos.getChannel();
			long size = input.size();
			long pos = 0;
			long count = 0;
			final long FIFTY_MB = 1024;
			while (pos < size) {
				count = (size - pos) > FIFTY_MB ? FIFTY_MB : (size - pos);
				pos += output.transferFrom(input, pos, count);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (output != null) {
				output.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (input != null) {
				input.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		if (srcFile.length() != destFile.length()) {
			throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
		}
		if (preserveFileDate) {
			destFile.setLastModified(srcFile.lastModified());
		}
		return true;

	}

}
