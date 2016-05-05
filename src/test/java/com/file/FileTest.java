package com.file;

import org.junit.Test;

import com.file.handler.FileHandler;
import com.file.spilt.FileSplit;
import com.file.util.FileUtil;

public class FileTest {

    @Test
    public void writeFile() throws Exception {

        System.out.println(FileUtil.currentWorkDir);

        StringBuilder sb = new StringBuilder();

        long originFileSize = 1024 * 1024 * 100;// 100M
        int blockFileSize = 1024 * 1024 * 15;// 15M

        // 生成一个大文件
        for (int i = 0; i < originFileSize; i++) {
            sb.append("A");
        }

        String fileName = FileUtil.currentWorkDir + "origin.myfile";
        System.out.println(fileName);
        FileUtil.write(fileName, sb.toString());

        // 追加内容
        sb.setLength(0);
        sb.append("0123456789");
        FileUtil.append(fileName, sb.toString());

        FileSplit fileSplit = new FileSplit();
        FileHandler handler = new FileHandler(fileSplit, fileName, blockFileSize);
        handler.mergePartFiles(FileUtil.currentWorkDir, ".part",
                blockFileSize, FileUtil.currentWorkDir + "new.myfile");
    }
}