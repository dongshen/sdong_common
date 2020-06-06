package sdong.common.bean.loc;

import com.google.common.base.Optional;

import sdong.common.utils.CommonUtil;

/**
 * File type enum
 */
public enum FileType {
    C("C/C++"), Java("Java"), JavaScript("JavaScript"), Python("Python"), Go("Go"), Kotlin("Kotlin"), Jsp("Jsp"),
    Xml("Xml"), Others("Others");

    private static final String EXT_C = "idc,cats,c,tpp,tcc,ipp,h++,C,cc,c++,cpp,CPP,cxx,ec,h,H,hh,hpp,hxx,inl,pcc,pgc,";
    private static final String EXT_JAVA = "java,";
    private static final String EXT_JAVASCRIPT = "xsjslib,xsjs,ssjs,sjs,pac,njs,mjs,jss,jsm,jsfl,jscad,jsb,jakefile,jake,bones,_js,js,es6,jsf,";
    private static final String EXT_PYTHON = "xpy,wsgi,wscript,workspace,tac,snakefile,sconstruct,sconscript,pyt,pyp,pyi,pyde,py3,lmi,gypi,gyp,build.bazel,buck,gclient,py,pyw,";
    private static final String EXT_GO = "go,";
    private static final String EXT_KOTLIN = "kt,ktm,kts,";
    private static final String EXT_JSP = "jsp,jspf,";
    private static final String EXT_XML = "zcml,xul,xspec,xproj,xml.dist,xliff,xlf,xib,xacro,x3d,wsf,"
            + "web.release.config,web.debug.config,web.config,vxml,vstemplate,vssettings,vsixmanifest,vcxproj,"
            + "ux,urdf,tmtheme,tmsnippet,tmpreferences,tmlanguage,tml,tmcommand,targets,sublime-snippet,sttheme,"
            + "storyboard,srdf,shproj,sfproj,settings.stylecop,scxml,rss,resx,rdf,pt,psc1,ps1xml,props,proj,plist,"
            + "pkgproj,packages.config,osm,odd,nuspec,nuget.config,nproj,ndproj,natvis,mjml,mdpolicy,launch,kml,"
            + "jsproj,jelly,ivy,iml,grxml,gmx,fsproj,filters,dotsettings,dll.config,ditaval,ditamap,depproj,ct,"
            + "csl,csdef,cscfg,cproject,clixml,ccxml,ccproj,builds,axml,app.config,ant,admx,adml,project,"
            + "classpath,xml,XML,mxml,MXML";

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
        } else if (EXT_XML.indexOf(ext) >= 0) {
            return FileType.Xml;
        } else {
            return FileType.Others;
        }
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
    public static String getFileTypeExt(FileType fileType) {
        String ext = "";
        if (fileType == null) {
            return ext;
        }
        switch (fileType) {
        case C:
            return EXT_C;
        case Java:
            return EXT_JAVA;
        case JavaScript:
            return EXT_JAVASCRIPT;
        case Python:
            return EXT_PYTHON;
        case Go:
            return EXT_GO;
        case Kotlin:
            return EXT_KOTLIN;
        case Jsp:
            return EXT_JSP;
        case Xml:
            return EXT_XML;
        case Others:
            return ext;
        default:
            break;
        }
        return ext;
    }
}
