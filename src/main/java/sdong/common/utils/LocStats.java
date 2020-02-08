package sdong.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import sdong.common.bean.FileInfo;
import sdong.common.bean.FileType;
import sdong.common.exception.SdongException;
import sdong.common.thread.LocInfoCallable;

/**
 * Calculate file source of line count
 *
 */
public class LocStats {

    private final static int ARG_FLODER = 0;
    private final static int ARG_LANGUAGE = 1;

    private static final Logger LOG = LoggerFactory.getLogger(LocStats.class);

    public static void main(String[] args) {

        try {
            // check folder
            String argFloder = args[ARG_FLODER];
            List<String> fileList = FileUtil.getFilesInFolderSum(argFloder);

            // check language
            List<String> extList = new ArrayList<String>();
            if (args.length == ARG_LANGUAGE) {
                extList = getExtList(args[ARG_LANGUAGE]);
            }

            int threadNume = 5;
            List<FileInfo> fileInfoList = getFieInfoThread(fileList, threadNume);
            for(FileInfo info :fileInfoList){
                LOG.info(info.toString());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public static List<FileInfo> getFieInfoThread(List<String> fileList, int threadNum) throws SdongException {
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        if (fileList == null || fileList.isEmpty()) {
            return fileInfoList;
        }

        int numOfThread = threadNum == 0 ? 1 : threadNum;
        ExecutorService pool = Executors.newFixedThreadPool(numOfThread);
        int batchSize = fileList.size() / numOfThread;
        batchSize = (0 == batchSize) ? 1 : batchSize;

        try {
            List<FutureTask<List<FileInfo>>> resultList = new ArrayList<FutureTask<List<FileInfo>>>();
            for (int ind = 0; ind < fileList.size(); ind = ind + batchSize) {
                LocInfoCallable task = new LocInfoCallable(fileList.subList(ind, batchSize));
                FutureTask<List<FileInfo>> futureTask = new FutureTask<List<FileInfo>>(task);
                pool.submit(futureTask);
                resultList.add(futureTask);
            }

            pool.shutdown();
            boolean isDone = false;
            while (!isDone) {
                isDone = true;
                for (FutureTask<List<FileInfo>> task : resultList) {
                    isDone = isDone && task.isDone();
                }
            }

            // get task result
            for (FutureTask<List<FileInfo>> task : resultList) {
                fileInfoList.addAll(task.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e.getMessage());
        }

        return fileInfoList;
    }

    private static List<String> getExtList(String langList) {
        String[] langs = langList.split(",");
        List<String> extList = new ArrayList<String>();
        String extStr;
        for (String lang : langs) {
            extStr = FileType.getFileTypeExt(FileType.getFileTypeByTypeName(lang));
            if (extStr != null && !extStr.isEmpty()) {
                extList.addAll(Arrays.asList(extStr.split(",")));
            }
        }

        return extList;
    }
}