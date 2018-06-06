package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Mybatis;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MybatisMapper {
    @Select("SELECT * FROM mybatis WHERE id = #{id}")
    Mybatis findById(Long id);

    @Delete({
            "delete from user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteById(Long id);
}
