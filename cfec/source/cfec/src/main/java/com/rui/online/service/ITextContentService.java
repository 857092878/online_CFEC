package com.rui.online.service;

import com.rui.online.pojo.TextContent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
public interface ITextContentService extends IService<TextContent> {

    <T, R> TextContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper);

    <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper);

    TextContent selectById(Integer frameTextContentId);

    void insertByFilter(TextContent frameTextContent);

    void updateByIdFilter(TextContent textContent);
}
