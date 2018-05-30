package com.niuchaoqun.jpa.repository;

import com.niuchaoqun.jpa.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}