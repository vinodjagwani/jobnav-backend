package com.jobnav.api.validator;

import com.jobnav.api.constant.ApplicationConstant;
import com.jobnav.api.validator.annotation.FileExtension;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Optional.ofNullable;


public class FileExtensionValidator implements ConstraintValidator<FileExtension, MultipartFile> {

    private FileExtension annotation;

    @Override
    public void initialize(final FileExtension annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final MultipartFile file, final ConstraintValidatorContext context) {
        return ofNullable(FilenameUtils.getExtension(file.getOriginalFilename()))
                .filter(fExt -> ApplicationConstant.PDF_EXT.equalsIgnoreCase(fExt) ||
                        ApplicationConstant.DOCX_EXT.equalsIgnoreCase(fExt) || ApplicationConstant.DOC_EXT.equalsIgnoreCase(fExt)).isPresent();
    }
}
