package com.cyra.exam.dao;

import com.cyra.exam.domain.Login;
import com.cyra.exam.domain.Login_;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
public class LoginHistoryDaoImpl implements LoginHistoryDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<Date> getUniqueDates() {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Date> query = builder.createQuery(Date.class);
        Root<Login> root = query.from(Login.class);
        query.select(root.get(Login_.loginTime)).orderBy(builder.asc(root.get(Login_.loginTime))).distinct(true);
        Query<Date> query1 = session.createQuery(query);
        return query1.getResultList();
    }
}
