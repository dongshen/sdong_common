package sdong.common.utils;

import sdong.common.bean.loc.FileInfo;
import sdong.common.bean.loc.FileInfoSum;
import sdong.common.bean.loc.FileType;
import sdong.common.exception.SdongException;
import sdong.common.thread.LocInfoCallable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Calculate file source of line count
 *
 */
public class LocStats {

    private final static int ARG_FLODER = 0;
    private final static String PARM_HELP = "-h";
    private final static String PARM_LANGUAGE = "lang=";
    private final static String PARM_PRINT_LIST = "-list";
    private final static String PARM_PRINT_THREAD = "-j";
    private final static int DEFAULT_THREAD_NUM = 5;
    private final static String PARM_SPLIT = ",";

    private static final Logger LOG = LogManager.getLogger(LocStats.class);

    public static void main(String[] args) {

        try {
            if (args.length == 0 || args[0].equals(PARM_HELP)) {
                System.out.println("<scan folder> [lang=] [-list] [-j]");
                System.out.println("<scan folder>: mandatory seperate by \",\"");
                System.out.println("[lang=]: optional, only scan special language. like c,java");
                System.out.println("[-list]: optional, output inforamtion by file");
                System.out.println("[-j]: optional, thread number, default is 5.");
                return;
            }

            long startTime = System.currentTimeMillis();
            // check folder
            String argFloder = args[ARG_FLODER];
            Set<String> extList = new HashSet<String>();

            int threadNum = DEFAULT_THREAD_NUM;
            String parm;
            boolean isPrintList = false;
            for (int ind = 1; ind < args.length; ind++) {
                parm = args[ind];
                if (parm.indexOf(PARM_LANGUAGE) >= 0) {
                    // check language
                    extList = getExtList(parm.substring(parm.indexOf(PARM_LANGUAGE) + PARM_LANGUAGE.length()));
                } else if (parm.indexOf(PARM_PRINT_LIST) >= 0) {
                    isPrintList = true;
                } else if (parm.indexOf(PARM_PRINT_THREAD) >= 0) {
                    threadNum = CommonUtil
                            .parseInteger(parm.substring(parm.indexOf(PARM_PRINT_THREAD) + PARM_PRINT_THREAD.length()));
                    threadNum = 0 == threadNum ? DEFAULT_THREAD_NUM : threadNum;
                }
            }
            List<String> fileList = FileUtil.getFilesInFolderList(Arrays.asList(argFloder.split(PARM_SPLIT)), extList);

            List<FileInfo> fileInfoList = getFieInfoThread(fileList, threadNum);
            System.out.println("Use: " + (System.currentTimeMillis() - startTime) + " msec.");
            printFileInfoSum(fileInfoList, isPrintList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static List<FileInfo> getFieInfoThread(List<String> fileList, int threadNum) throws SdongException {
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        if (fileList == null || fileList.isEmpty()) {
            return fileInfoList;
        }

        ExecutorService pool = Executors.newFixedThreadPool(threadNum);
        int batchSize = fileList.size() / threadNum;
        batchSize = (0 == batchSize) ? 1 : batchSize;

        try {
            List<FutureTask<List<FileInfo>>> resultList = new ArrayList<FutureTask<List<FileInfo>>>();
            int filesCounts = fileList.size();
            int endInd = batchSize;
            for (int ind = 0; ind < filesCounts; ind = ind + batchSize) {
                endInd = ind + batchSize >= filesCounts ? filesCounts : ind + batchSize;
                LocInfoCallable task = new LocInfoCallable(fileList.subList(ind, endInd));
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

    public static ConcurrentHashMap<FileType, FileInfoSum> printFileInfoSum(List<FileInfo> fileInfoList,
            boolean isPrintList) {
        if (isPrintList) {
            for (FileInfo info : fileInfoList) {
                LOG.info(info.toString());
            }
        }
        ConcurrentHashMap<FileType, FileInfoSum> sumMap = new ConcurrentHashMap<FileType, FileInfoSum>();
        FileInfoSum fileInfoSum;
        for (FileInfo info : fileInfoList) {
            if (sumMap.containsKey(info.getFileType())) {
                fileInfoSum = sumMap.get(info.getFileType());
            } else {
                fileInfoSum = new FileInfoSum();
                fileInfoSum.setFileType(info.getFileType());
                sumMap.put(info.getFileType(), fileInfoSum);
            }
            fileInfoSum.setFilesCounts(fileInfoSum.getFilesCounts() + 1);
            fileInfoSum.setBlankLineCounts(fileInfoSum.getBlankLineCounts() + info.getBlankLineCounts());
            fileInfoSum.setCommentCounts(fileInfoSum.getCommentCounts() + info.getCommentCounts());
            fileInfoSum.setCommentInLineCounts(fileInfoSum.getCommentInLineCounts() + info.getCommentInLineCounts());
            fileInfoSum.setFileSize(fileInfoSum.getFileSize() + info.getFileSize());
            fileInfoSum.setLineCounts(fileInfoSum.getLineCounts() + info.getLineCounts());
            fileInfoSum.setRowLineCounts(fileInfoSum.getRowLineCounts() + info.getRowLineCounts());
        }

        printFileInfoBySysteOut(sumMap);
        return sumMap;

    }

    private static void printFileInfoBySysteOut(Map<FileType, FileInfoSum> sumMap) {
        System.out.println(
                "| File Type|          Files|    Blank Lines|       Comments|Comment in Line|      File Size|    Line Counts|Row Line Counts|");
        System.out.println(
                "|----------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|");
        FileInfoSum info;
        for (Map.Entry<FileType, FileInfoSum> sum : sumMap.entrySet()) {
            LOG.debug(sum.getValue().toString());
            info = sum.getValue();
            System.out.println(String.format("|%-10s|% 15d|% 15d|% 15d|% 15d|% 15d|% 15d|% 15d|", sum.getKey(),
                    info.getFilesCounts(), info.getBlankLineCounts(), info.getCommentCounts(),
                    info.getCommentInLineCounts(), info.getFileSize(),
                    info.getLineCounts(), info.getRowLineCounts()));
        }
    }

    protected static Set<String> getExtList(String langList) {
        Set<String> extList = new HashSet<String>();
        String[] langs = langList.split(PARM_SPLIT);
        for (String lang : langs) {
            extList.addAll(FileType.getFileTypeExt(FileType.getFileTypeByTypeName(lang)));
        }

        return extList;
    }
}