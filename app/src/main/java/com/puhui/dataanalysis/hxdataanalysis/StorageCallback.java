package com.puhui.dataanalysis.hxdataanalysis;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.CRC32;

/**
 * Created by chenb on 2018/5/25.
 */

public class StorageCallback {
    private static final String b = "OperationManager";
    Lock lock = new ReentrantLock();
    private static StorageCallback storageCallback;
    private HashMap hashMap;
    private Map map;
    private Map lockMap;
    private ExecutorService service;
    private CRC32Util crc32Util;
    private CRC32 crc32;

    public static StorageCallback getInstance() {
        synchronized (StorageCallback.class) {
            if (storageCallback == null) {
                storageCallback = new StorageCallback();
            }
        }
        return storageCallback;
    }

    private StorageCallback() {
        this.createDir();
        this.crc32Util = null;
        this.hashMap = new HashMap();
        HostService[] hostServices = HostService.getHosts();
        int length = hostServices.length;
        for (int i = 0; i < length; i++) {
            HostService hostService = hostServices[i];
            this.hashMap.put(Integer.valueOf(hostService.getApiType()), new TreeSet<>());
        }
        this.service = Executors.newSingleThreadExecutor();
        this.crc32 = new CRC32();
    }


    private void createDir() {
        File file = HXDataConfig.context.getFilesDir();
        this.map = new HashMap();
        this.lockMap = new HashMap();
        try {
            HostService[] hostServices = HostService.hosts;
            int length = hostServices.length;
            for (int i = 0; i < length; i++) {
                HostService service = hostServices[i];
                File file1 = new File(file, "hx_database" + service.getApiType() + "hxOfficial");
                if (!file1.exists()) {
                    file1.mkdir();
                }
                HXLog.eForDeveloper("createDir" + file1.getAbsolutePath());
                File file2 = new File(file, "Lock" + service.getApiType());
                this.map.put(new Integer(service.getApiType()), new RandomAccessFile(file2, "rw"));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private int getDirSize(File file) {
        try {
            long fileSize = 0L;
            if (file == null) {
                return 0;
            } else if (!file.isDirectory()) {
                return 0;
            } else {
                File[] files = file.listFiles();
                int subFiles = files.length;
                for (int i = 0; i < subFiles; i++) {
                    File subFile = files[i];
                    if (subFile.isFile()) {
                        fileSize += subFile.length();
                    }
                }
                int mSize = (int) (fileSize / 1048576L);
                return mSize;
            }
        } catch (Throwable throwable) {
            return 0;
        }
    }

    public void removeDir() {
        File file = HXDataConfig.context.getFilesDir();
        try {
            HostService[] hostServices = HostService.getHosts();
            int length = hostServices.length;
            for (int i = 0; i < length; i++) {
                HostService service = hostServices[i];
                File file1 = new File(file, "hx_database" + service.getApiType() + "hxOfficial");
                if (file1.exists()) {
                    List list = sortFile(file);
                    File subFile;
                    boolean isDelete;
                    for (Iterator iterator = list.iterator(); iterator.hasNext(); isDelete = subFile.delete()) {
                        subFile = (File) iterator.next();
                    }
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    public void getFileLock(HostService hostService) {
        try {
            this.lock.lock();
            this.lockMap.put(new Integer(hostService.getApiType()), ((RandomAccessFile) this.map.get(new Integer(hostService.getApiType()))).getChannel().lock());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    public void releaseFileLock(HostService hostService) {
        try {
            if (this.lockMap.get(new Integer(hostService.getApiType())) != null) {
                try {
                    ((FileLock) this.lockMap.get(new Integer(hostService.getApiType()))).release();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void clearFile(File file) {
        try {
            if (this.getDirSize(file) > 6) {
                this.cleanDir(file);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void cleanDir(File file) {
        try {
            if (file.isDirectory()) {
                List list = this.sortFile(file);
                this.cleanDir((File) list.get(0));
            } else {
                file.delete();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private List sortFile(File file) {
        List list = Arrays.asList(file.listFiles());
        try {
            Collections.sort(list, new FileComparator(this));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

    public synchronized void writeFile(String crc32Util, DataStoreObj dataStoreObj) {
        this.service.execute(new WriteData(crc32Util, dataStoreObj));
    }

    public void confirmRead(HostService hostService) {
        this.service.execute(new DeleteFileNoCRC32(hostService));
    }

    public synchronized List getList(HostService hostService, int length) {
        LinkedList linkedList = new LinkedList();
        File file = HXDataConfig.context.getFilesDir();
        File rootFile = new File(file, "hx_database" + hostService.getApiType() + "hxOfficial");
        RandomAccessFile randomAccessFile = null;
        FileLock fileLock = null;
        if (!rootFile.exists()) {
            HXLog.eForDeveloper("operationFolder is not exists: " + rootFile);
        } else {
            String[] fileList = rootFile.list();
            if (fileList != null && fileList.length > 0) {
                int minLength = fileList.length < length ? fileList.length : length;
                for (int i = 0; i < minLength; i++) {
                    try {
                        File subFile = new File(rootFile, fileList[i]);
                        randomAccessFile = new RandomAccessFile(subFile, "rw");
                        fileLock = randomAccessFile.getChannel().tryLock();
                        if (fileLock == null) {
                            randomAccessFile.close();
                        } else {
                            randomAccessFile.seek(1L);
                            byte[] bytes = new byte[(int) randomAccessFile.length()-1];
                            randomAccessFile.read(bytes);
                            HXLog.eForDeveloper(new String(bytes));
                            if (bytes != null) {
                                linkedList.add(bytes);
                            } else {
                                this.service.execute(new DeleteFile(crc32Util, hostService));
                            }
                        }
                    } catch (Throwable throwable) {
                        this.service.execute(new DeleteFile(crc32Util, hostService));
                    } finally {
                        try {
                            if (fileLock != null) {
                                fileLock.release();
                                fileLock = null;
                            }

                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                                randomAccessFile = null;
                            }
                        } catch (Throwable throwable) {

                        }
                    }
                }
            }
        }
        return linkedList;
    }


    class DeleteFile implements Runnable {
        private final String string;
        private final CRC32Util crc32Util;

        public DeleteFile(CRC32Util crc32Util, HostService hostService) {
            this.string = HXDataConfig.context.getFilesDir().getAbsolutePath() + File.separator + "hx_database" + hostService.getApiType() + "hxOfficial";
            this.crc32Util = crc32Util;
        }

        @Override
        public void run() {
            try {
                String str = this.string + File.separator + this.crc32Util.getStr();
                File file = new File(str);
                if (file.exists() && !file.delete()) {
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    class WriteData implements Runnable {
        private final String str;
        private final String data;

        public WriteData(String crc32Util, DataStoreObj dataStoreObj) {
            this.str = HXDataConfig.context.getFilesDir() + File.separator + "hx_database" + dataStoreObj.hostService.getApiType() + "hxOfficial";
            this.data = crc32Util;
        }

        @Override
        public void run() {
            try {
                File file = new File(this.str);
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdir();
                }
                StorageCallback.this.clearFile(file);
                String path = this.str + File.separator + System.currentTimeMillis();
                File file1 = new File(path);
                if (!file1.exists()) {
                    file1.createNewFile();
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(file1, "rw");
                randomAccessFile.seek(1L);
                randomAccessFile.write(this.data.getBytes());
                randomAccessFile.getFD().sync();
                randomAccessFile.close();
//                File file = new File(this.str);
//                if (!file.exists() && !file.isDirectory()) {
//                    file.mkdir();
//                }
//                StorageCallback.this.clearFile(file);
//                String path = this.str + File.separator + this.crc32Util.getStr();
//                File file1 = new File(path);
//                if (!file1.exists()) {
//                    file1.createNewFile();
//                }
//                RandomAccessFile randomAccessFile = new RandomAccessFile(file1, "rw");
//                randomAccessFile.seek(1L);
//                randomAccessFile.writeInt(this.crc32Util.getCRCValue());
//                randomAccessFile.writeInt(this.crc32Util.getCRCLength());
//                randomAccessFile.write(this.crc32Util.getBytes());
//                randomAccessFile.getFD().sync();
//                randomAccessFile.close();
            } catch (Throwable throwable) {

            }
        }
    }

    public synchronized void putCRC32(CRC32Util crc32Util, HostService hostService) {
        try {
            if (hostService != null && crc32Util != null) {
                ((TreeSet) this.hashMap.get(Integer.valueOf(hostService.getApiType()))).add(crc32Util);
            }
        } catch (Throwable throwable) {

        }
    }

    class DeleteFileNoCRC32 implements Runnable {
        private final String str;
        private final HostService hostService;

        public DeleteFileNoCRC32(HostService hostService) {
            this.str = HXDataConfig.context.getFilesDir() + File.separator + "hx_database" + hostService.getApiType() + "hxOfficial";
            this.hostService = hostService;
        }

        @Override
        public void run() {
            try {
                        File file = new File(str);
                        if (file.exists() && file.list().length > 0) {
                            String[] list = file.list();
                            for (int i = 0; i < list.length; i++) {
                                File file1 = new File(file, list[i]);
                                file1.delete();
                            }
                        }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }


}
