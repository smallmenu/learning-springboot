//package com.niuchaoqun.jpa.repository.impl;
//
//
//import com.niuchaoqun.jpa.dto.UserSearchForm;
//import com.niuchaoqun.jpa.entity.User;
//import com.niuchaoqun.jpa.repository.UserCustomRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.dto.Page;
//import org.springframework.data.dto.PageImpl;
//import org.springframework.data.dto.Pageable;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.util.Collections;
//import java.util.List;
//
//public class UserRepositoryImpl implements UserCustomRepository {
//    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public Page<User> search(UserSearchForm userSearch, Pageable pageable) {
//        String querySql = "select t ";
//        String countSql = "select count(t) ";
//        StringBuffer sqlBuffer = new StringBuffer("from User t where 1=1");
//
//        if (null != userSearch.getUsername()) {
//            sqlBuffer.append(" and t.username = :username");
//        }
//        if (null != userSearch.getSex()) {
//            sqlBuffer.append(" and t.sex = :sex");
//        }
//
//        querySql += sqlBuffer.toString();
//        countSql += sqlBuffer.toString();
//
//        Query dataQuery = em.createQuery(querySql);
//        Query countQuery = em.createQuery(countSql);
//
//        if (null != userSearch.getUsername()) {
//            dataQuery.setParameter("username", userSearch.getUsername());
//            countQuery.setParameter("username", userSearch.getUsername());
//        }
//        if (null != userSearch.getSex()) {
//            dataQuery.setParameter("sex", userSearch.getSex());
//            countQuery.setParameter("sex", userSearch.getSex());
//        }
//
//        dataQuery.setFirstResult(pageable.getOffset());
//        dataQuery.setMaxResults(pageable.getPageSize());
//        long totalSize = (long) countQuery.getSingleResult();
//        List<User> content = totalSize > (long) pageable.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
//        Page<User> pages = new PageImpl(content, pageable, totalSize);
//
//        return pages;
//    }
//}
