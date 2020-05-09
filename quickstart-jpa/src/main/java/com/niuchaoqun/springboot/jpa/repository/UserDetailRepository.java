package com.niuchaoqun.springboot.jpa.repository;

import com.niuchaoqun.springboot.jpa.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByUserId(Long userId);
}
