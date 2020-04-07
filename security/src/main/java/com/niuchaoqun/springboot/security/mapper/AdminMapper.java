package com.niuchaoqun.springboot.security.mapper;

import com.niuchaoqun.springboot.security.dto.form.AdminSearchForm;
import com.niuchaoqun.springboot.security.entity.Admin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface AdminMapper extends Mapper<Admin> {
    Admin getRelationById(Long id);

    List<Admin> search(AdminSearchForm searchForm);
}
