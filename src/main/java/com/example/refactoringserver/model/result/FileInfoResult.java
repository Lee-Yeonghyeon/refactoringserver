package com.example.refactoringserver.model.result;

import lombok.*;

import javax.persistence.*;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfoResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="filename")
    private String fileName;

    @Column(name="format")
    private String format;

    @Column(name="size")
    private long size;

    @Column(name="width")
    private long width;

    @Column(name = "height")
    private long height;

    @Column(name="url")
    private String url;

    public FileInfoResult(String fileName, String format, long size, long width, long height, String url){
        this.fileName = fileName;
        this.format = format;
        this.size = size;
        this.width = width;
        this.height = height;
        this.url = url;
    }

}
