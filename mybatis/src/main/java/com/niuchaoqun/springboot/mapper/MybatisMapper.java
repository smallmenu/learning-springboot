package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Mybatis;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MybatisMapper {
    @Select("SELECT * FROM mybatis WHERE id = #{id}")
    Mybatis findById(long id);

    @Select("SELECT * FROM mybatis WHERE name = #{name}")
    Mybatis findByName(String name);

    @Select("SELECT * FROM mybatis")
    List<Mybatis> findAll();

    @Insert({
            "INSERT INTO mybatis",
            "(name, birthday, sex, access, access_time, state)",
            "VALUES",
            "(#{name}, #{birthday}, #{sex}, #{access}, #{accessTime}, #{state})"
    })
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id, created")
    int insert(Mybatis mybatis);

    @UpdateProvider(type = MybatisSqlProvider.class, method = "updateSelectiveById")
    int updateSelectiveById(Mybatis mybatis);

    @Delete("DELETE FROM mybatis WHERE id = #{id}")
    int deleteById(long id);
}
