package com.nadiannis.spring_onetomany.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlogFormDto {

    private String title;

    private String category;

    private String content;

    @JsonProperty(value = "owner_id")
    private Long ownerId;

}
