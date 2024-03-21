package com.example.demo.src.user.model;

import com.example.demo.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLoginReq {
    @NotBlank(message = Constant.LOGIN_ID_NOTNULL)
    @Pattern(regexp = Constant.LOGIN_ID_REGEX, message = Constant.LOGIN_ID_VALID)
    private String loginId;

    @NotBlank(message = Constant.PASSWORD_NOTNULL)
    @Pattern(regexp = Constant.PASSWORD_REGEX, message = Constant.PASSWORD_VALID)
    private String password;
}
