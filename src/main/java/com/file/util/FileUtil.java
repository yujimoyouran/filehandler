package com.file.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class FileUtil {

	/**
	 * 当前目录路径
	 */
	public static String currentWorkDir = System.getProperty("user.dir") + "\\";

	/**
	 * 左填充
	 * 
	 * @param str
	 * @param length
	 * @param ch
	 * @return
	 */
	public static String leftPad(String str, int length, char ch) {
		if (str.length() >= length) {
			return str;
		}
		char[] chs = new char[length];
		Arrays.fill(chs, ch);
		char[] src = str.toCharArray();
		System.arraycopy(src, 0, chs, length - src.length, src.length);
		return new String(chs);

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            待删除的完整文件名
	 * @return
	 * @throws IOException 
	 */
	public static void delete(String fileName) throws IOException {
		FileUtils.forceDeleteOnExit(new File(fileName));
	}

	/**
	 * 递归获取指定目录下的所有的文件(不包括文件夹)
	 * 
	 * @param file
	 * @return
	 */
	public static List<File> getAllFiles(File file){
		return getAllFiles(file, false);
	}

	/**
	 * 递归获取指定目录下的所有的文件
	 * 
	 * @param file 指定文件目录
	 * @param isDir 是否包含文件夹
	 * @return
	 */
	public static List<File> getAllFiles(File file, boolean isDir){
		List<File> list = new ArrayList<File>();
		if(file.isDirectory()){
			if(isDir)
				list.add(file);
			File[] files = file.listFiles();
			for(File f : files)
				getAllFiles(f);
		}
		else
			list.add(file);
		return list;
	} 

	/**
	 * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
	 * 
	 * @param dirPath
	 *            目录路径
	 * @param suffix
	 *            文件后缀
	 * @return
	 */
	public ArrayList<File> getDirFiles(String dirPath, final String suffix) {
		File[] fileArr = fileFilterBySuffix(new File(dirPath), suffix);
		
		ArrayList<File> files = new ArrayList<File>();

		for (File f : fileArr) {
			if (f.isFile()) {
				files.add(f);
			}
		}
		return files;
	}

	/**
	 * 根据文件后缀过滤文件
	 * 
	 * @param file 文件
	 * @param suffix 后缀
	 * @return
	 */
	public File[] fileFilterBySuffix(File file, final String suffix) {
		return fileFilter(file, suffix, "");
	}
	
	/**
	 * 根据文件前缀过滤文件
	 * 
	 * @param file 文件
	 * @param prefix 前缀
	 * @return
	 */
	public File[] fileFileterByPrefix(File file, final String prefix){
		return fileFilter(file, "", prefix);
	}

	private File[] fileFilter(File file, final String suffix, final String prefix) {
		return file.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (StringUtils.isNoneBlank(suffix) && name.endsWith(suffix))
					return true;
				else if(StringUtils.isNoneBlank(prefix) && name.startsWith(prefix))
					return true;
				return false;
			}
		});
	}

	/**
	 * 读取文件内容
	 * 
	 * @param fileName
	 *            待读取的完整文件名
	 * @return 文件内容
	 * @throws IOException
	 */
	public static String read(String fileName) throws IOException {
		return FileUtils.readFileToString(new File(fileName));
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 *            目标文件名
	 * @param fileContent
	 *            写入的内容
	 * @return
	 * @return
	 * @throws IOException
	 */
	public static void write(String fileName, String fileContent) throws FileNotFoundException, IOException {
		IOUtils.write(fileContent, new FileOutputStream(fileName));
	}

	/**
	 * 追加内容到指定文件
	 * 
	 * @param fileName
	 * @param fileContent
	 * @return
	 * @throws IOException
	 */
	public static void append(String fileName, String fileContent) throws IOException {
		FileUtils.writeStringToFile(new File(fileName), fileContent, true);
	}



	/**
	 * 根据文件名，比较文件
	 * 
	 * @author yjmyzz@126.com
	 *
	 */
	public static class FileComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}


}
