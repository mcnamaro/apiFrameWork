package com.api_test.utill.data_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CreateUser {
    String name;
    String job;
    String id;
    String createdAt;



    public CreateUser() {

    }

    public CreateUser(String name, String job) {
        this.name = name;
        this.job = job;
    }









}
