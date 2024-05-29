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
    private Long hit;
    private String regDate;
    private Long groupNo;
    private Long orderNo;
    private Long depth;
    private Long userNo;
}
