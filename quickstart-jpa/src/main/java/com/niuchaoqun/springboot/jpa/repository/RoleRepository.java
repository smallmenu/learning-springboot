package com.niuchaoqun.springboot.jpa.repository;

import com.niuchaoqun.springboot.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Short> {
}
