package com.niuchaoqun.springboot.gmapper;

import com.niuchaoqun.springboot.gentity.User;
import com.niuchaoqun.springboot.gentity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {
    @SelectProvider(type=UserSqlProvider.class, method="countByExample")
    long countByExample(UserExample example);

    @DeleteProvider(type=UserSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserExample example);

    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user (id, role_id, ",
        "username, password, ",
        "salt, name, birthday, ",
        "sex, access, access_time, ",
        "created, updated, ",
        "state)",
        "values (#{id,jdbcType=INTEGER}, #{roleId,jdbcType=TINYINT}, ",
        "#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{salt,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{birthday,jdbcType=DATE}, ",
        "#{sex,jdbcType=CHAR}, #{access,jdbcType=TIMESTAMP}, #{accessTime,jdbcType=TIME}, ",
        "#{created,jdbcType=TIMESTAMP}, #{updated,jdbcType=TIMESTAMP}, ",
        "#{state,jdbcType=BIT})"
    })
    int insert(User record);

    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @SelectProvider(type=UserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.TINYINT),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="birthday", property="birthday", jdbcType=JdbcType.DATE),
        @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
        @Result(column="access", property="access", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="access_time", property="accessTime", jdbcType=JdbcType.TIME),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated", property="updated", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="state", property="state", jdbcType=JdbcType.BIT)
    })
    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    @SelectProvider(type=UserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.TINYINT),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="birthday", property="birthday", jdbcType=JdbcType.DATE),
        @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
        @Result(column="access", property="access", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="access_time", property="accessTime", jdbcType=JdbcType.TIME),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated", property="updated", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="state", property="state", jdbcType=JdbcType.BIT)
    })
    List<User> selectByExample(UserExample example);

    @Select({
        "select",
        "id, role_id, username, password, salt, name, birthday, sex, access, access_time, ",
        "created, updated, state",
        "from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.TINYINT),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="birthday", property="birthday", jdbcType=JdbcType.DATE),
        @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
        @Result(column="access", property="access", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="access_time", property="accessTime", jdbcType=JdbcType.TIME),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated", property="updated", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="state", property="state", jdbcType=JdbcType.BIT)
    })
    User selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update user",
        "set role_id = #{roleId,jdbcType=TINYINT},",
          "username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "salt = #{salt,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "birthday = #{birthday,jdbcType=DATE},",
          "sex = #{sex,jdbcType=CHAR},",
          "access = #{access,jdbcType=TIMESTAMP},",
          "access_time = #{accessTime,jdbcType=TIME},",
          "created = #{created,jdbcType=TIMESTAMP},",
          "updated = #{updated,jdbcType=TIMESTAMP},",
          "state = #{state,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);
}