package sdong.common.bean.loc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;

import sdong.common.utils.CommonUtil;

/**
 * File type enum
 */
public enum FileType {
    C("C/C++"), Java("Java"), JavaScript("JavaScript"), Python("Python"), Go("Go"), Kotlin("Kotlin"), Jsp("Jsp"),
    Xml("Xml"), Others("Others");

    private static final String EXT_C = "idc,cats,c,tpp,tcc,ipp,h++,C,cc,c++,cpp,CPP,cxx,ec,h,H,hh,hpp,hxx,inl,pcc,pgc";
    private static final String EXT_JAVA = "java,";
    private static final String EXT_JAVASCRIPT = "xsjslib,xsjs,ssjs,sjs,pac,njs,mjs,jss,jsm,jsfl,jscad,jsb,jakefile,"
            + "jake,bones,_js,js,es6,jsf";
    private static final String EXT_PYTHON = "xpy,wsgi,wscript,workspace,tac,snakefile,sconstruct,sconscript,pyt,pyp,"
            + "pyi,pyde,py3,lmi,gypi,gyp,build.bazel,buck,gclient,py,pyw";
    private static final String EXT_GO = "go";
    private static final String EXT_KOTLIN = "kt,ktm,kts";
    private static final String EXT_JSP = "jsp,jspf";
    private static final String EXT_XML = "zcml,xul,xspec,xproj,xml.dist,xliff,xlf,xib,xacro,x3d,wsf,"
            + "web.release.config,web.debug.config,web.config,vxml,vstemplate,vssettings,vsixmanifest,vcxproj,"
            + "ux,urdf,tmtheme,tmsnippet,tmpreferences,tmlanguage,tml,tmcommand,targets,sublime-snippet,sttheme,"
            + "storyboard,srdf,shproj,sfproj,settings.stylecop,scxml,rss,resx,rdf,pt,psc1,ps1xml,props,proj,plist,"
            + "pkgproj,packages.config,osm,odd,nuspec,nuget.config,nproj,ndproj,natvis,mjml,mdpolicy,launch,kml,"
            + "jsproj,jelly,ivy,iml,grxml,gmx,fsproj,filters,dotsettings,dll.config,ditaval,ditamap,depproj,ct,"
            + "csl,csdef,cscfg,cproject,clixml,ccxml,ccproj,builds,axml,app.config,ant,admx,adml,project,"
            + "classpath,xml,XML,mxml,MXML";

    private static final String SPLIT = ",";
    public static final Map<FileType, Set<String>> FILE_TYPE_MAP = new HashMap<FileType, Set<String>>();
    static {
        FILE_TYPE_MAP.put(FileType.C, new HashSet<String>(Arrays.asList(EXT_C.split(SPLIT))));
        FILE_TYPE_MAP.put(FileType.Java, new HashSet<String>(Arrays.asList(EXT_JAVA.split(SPLIT))));
        FILE_TYPE_MAP.put(FileType.JavaScript, new HashSet<String>(Arrays.asList(EXT_JAVASCRIPT.split(SPLIT))));
        FILE_TYPE_MAP.put(FileType.Python, new HashSet<String>(Arrays.asList(EXT_PYTHON.split(SPLIT))));
        FILE_TYPE_MAP.put(FileType.Go, new HashSet<String>(Arrays.asList(EXT_GO.split(SPLIT))));
        FILE_TYPE_MAP.put(FileType.Kotlin, new HashSet<String>(Arrays.asList(EXT_KOTLIN.split(SPLIT))));
        FILE_TYPE_MAP.put(FileType.Jsp, new HashSet<String>(Arrays.asList(EXT_JSP.split(SPLIT))));
        FILE_TYPE_MAP.put(FileType.Xml, new HashSet<String>(Arrays.asList(EXT_XML.split(SPLIT))));
    }

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
    public static FileType getFileTypeByExt(String extension) {
        if (extension == null || extension.isEmpty()) {
            return FileType.Others;
        }

        for (Map.Entry<FileType, Set<String>> entry : FILE_TYPE_MAP.entrySet()) {
            if (entry.getValue().contains(extension)) {
                return entry.getKey();
            }
        }

        return FileType.Others;

    }

    /**
     * Get File type
     *
     * @param fileTypeName file type name
     * @return file type
     */
    public static FileType getFileTypeByTypeName(String fileTypeName) {
        Optional<FileType> option = CommonUtil.getEnum(FileType.class, fileTypeName);
        if (option.isPresent()) {
            return option.get();
        } else {
            return FileType.Others;
        }
    }

    /**
     * Get file type related extsion
     *
     * @param fileType input file type
     * @return extension
     */
    public static Set<String> getFileTypeExt(FileType fileType) {
        Set<String> exts = new HashSet<String>();
        if (fileType == null) {
            return exts;
        }
        if (FILE_TYPE_MAP.containsKey(fileType)) {
            return FILE_TYPE_MAP.get(fileType);
        }

        return exts;
    }
}
