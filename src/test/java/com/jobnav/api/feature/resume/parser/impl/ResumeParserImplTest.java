package com.jobnav.api.feature.resume.parser.impl;


import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ResumeParserImplTest {

    @InjectMocks
    ResumeParserImpl resumeParserImpl;

    @Test
    @SneakyThrows
    void testParseResume() {
        final String path = ResourceUtils.getURL("src/test/resources/cv.docx").getPath();
        final JSONObject jsonObject = resumeParserImpl.parseResume(path);
        assertTrue(jsonObject.containsKey("basics"));
    }
}
