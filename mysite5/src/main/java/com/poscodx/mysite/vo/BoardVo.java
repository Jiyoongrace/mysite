package com.poscodx.mysite.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVo {

    private Long no;
    private String title;
    private String contents;
    private Integer hit;
    private String regDate;
    private Integer groupNo;
    private Integer orderNo;
    private Integer depth;
    private Long userNo;
    private String userName;
}