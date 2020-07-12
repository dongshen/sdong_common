package sdong.common.bean.defect;

import java.util.ArrayList;
import java.util.List;

import sdong.common.bean.loc.FileInfo;

public class ScanResult {
    // scan result summary
    // tool info
    String buildId = "";
    String tools = "";
    String version= "";

    // second
    int scanTime = 0;

    String runCommand ="";

    // scan files
    String sourceBasePath = "";    
    int numberFiles = 0;
    long sLoc = 0l;
    long rowLoc = 0l;

    List<FileInfo> fileList = new ArrayList<FileInfo>();

    // scan fail files
    int faileParseFiles = 0;
    long failLoc = 0l;
    List<String> failFileList = new ArrayList<String>();

    // defects
    List<Defect> defectList = new ArrayList<Defect>();

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRunCommand() {
        return runCommand;
    }

    public void setRunCommand(String runCommand) {
        this.runCommand = runCommand;
    }

    public String getSourceBasePath() {
        return sourceBasePath;
    }

    public void setSourceBasePath(String sourceBasePath) {
        this.sourceBasePath = sourceBasePath;
    }

    public int getNumberFiles() {
        return numberFiles;
    }

    public void setNumberFiles(int numberFiles) {
        this.numberFiles = numberFiles;
    }

    public int getFaileParseFiles() {
        return faileParseFiles;
    }

    public void setFaileParseFiles(int faileParseFiles) {
        this.faileParseFiles = faileParseFiles;
    }

    public long getFailLoc() {
        return failLoc;
    }

    public void setFailLoc(long failLoc) {
        this.failLoc = failLoc;
    }

    public List<String> getFailFileList() {
        return failFileList;
    }

    public void setFailFileList(List<String> failFileList) {
        this.failFileList = failFileList;
    }

    public List<Defect> getDefectList() {
        return defectList;
    }

    public void setDefectList(List<Defect> defectList) {
        this.defectList = defectList;
    }

    public int getScanTime() {
        return scanTime;
    }

    public void setScanTime(int scanTime) {
        this.scanTime = scanTime;
    }

    public long getsLoc() {
        return sLoc;
    }

    public void setsLoc(long sLoc) {
        this.sLoc = sLoc;
    }

    public long getRowLoc() {
        return rowLoc;
    }

    public void setRowLoc(long rowLoc) {
        this.rowLoc = rowLoc;
    }

    public List<FileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileInfo> fileList) {
        this.fileList = fileList;
    }
}