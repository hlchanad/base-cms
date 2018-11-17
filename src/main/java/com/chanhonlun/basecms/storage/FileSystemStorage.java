package com.chanhonlun.basecms.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemStorage implements Storage {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorage.class);

    @Override
    public boolean saveObject(String filePath, String destinationPath, String fileName) {

        try {
            return saveObject(FileUtils.openInputStream(new File(filePath)), destinationPath, fileName);
        } catch (IOException e) {
            logger.error("fail writing file, e: {}", e);
            return false;
        }
    }

    @Override
    public boolean saveObject(InputStream inputStream, String destinationPath, String fileName) {

        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Path path = Paths.get(destinationPath + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            logger.error("fail writing file, e: {}", e);
            return false;
        }

        return true;
    }
}
