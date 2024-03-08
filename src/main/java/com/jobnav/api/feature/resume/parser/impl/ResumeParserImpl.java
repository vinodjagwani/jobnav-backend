package com.jobnav.api.feature.resume.parser.impl;


import com.jobnav.api.feature.resume.parser.ResumeParser;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.util.GateException;
import gate.util.Out;
import gate.util.persistence.PersistenceManager;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import static com.jobnav.api.constant.ApplicationConstant.USER_DIR;
import static com.jobnav.api.constant.ApplicationConstant.fileExtensions;
import static gate.Utils.stringFor;
import static org.apache.commons.io.FilenameUtils.removeExtension;

@Component
public class ResumeParserImpl implements ResumeParser {

    @Override
    public JSONObject parseResume(final String file) throws IOException, SAXException, TikaException, GateException {
        final File f = getFileParseFile(file);
        setGateHome();
        Gate.init();
        final CorpusController corpusController = initAnnie();
        final Corpus corpus = Factory.newCorpus("Annie corpus");
        final URL u = f.toURI().toURL();
        final FeatureMap params = Factory.newFeatureMap();
        params.put("sourceUrl", u);
        params.put("preserveOriginalContent", Boolean.TRUE);
        params.put("collectRepositioningInfo", Boolean.TRUE);
        Out.prln("Creating doc for " + u);
        final Document resume = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
        corpus.add(resume);
        corpusController.setCorpus(corpus);
        corpusController.execute();
        final Iterator<Document> documentIterator = corpus.iterator();
        final JSONObject parsedJSON = new JSONObject();
        if (documentIterator.hasNext()) {
            final JSONObject profileJSON = new JSONObject();
            final Document doc = documentIterator.next();
            final AnnotationSet defaultAnnotateSet = doc.getAnnotations();
            AnnotationSet curAnnSet;
            Iterator<Annotation> it;
            Annotation currAnnotate;
            curAnnSet = defaultAnnotateSet.get("NameFinder");
            if (curAnnSet.iterator().hasNext()) {
                currAnnotate = curAnnSet.iterator().next();
                String gender = (String) currAnnotate.getFeatures().get("gender");
                if (gender != null && gender.length() > 0) {
                    profileJSON.put("gender", gender);
                }
                final JSONObject nameJson = new JSONObject();
                final String[] nameFeatures = new String[]{"firstName", "middleName", "surname"};
                for (String feature : nameFeatures) {
                    String s = (String) currAnnotate.getFeatures().get(feature);
                    if (s != null && s.length() > 0) {
                        nameJson.put(feature, s);
                    }
                }
                profileJSON.put("name", nameJson);
            }
            curAnnSet = defaultAnnotateSet.get("TitleFinder");
            if (curAnnSet.iterator().hasNext()) {
                currAnnotate = curAnnSet.iterator().next();
                String title = stringFor(doc, currAnnotate);
                if (title != null && title.length() > 0) {
                    profileJSON.put("title", title);
                }
            }
            final String[] annSections = new String[]{"EmailFinder", "AddressFinder", "PhoneFinder", "URLFinder"};
            final String[] annKeys = new String[]{"email", "address", "phone", "url"};
            for (short i = 0; i < annSections.length; i++) {
                String annSection = annSections[i];
                curAnnSet = defaultAnnotateSet.get(annSection);
                it = curAnnSet.iterator();
                JSONArray sectionArray = new JSONArray();
                while (it.hasNext()) {
                    currAnnotate = it.next();
                    final String s = stringFor(doc, currAnnotate);
                    if (s != null && s.length() > 0) {
                        sectionArray.add(s);
                    }
                }
                if (!sectionArray.isEmpty()) {
                    profileJSON.put(annKeys[i], sectionArray);
                }
            }
            if (!profileJSON.isEmpty()) {
                parsedJSON.put("basics", profileJSON);
            }
            final String[] otherSections = new String[]{"summary", "education_and_training", "skills", "accomplishments",
                    "awards", "credibility", "extracurricular", "misc"};
            for (String otherSection : otherSections) {
                curAnnSet = defaultAnnotateSet.get(otherSection);
                it = curAnnSet.iterator();
                final JSONArray subSections = new JSONArray();
                while (it.hasNext()) {
                    final JSONObject subSection = new JSONObject();
                    currAnnotate = it.next();
                    final String key = (String) currAnnotate.getFeatures().get("sectionHeading");
                    final String value = stringFor(doc, currAnnotate);
                    if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                        subSection.put(key, value);
                    }
                    if (!subSection.isEmpty()) {
                        subSections.add(subSection);
                    }
                }
                if (!subSections.isEmpty()) {
                    parsedJSON.put(otherSection, subSections);
                }
            }
            curAnnSet = defaultAnnotateSet.get("work_experience");
            it = curAnnSet.iterator();
            final JSONArray workExperiences = new JSONArray();
            while (it.hasNext()) {
                final JSONObject workExperience = new JSONObject();
                currAnnotate = it.next();
                String key = (String) currAnnotate.getFeatures().get("sectionHeading");
                if (key.equals("work_experience_marker")) {
                    String[] annotations = new String[]{"date_start", "date_end", "jobtitle", "organization"};
                    for (String annotation : annotations) {
                        String v = (String) currAnnotate.getFeatures().get(annotation);
                        if (!StringUtils.isBlank(v)) {
                            workExperience.put(annotation, v);
                        }
                    }
                    key = "text";
                }
                final String value = stringFor(doc, currAnnotate);
                if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                    workExperience.put(key, value);
                }
                if (!workExperience.isEmpty()) {
                    workExperiences.add(workExperience);
                }
            }
            if (!workExperiences.isEmpty()) {
                parsedJSON.put("work_experience", workExperiences);
            }
        }
        return parsedJSON;

    }

    private void setGateHome() {
        System.setProperty("gate.site.config", System.getProperty(USER_DIR) + "/GATEFiles/gate.xml");
        if (Gate.getGateHome() == null)
            Gate.setGateHome(new File(System.getProperty(USER_DIR) + "/GATEFiles"));
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(new File(System.getProperty(USER_DIR) + "/GATEFiles/plugins"));
    }

    @SneakyThrows
    private CorpusController initAnnie() {
        final File annieGap = new File(Gate.getGateHome() + "/ANNIEResumeParser.gapp");
        return (CorpusController) PersistenceManager.loadObjectFromFile(annieGap);
    }

    private File getFileParseFile(final String file) throws IOException, SAXException, TikaException {
        final String newFileExtension = removeExtension(file) + fileExtensions.getOrDefault(FilenameUtils.getExtension(file).toLowerCase(), ".txt");
        final ContentHandler handler = new ToXMLContentHandler();
        try (final InputStream inputStream = new FileInputStream(file)) {
            final AutoDetectParser parser = new AutoDetectParser();
            final Metadata metadata = new Metadata();
            parser.parse(inputStream, handler, metadata);
            try (final FileWriter htmlFileWriter = new FileWriter(newFileExtension)) {
                htmlFileWriter.write(handler.toString());
                htmlFileWriter.flush();
            }
            return new File(newFileExtension);
        }
    }
}
