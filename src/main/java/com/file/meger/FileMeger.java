package com.file.meger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.file.util.FileUtil;
import com.file.util.FileUtil.FileComparator;

public class FileMeger {
	
	private static FileUtil fileUtil = new FileUtil();
	/**
	 * 合并文件
	 * 
	 * @param dirPath
	 *            拆分文件所在目录名
	 * @param partFileSuffix
	 *            拆分文件后缀名
	 * @param partFileSize
	 *            拆分文件的字节数大小
	 * @param mergeFileName
	 *            合并后的文件名
	 * @throws IOException
	 * @throws Exception 
	 */
	public void mergePartFiles(String dirPath, String partFileSuffix, int partFileSize, String mergeFileName)
			throws IOException, Exception {
		ArrayList<File> partFiles = fileUtil.getDirFiles(dirPath, partFileSuffix);
		Collections.sort(partFiles, new FileComparator());

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(partFiles.size(), partFiles.size() * 3, 1,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(partFiles.size() * 2));

		for (int i = 0; i < partFiles.size(); i++) {
			threadPool.execute(new MergeRunnable(i * partFileSize, mergeFileName, partFiles.get(i)));
		}

	}
}
