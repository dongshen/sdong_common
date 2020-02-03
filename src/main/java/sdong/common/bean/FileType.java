package sdong.common.bean;

public enum FileType {
    C("C/C++"), Java("Java"), JavaScript("JavaScript"), Python("Python");

    private static final String extensionC = "idc,cats,c,tpp,tcc,ipp,h++,C,cc,c++,cpp,CPP,cxx,ec,h,H,hh,hpp,hxx,inl,pcc,pgc,";
    private static final String extensionJava = "java,";
    private static final String extensionJavaScript = "xsjslib,xsjs,ssjs,sjs,pac,njs,mjs,jss,jsm,jsfl,jscad,jsb,jakefile,jake,bones,_js,js,es6,jsf,";
    private static final String extensionPython = "xpy,wsgi,wscript,workspace,tac,snakefile,sconstruct,sconscript,pyt,pyp,pyi,pyde,py3,lmi,gypi,gyp,build.bazel,buck,gclient,py,pyw,";

    private final String fileType;

    private FileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public static FileType getFileType(String extension) {
        if (extensionC.indexOf(extension + ",") >= 0) {
            return FileType.C;
        } else if (extensionJava.indexOf(extension + ",") >= 0) {
            return FileType.Java;
        } else if (extensionJavaScript.indexOf(extension + ",") >= 0) {
            return FileType.JavaScript;
        } else if (extensionPython.indexOf(extension + ",") >= 0) {
            return FileType.Python;
        } else {
            return null;
        }
    }
}
