package com.example.lab.payload.request;

import lombok.Data;


@Data
public class CommonParams {
    private String search;
    private Long from;
    private Long to;
    private Boolean status = true;
}
