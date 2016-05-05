package com.file.spilt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class SplitRunnable  implements Runnable {
	int byteSize;
	String partFileName;
	File originFile;
	int startPos;

	public SplitRunnable(int byteSize, int startPos, String partFileName, File originFile) {
		this.startPos = startPos;
		this.byteSize = byteSize;
		this.partFileName = partFileName;
		this.originFile = originFile;
	}

	public void run() {
		RandomAccessFile rFile;
		OutputStream os;
		try {
			rFile = new RandomAccessFile(originFile, "r");
			byte[] b = new byte[byteSize];
			rFile.seek(startPos);// 移动指针到每“段”开头
			int s = rFile.read(b);
			os = new FileOutputStream(partFileName);
			os.write(b, 0, s);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
