package com.niuchaoqun.springboot.repository;

import com.niuchaoqun.springboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Short> {
}
