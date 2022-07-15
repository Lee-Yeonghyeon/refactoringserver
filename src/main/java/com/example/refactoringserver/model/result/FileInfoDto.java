package com.example.refactoringserver.model.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfoDto {

    private int id;

    private String fileName;

    private String format;

    private long size;

    private long width;

    private long height;

    private String url;
}
