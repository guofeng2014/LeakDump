package com.leaklibrary.util;

import android.text.TextUtils;

import com.leaklibrary.adapter.LeakAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class FileUtil {

    private static final Object lock = new Object();

    /**
     * 保存格式 泄露Obj对象#$#时间
     *
     * @param filePath
     * @param content
     */
    public static void writeFile(String filePath, String content, long timeMillion) {
        if (TextUtils.isEmpty(content)) return;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            synchronized (lock) {
                fileWriter = new FileWriter(file, true);
                fileWriter.write(generateLine(content, DateUtil.dateFormatter(timeMillion)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static List<LeakAdapter.LeakBean> readFileToList(String filePath) {
        List<LeakAdapter.LeakBean> strings = new ArrayList<>();

        BufferedReader reader = null;
        File file = new File(filePath);
        try {
            synchronized (lock) {
                reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    LeakAdapter.LeakBean leakBean = generateLeakBean(line);
                    if (leakBean != null) {
                        strings.add(leakBean);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return strings;
    }


    private static String generateLine(String content, String date) {
        return content + "#" + date;
    }

    private static LeakAdapter.LeakBean generateLeakBean(String line) {
        if (!TextUtils.isEmpty(line)) {
            String lines[] = line.split("#");
            if (lines.length >= 2) {
                return new LeakAdapter.LeakBean(lines[0], lines[1]);
            }
        }
        return null;
    }


    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

    }

}
