package com.poscodx.mysite.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class UserVo {
    private Long no;

    @NotEmpty
    @Length(min=2, max=8)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min=4, max=16)
    private String password; // @Pattern 으로 정규표현식 적용해서 많이 씀

    private String gender = "female";
    private String joinDate;
    private String role;

    // custom validation 도 가능하다.
}
