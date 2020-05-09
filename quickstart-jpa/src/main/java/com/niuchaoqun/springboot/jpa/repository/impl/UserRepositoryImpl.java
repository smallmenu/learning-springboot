package com.niuchaoqun.springboot.jpa.repository.impl;

import com.niuchaoqun.springboot.jpa.dto.form.UserSearchForm;
import com.niuchaoqun.springboot.jpa.entity.User;
import com.niuchaoqun.springboot.jpa.repository.UserCustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class UserRepositoryImpl implements UserCustomRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<User> search(UserSearchForm userSearchForm, Pageable pageable) {
        String querySql = "select t ";
        String countSql = "select count(t) ";
        StringBuffer sqlBuffer = new StringBuffer("from User t where 1=1");

        if (null != userSearchForm.getUsername()) {
            sqlBuffer.append(" and t.username = :username");
        }
        if (null != userSearchForm.getSex()) {
            sqlBuffer.append(" and t.sex = :sex");
        }

        querySql += sqlBuffer.toString();
        countSql += sqlBuffer.toString();

        Query dataQuery = em.createQuery(querySql);
        Query countQuery = em.createQuery(countSql);

        if (null != userSearchForm.getUsername()) {
            dataQuery.setParameter("username", userSearchForm.getUsername());
            countQuery.setParameter("username", userSearchForm.getUsername());
        }
        if (null != userSearchForm.getSex()) {
            dataQuery.setParameter("sex", userSearchForm.getSex());
            countQuery.setParameter("sex", userSearchForm.getSex());
        }

        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        long totalSize = (long) countQuery.getSingleResult();
        List<User> content = totalSize > (long) pageable.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
        Page<User> pages = new PageImpl(content, pageable, totalSize);

        return pages;
    }
}
