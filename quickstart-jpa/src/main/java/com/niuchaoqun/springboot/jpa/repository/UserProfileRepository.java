package com.niuchaoqun.springboot.jpa.repository;

import com.niuchaoqun.springboot.jpa.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}