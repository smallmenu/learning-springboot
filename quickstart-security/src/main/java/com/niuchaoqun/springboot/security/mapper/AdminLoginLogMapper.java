package com.niuchaoqun.springboot.security.mapper;

import com.niuchaoqun.springboot.security.dto.form.AdminLoginLogSearchForm;
import com.niuchaoqun.springboot.security.entity.AdminLoginLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminLoginLogMapper extends Mapper<AdminLoginLog> {
    List<AdminLoginLog> search(AdminLoginLogSearchForm searchForm);
}
