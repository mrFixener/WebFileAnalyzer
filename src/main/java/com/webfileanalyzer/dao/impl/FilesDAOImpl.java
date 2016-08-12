/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.dao.impl;

import com.webfileanalyzer.dao.FilesDAO;
import static com.webfileanalyzer.dao.impl.FileStatisticDAOImpl.MAX_ROWS;
import com.webfileanalyzer.domain.Files;
import java.util.List;
import org.hibernate.Query;

import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dimon
 */
@Repository
public class FilesDAOImpl implements FilesDAO{
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Override
    public Files getFileById(Long id) {
        List<Files> lCreat = hibernateTemplate.findByNamedParam(" from Files where id = :id", "id", id);
        return lCreat == null || lCreat.isEmpty() ? new Files() : lCreat.get(0);
    }

    @Override
    public List<Files> getAll() {
        Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(" from Files order by procDate desc");
        return q.setMaxResults(MAX_ROWS).list();
    }

    @Override
    public void add(Files dbFile) {
        hibernateTemplate.save(dbFile);
        hibernateTemplate.flush();
    }

    @Override
    public void update(Files dbFile) {
        hibernateTemplate.saveOrUpdate(dbFile);
        hibernateTemplate.flush();
    }

    @Override
    public void delete(Files dbFile) {
        hibernateTemplate.delete(dbFile);
        hibernateTemplate.flush();
    }

    @Override
    public List<Files> getFilesLineMoreThen(Long numMoreThen) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        return session.createSQLQuery("select t1.* from public.files as t1 "
                + "join (select count(*) as \"CNT\", fileid  "
                + "from public.filestatistic group by FILEID)  as t2 on t2.fileid=t1.id where t2.\"CNT\" > :numMoreThen")
                .addEntity(Files.class)
                .setParameter("numMoreThen", numMoreThen)
                .setMaxResults(MAX_ROWS).list();
    }

    @Override
    public List<Files> getAll(int from, int qty) {
        Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(" from Files order by procDate desc");
        return q.setFirstResult(from).setMaxResults(qty).list();
    }

    @Override
    public List<Files> getFilesLineMoreThen(Long numMoreThen, int from, int qty) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        return session.createSQLQuery("select t1.* from public.files as t1 "
                + "join (select count(*) as \"CNT\", fileid  "
                + "from public.filestatistic group by FILEID)  as t2 on t2.fileid=t1.id where t2.\"CNT\" > :numMoreThen")
                .addEntity(Files.class)
        .setParameter("numMoreThen", numMoreThen)
        .setFirstResult(from)
        .setMaxResults(qty).list();
    }
    
}
