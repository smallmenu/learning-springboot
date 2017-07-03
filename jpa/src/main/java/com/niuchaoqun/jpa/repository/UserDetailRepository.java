package com.niuchaoqun.jpa.repository;

import com.niuchaoqun.jpa.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByUserId(Long userId);
}
