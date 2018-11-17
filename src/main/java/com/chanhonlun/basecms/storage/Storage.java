package com.chanhonlun.basecms.storage;

import java.io.InputStream;

public interface Storage {

    boolean saveObject(String filePath, String destinationFolder, String fileName);

    boolean saveObject(InputStream inputStream, String destinationFolder, String fileName);
}
