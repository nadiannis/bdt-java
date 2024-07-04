package com.nadiannis.phinroll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataList<T> {

    private T data;
    private Metadata metadata;

}
