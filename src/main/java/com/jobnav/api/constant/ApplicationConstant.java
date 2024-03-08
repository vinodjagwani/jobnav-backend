package com.jobnav.api.constant;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ApplicationConstant {

    public static final String HTML_EXT = ".html";
    public static final String PDF_EXT = "pdf";
    public static final String DOCX_EXT = "docx";
    public static final String DOC_EXT = "doc";
    public static final String USER_DIR = "user.dir";
    public static final Map<String, String> fileExtensions = Map.of("html", HTML_EXT, PDF_EXT, HTML_EXT, DOC_EXT, HTML_EXT, DOCX_EXT, HTML_EXT, "txt", ".txt", "rtf", ".txt");


}
