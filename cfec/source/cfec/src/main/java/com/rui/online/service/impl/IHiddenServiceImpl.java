package com.rui.online.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.online.mapper.HiddenMapper;
import com.rui.online.pojo.Hidden;
import com.rui.online.service.IHiddenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/29 21:03
 * Version1.0.0
 */
@Service
public class IHiddenServiceImpl extends ServiceImpl<HiddenMapper,Hidden> implements IHiddenService {
    @Autowired
    private HiddenMapper hiddenMapper;
    @Override
    public Hidden findHidden() {
        return hiddenMapper.selectById(1);
    }
}
