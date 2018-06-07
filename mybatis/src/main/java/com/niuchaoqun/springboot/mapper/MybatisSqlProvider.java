package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Mybatis;
import org.apache.ibatis.jdbc.SQL;


public class MybatisSqlProvider {
    public String updateSelectiveById(Mybatis record) {
        SQL sql = new SQL();
        sql.UPDATE("mybatis");

        if (record.getBirthday() != null) {
            sql.SET("birthday = #{birthday}");
        }

        if (record.getBirthday() != null) {
            sql.SET("sex = #{sex}");
        }

        if (record.getName() != null) {
            sql.SET("name = #{name}");
        }

        sql.WHERE("id = #{id}");

        return sql.toString();
    }
}
