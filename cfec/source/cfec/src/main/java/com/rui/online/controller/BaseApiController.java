package com.rui.online.controller;

import com.rui.online.context.WebContext;
import com.rui.online.pojo.User;
import com.rui.online.utils.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/15 16:09
 * Version1.0.0
 */

public class BaseApiController {
    /**
     * 默认分页数量
     */
    protected final static String DEFAULT_PAGE_SIZE = "10";
    /**
     * The constant modelMapper.
     */
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    /**
     * The Web context.
     */
    @Autowired
    protected WebContext webContext;

    /**
     * Gets current user.
     *
     * @return the current user
     */
    protected User getCurrentUser() {
        return webContext.getCurrentUser();
    }
}
