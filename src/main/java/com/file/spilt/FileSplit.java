package com.file.spilt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.file.util.FileUtil;

public class FileSplit {
	/**
	 * 拆分文件
	 * 
	 * @param fileName
	 *            待拆分的完整文件名
	 * @param byteSize
	 *            按多少字节大小拆分
	 * @return 拆分后的文件名列表
	 * @throws IOException
	 */
	public List<String> splitBySize(String fileName, int byteSize) throws IOException {
		List<String> parts = new ArrayList<String>();
		File file = new File(fileName);
		int count = (int) Math.ceil(file.length() / (double) byteSize);
		int countLen = (count + "").length();
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count, count * 3, 1, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(count * 2));

		for (int i = 0; i < count; i++) {
			String partFileName = file.getName() + "." + FileUtil.leftPad((i + 1) + "", countLen, '0') + ".part";
			threadPool.execute(new SplitRunnable(byteSize, i * byteSize, partFileName, file));
			parts.add(partFileName);
		}
		return parts;
	}
}
