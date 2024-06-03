package com.poscodx.mysite.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVo {
    private Long no;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String joinDate;
}
