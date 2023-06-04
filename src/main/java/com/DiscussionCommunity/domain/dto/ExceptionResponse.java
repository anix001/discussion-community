package com.DiscussionCommunity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private Boolean success;
    private String method;
    private String url;
    private HttpStatus status;
    private String message;
    private ZonedDateTime timestamp;
}
