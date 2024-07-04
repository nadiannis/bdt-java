package com.nadiannis.phinroll.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private int page;
    private int limit;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
