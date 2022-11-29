package com.rui.online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.online.pojo.Hidden;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/29 21:02
 * Version1.0.0
 */

public interface IHiddenService extends IService<Hidden> {
    Hidden findHidden();
}
