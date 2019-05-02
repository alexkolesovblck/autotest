package ru.bccomon.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRequest {

    private String name;

    private String job;

}
