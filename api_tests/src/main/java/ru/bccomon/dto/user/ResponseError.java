package ru.bccomon.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseError {
    private int code;
    private String reason;
    private String hostname;
    private String message;
}
