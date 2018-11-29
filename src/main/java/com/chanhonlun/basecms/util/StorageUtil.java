package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.storage.FileSystemStorage;
import com.chanhonlun.basecms.storage.Storage;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${com.chanhonlun.path.upload}")
    private String uploadPath;

    @Autowired
    private FileSystemStorage fileSystemStorage;

    @PostConstruct
    public void init() {
        this.storage = fileSystemStorage;
    }

    public boolean saveObject(InputStream inputStream, String destinationFolder, String fileName) {
        return storage.saveObject(inputStream, uploadPath + destinationFolder, fileName);
    }

    public boolean saveObject(String filePath, String destinationFolder, String fileName) {
        try {
            return saveObject(FileUtils.openInputStream(new File(filePath)), destinationFolder, fileName);
        } catch (IOException e) {
            logger.error("fail getting input stream, e: {}", e);
            return false;
        }
    }

    public boolean saveObject(MultipartFile file, String destinationFolder, String fileName) {
        try {
            return saveObject(file.getInputStream(), destinationFolder, fileName);
        } catch (IOException e) {
            logger.error("fail getting input stream, e: {}", e);
            return false;
        }
    }

    public InputStream getObject(String destinationFolder, String fileName) {
        return storage.getObject(uploadPath + destinationFolder, fileName);
    }
}
