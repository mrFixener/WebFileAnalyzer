/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.dao.impl;

import com.webfileanalyzer.dao.FileStatisticDAO;
import com.webfileanalyzer.domain.FileStatistic;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

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
        List<FileStatistic> lCreat = hibernateTemplate.findByNamedParam(" from FileStatistic where id = :id", "id", id);
        return lCreat == null || lCreat.isEmpty() ? new FileStatistic() : lCreat.get(0);
    }

    
    @Override
    public List<FileStatistic> getStatisticsByFileId(Long id) {
        return hibernateTemplate.getSessionFactory().getCurrentSession().
            createQuery(" from FileStatistic t1 where fileId = :fileId")
            .setParameter("fileId", id)
            .setMaxResults(MAX_ROWS)
            .list();
    }
    
    public List<FileStatistic> getStatisticsByFileId(Long id,  int from, int qty){
        List<FileStatistic> lStat = hibernateTemplate.getSessionFactory().getCurrentSession().
            createQuery(" from FileStatistic t1 where fileId = :fileId")
            .setParameter("fileId", id)
            .setFirstResult(from)
            .setMaxResults(qty)
            .list();
         return lStat;
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
