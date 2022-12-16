package com.rui.online.utils;


/**
 * @version 3.5.0
 * @description: The type Error util.
 * Copyright (C), 2022-2022, 白色巨塔
 * @date 2022/12/25 9:45
 */
public class ErrorUtil {
    /**
     * Parameter error format string.
     *
     * @param field the field
     * @param msg   the msg
     * @return the string
     */
    public static String parameterErrorFormat(String field, String msg) {
        return "【" + field + " : " + msg + "】";
    }
}
