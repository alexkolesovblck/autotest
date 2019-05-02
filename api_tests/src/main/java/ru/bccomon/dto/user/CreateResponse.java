package ru.bccomon.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateResponse {

    private String name;

    private String job;

    private String id;

    private String createdAt;

}
