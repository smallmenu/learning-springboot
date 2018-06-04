package com.niuchaoqun.springboot.repository;

import com.niuchaoqun.springboot.dto.form.UserSearchForm;
import com.niuchaoqun.springboot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCustomRepository {
    Page<User> search(UserSearchForm userSearchForm, Pageable pageRequest);
}
