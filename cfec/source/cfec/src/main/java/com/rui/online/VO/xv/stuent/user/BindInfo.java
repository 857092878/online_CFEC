package com.rui.online.VO.xv.stuent.user;


import lombok.ToString;

import javax.validation.constraints.NotBlank;
@ToString
public class BindInfo {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String code;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
