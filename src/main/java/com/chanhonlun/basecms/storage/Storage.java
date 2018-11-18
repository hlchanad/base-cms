package com.chanhonlun.basecms.storage;

import java.io.InputStream;

public interface Storage {

    boolean saveObject(InputStream inputStream, String destinationFolder, String fileName);
}
