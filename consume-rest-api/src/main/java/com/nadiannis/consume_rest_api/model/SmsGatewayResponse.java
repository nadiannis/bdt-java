package com.nadiannis.consume_rest_api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class SmsGatewayResponse {

    private String transactionId;
    private String responseCode;
    private String responseMessage;
    private List<SmsInfo> smsInformation;

}
