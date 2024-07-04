package com.nadiannis.consume_rest_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Quote {

    String type;
    Value value;

}
