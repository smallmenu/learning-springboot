package com.niuchaoqun.springboot.repository;

import com.niuchaoqun.springboot.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByUserId(Long userId);
}
