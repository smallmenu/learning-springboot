package com.niuchaoqun.springboot.mybatis.mapper;

import com.niuchaoqun.springboot.mybatis.entity.Mybatis;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MybatisMapper {
    Mybatis findById(long id);

    Mybatis findByName(String name);

    List<Mybatis> findAll();

    boolean insert(Mybatis mybatis);

    int update(Mybatis mybatis);

    int deleteById(long id);
}
