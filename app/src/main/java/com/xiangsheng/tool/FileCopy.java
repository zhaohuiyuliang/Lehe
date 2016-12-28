package com.xiangsheng.tool;

import com.xiangsheng.ControlApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wangliang on 2016/12/28.
 */

public class FileCopy {

    /**
     * assets目录下的文件copy到目的地目录
     *
     * @param filePath
     * @param filePath2
     */
    public static void fileCopyFromAssetsTo(String filePath, String filePath2) {
        try {
            InputStream inputStream = ControlApplication.getApplication().getClass().getResourceAsStream("/assets/" + filePath);
            OutputStream outputStream = new FileOutputStream(new File(filePath2));
            byte[] bytes = new byte[1024];
            int line;
            while ((line = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, line);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
