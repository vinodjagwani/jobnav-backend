package com.jobnav.api.feature.resume.parser;

import gate.util.GateException;
import org.apache.tika.exception.TikaException;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;

@FunctionalInterface
public interface ResumeParser {

    JSONObject parseResume(final String file) throws IOException, SAXException, TikaException, GateException;
}
