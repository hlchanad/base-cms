package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.storage.FileSystemStorage;
import com.chanhonlun.basecms.storage.Storage;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class StorageUtil {

    private static final Logger logger = LoggerFactory.getLogger(StorageUtil.class);

    private Storage storage;

    @Autowired
    private FileSystemStorage fileSystemStorage;

    @PostConstruct
    public void init() {
        this.storage = fileSystemStorage;
    }

    public boolean saveObject(InputStream inputStream, String destinationPath, String fileName) {
        return storage.saveObject(inputStream, destinationPath, fileName);
    }

    public boolean saveObject(String filePath, String destinationPath, String fileName) {
        try {
            return saveObject(FileUtils.openInputStream(new File(filePath)), destinationPath, fileName);
        } catch (IOException e) {
            logger.error("fail getting input stream, e: {}", e);
            return false;
        }
    }

    public boolean saveObject(MultipartFile file, String destinationPath, String fileName) {
        try {
            return saveObject(file.getInputStream(), destinationPath, fileName);
        } catch (IOException e) {
            logger.error("fail getting input stream, e: {}", e);
            return false;
        }
    }

}
