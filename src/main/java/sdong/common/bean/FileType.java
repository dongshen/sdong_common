package sdong.common.bean;

/**
 * File type enum
 */
public enum FileType {
    C("C/C++"), Java("Java"), JavaScript("JavaScript"), Python("Python"), Go("Go"), Kotlin("Kotlin"), Jsp("Jsp");

    private static final String EXT_C = "idc,cats,c,tpp,tcc,ipp,h++,C,cc,c++,cpp,CPP,cxx,ec,h,H,hh,hpp,hxx,inl,pcc,pgc,";
    private static final String EXT_JAVA = "java,";
    private static final String EXT_JAVASCRIPT = "xsjslib,xsjs,ssjs,sjs,pac,njs,mjs,jss,jsm,jsfl,jscad,jsb,jakefile,jake,bones,_js,js,es6,jsf,";
    private static final String EXT_PYTHON = "xpy,wsgi,wscript,workspace,tac,snakefile,sconstruct,sconscript,pyt,pyp,pyi,pyde,py3,lmi,gypi,gyp,build.bazel,buck,gclient,py,pyw,";
    private static final String EXT_GO = "go,";
    private static final String EXT_KOTLIN = "kt,ktm,kts,";
    private static final String EXT_JSP = "jsp,jspf,";

    private final String fileType;

    private FileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    /**
     * Get file type
     * 
     * @param extension file extension
     * @return file type
     */
    public static FileType getFileType(String extension) {
        String ext = extension + ",";
        if (EXT_C.indexOf(ext) >= 0) {
            return FileType.C;
        } else if (EXT_JAVA.indexOf(ext) >= 0) {
            return FileType.Java;
        } else if (EXT_JAVASCRIPT.indexOf(ext) >= 0) {
            return FileType.JavaScript;
        } else if (EXT_PYTHON.indexOf(ext) >= 0) {
            return FileType.Python;
        } else if (EXT_GO.indexOf(ext) >= 0) {
            return FileType.Go;
        } else if (EXT_KOTLIN.indexOf(ext) >= 0) {
            return FileType.Kotlin;
        } else if (EXT_JSP.indexOf(ext) >= 0) {
            return FileType.Jsp;
        } else {
            return null;
        }
    }
}
