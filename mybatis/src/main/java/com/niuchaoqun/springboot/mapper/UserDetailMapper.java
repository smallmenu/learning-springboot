package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.User;
import com.niuchaoqun.springboot.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDetailMapper {
    @Select("SELECT * FROM user_detail WHERE id = #{id}")
    UserDetail findById(long id);

    @Select("SELECT u.*, ud.address FROM user u, user_detail ud WHERE u.id = ud.user_id AND ud.user_id = #{id}")
    User findByUserId(long id);
}
