package com.api_test.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//lombook for getters and setters
@Getter
@Setter
@NoArgsConstructor
public class YamlService {
    private String ContentType;
    private String Accept;
    private String request;
    private String service;
    private String jsonPath;
}
