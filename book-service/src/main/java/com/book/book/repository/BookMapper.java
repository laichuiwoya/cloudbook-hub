package com.book.book.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.common.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    /** 模糊搜索：按书名或作者匹配 */
    @Select("SELECT * FROM book WHERE title LIKE CONCAT('%',#{keyword},'%') OR author LIKE CONCAT('%',#{keyword},'%')")
    List<Book> search(@Param("keyword") String keyword);
}
