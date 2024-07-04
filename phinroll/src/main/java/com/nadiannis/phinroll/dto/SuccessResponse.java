package com.nadiannis.phinroll.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuccessResponse<T> {

    private String status = "success";
    private String message;
    private T data;
    private Metadata metadata;

    public SuccessResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public SuccessResponse(String message, T data, Metadata metadata) {
        this.message = message;
        this.data = data;
        this.metadata = metadata;
    }

}
