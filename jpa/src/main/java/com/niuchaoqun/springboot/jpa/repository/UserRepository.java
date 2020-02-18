package com.niuchaoqun.springboot.jpa.repository;


import com.niuchaoqun.springboot.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User SET state = ?2 WHERE id = ?1 ")
    Integer saveState(Long id, Integer state);
}
