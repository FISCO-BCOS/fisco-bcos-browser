package org.bcos.browser.util;

import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Configuration
public class FileUtils {

    private File createFilePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public File createFile(String filePath, String fileName, String inputStr) {
        File file = new File(createFilePath(filePath), fileName);
        if (!file.exists()) {
            try {
                boolean isCreated = file.createNewFile();
                if (isCreated) {
                    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file.getPath()), StandardCharsets.UTF_8);
                    osw.write(inputStr);
                    osw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }
}
