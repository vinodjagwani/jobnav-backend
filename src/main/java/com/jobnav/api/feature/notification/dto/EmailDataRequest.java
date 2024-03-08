package com.jobnav.api.feature.notification.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Value
@Builder
@Validated
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailDataRequest {

    @NotEmpty
    String from;

    @NotEmpty
    List<@NotEmpty String> to;

    @NotEmpty
    String subject;

    @NotEmpty
    Object content;

}
