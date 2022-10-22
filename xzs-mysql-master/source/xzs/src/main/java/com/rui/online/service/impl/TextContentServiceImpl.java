package com.rui.online.service.impl;

import com.rui.online.pojo.TextContent;
import com.rui.online.mapper.TextContentMapper;
import com.rui.online.service.ITextContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.online.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Service
public class TextContentServiceImpl extends ServiceImpl<TextContentMapper, TextContent> implements ITextContentService {

    @Autowired
    private TextContentMapper textContentMapper;

    @Override
    public <T, R> TextContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JsonUtil.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JsonUtil.toJsonStr(mapList);
        }
        TextContent textContent = new TextContent(frameTextContent, now);
        return textContent;
    }

    @Override
    public <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JsonUtil.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JsonUtil.toJsonStr(mapList);
        }
        textContent.setContent(frameTextContent);
        return textContent;
    }

    @Override
    public TextContent selectById(Integer frameTextContentId) {
        return textContentMapper.selectById(frameTextContentId);
    }

    @Override
    public void insertByFilter(TextContent frameTextContent) {
        textContentMapper.insert(frameTextContent);
    }

    @Override
    public void updateByIdFilter(TextContent textContent) {
        textContentMapper.updateById(textContent);
    }
}
