package com.niuchaoqun.springboot.repository;

import com.niuchaoqun.springboot.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}