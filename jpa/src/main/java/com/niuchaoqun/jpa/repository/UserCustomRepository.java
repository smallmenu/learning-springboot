package com.niuchaoqun.jpa.repository;

import com.niuchaoqun.jpa.dto.form.UserSearchForm;
import com.niuchaoqun.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
    Page<User> search(UserSearchForm userSearchForm, Pageable pageRequest);
}
