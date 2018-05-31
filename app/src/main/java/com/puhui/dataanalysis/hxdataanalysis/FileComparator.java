package com.puhui.dataanalysis.hxdataanalysis;

import java.io.File;
import java.util.Comparator;

/**
 * Created by chenb on 2018/5/25.
 */

public class FileComparator implements Comparator<File>{
    private StorageCallback storageCallback;

    public FileComparator(StorageCallback storageCallback) {
        this.storageCallback = storageCallback;
    }

    @Override
    public int compare(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
