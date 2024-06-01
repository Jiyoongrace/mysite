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
    private int hit;
    private String regDate;
    private int groupNo;
    private int orderNo;
    private int depth;
    private Long userNo;
    private String userName;
}