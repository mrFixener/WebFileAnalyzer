/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.dao.impl;

import com.webfileanalyzer.dao.FileStatisticDAO;
import com.webfileanalyzer.domain.FileStatistic;
import java.util.List;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;

/**
 *
 * @author Dimon
 */
@Repository
public class FileStatisticDAOImpl implements FileStatisticDAO{
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Override
    public FileStatistic getFileById(Long id) {
        /*Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(" from FileStatistic where id = :idr");
        q.setParameter("id", id);
        q.setMaxResults(MAX_ROWS);
        List<FileStatistic> lCreat = q.list();*/
        List<FileStatistic> lCreat = hibernateTemplate.findByNamedParam(" from FileStatistic where id = :id", "id", id);
        return lCreat == null || lCreat.isEmpty() ? new FileStatistic() : lCreat.get(0);
    }

    
    @Override
    public List<FileStatistic> getFilesByFileId(Long id) {
        /*Query q = hibernateTemplate.getSessionFactory().getCurrentSession().
                createQuery("select new FileStatistic(line, maxWord, minWord, lengthLine, avgWord) from FileStatistic t1 where fileId = :fileId");
        q.setParameter("fileId", id);
        q.setMaxResults(MAX_ROWS);
        return q.list();*/
        //List<FileStatistic> lCreat = getAllBetween(MAX_ROWS, 0);
        return hibernateTemplate.findByNamedParam(" from FileStatistic where fileId = :fileId order by id", "fileId", id);
        
    }
    
    /*private String getAllBetweenByQuery(){
        
    }*/
    @Override
    public List<FileStatistic> getAllBetween(Integer numRows, Integer shift) {
        /*Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(" from FileStatistic ");
        q.setFirstResult(shift); // modify this to adjust paging
        q.setMaxResults(numRows);
        return q.list();*/
        Session session = hibernateTemplate.getSessionFactory().openSession();
        return session.createSQLQuery(
                "select * from public.filestatistic limit :numRows offset :shift")
                .addEntity(FileStatistic.class)
                .setParameter("numRows", (numRows > MAX_ROWS)?MAX_ROWS:numRows)
                .setParameter("shift", shift).list();
    }

    @Override
    public void add(FileStatistic dbFile) {
        hibernateTemplate.save(dbFile);
        hibernateTemplate.flush();
    }

    @Override
    public void update(FileStatistic dbFile) {
        hibernateTemplate.saveOrUpdate(dbFile);
        hibernateTemplate.flush();
    }

    @Override
    public void delete(FileStatistic dbFile) {
        hibernateTemplate.delete(dbFile);
        hibernateTemplate.flush();
    }
    
}
