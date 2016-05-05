package com.file.handler;

import com.file.meger.FileMeger;
import com.file.spilt.FileSplit;

public class FileHandler extends FileMeger {
	
	private FileSplit fileSplit;
	private String fileName;
	private int byteSize;
	
	public FileHandler(FileSplit fileSplit, String fileName, int byteSize) {
		this.fileSplit = new FileSplit();
		this.fileName = fileName;
		this.byteSize = byteSize;
	}
	
	@Override
	public void mergePartFiles(String dirPath, String partFileSuffix, int partFileSize, String mergeFileName)
			throws Exception {
		fileSplit.splitBySize(fileName, byteSize);
		Thread.sleep(10000);// 稍等10秒，等前面的小文件全都写完
		super.mergePartFiles(dirPath, partFileSuffix, partFileSize, mergeFileName);
	}
	
}
