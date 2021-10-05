package sdong.common.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import sdong.common.bean.loc.FileInfo;
import sdong.common.exception.SdongException;
import sdong.common.utils.LocUtil;

public class LocInfoCallable implements Callable<List<FileInfo>> {
    private static final Logger LOG = LogManager.getLogger(LocInfoCallable.class);

    List<String> fileList;

    public LocInfoCallable(List<String> fileList) {
        this.fileList = fileList;
    }

    @Override
    public List<FileInfo> call() throws Exception{
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        try{           
            for(String fileName: fileList){
                fileInfoList.add(LocUtil.getFileLocInfo(new File(fileName)));
            }
        }catch(SdongException e){
            LOG.error(e.getMessage(),e );
            throw new Exception(e.getMessage());
        }
        return fileInfoList;
    }
}