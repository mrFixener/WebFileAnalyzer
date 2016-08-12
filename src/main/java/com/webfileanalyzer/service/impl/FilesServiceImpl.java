/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.service.impl;

import com.webfileanalyzer.dao.FilesDAO;
import com.webfileanalyzer.domain.Files;
import com.webfileanalyzer.service.FilesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dimon
 */
@Service
public class FilesServiceImpl implements FilesService{
    @Autowired
    private FilesDAO filesDAO;

    @Autowired
    private EhCacheCacheManager cacheManager;
      
    private static final String Files_LineMore_Then_B_Cache ="getFilesLineMoreThenBeetweenCache";
    private static final String Files_Service_Get_All_B_Cache ="filesServiceGetAllBeetweenCache";
    @Transactional(readOnly = true)
    @Override
    public Files getFileById(Long id) {
        return filesDAO.getFileById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Files> getAll() {
        return filesDAO.getAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void add(Files dbFile) {
        clearCache();
        filesDAO.add(dbFile);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void update(Files dbFile) {
        clearCache();
        filesDAO.update(dbFile);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(Files dbFile) {
        clearCache();
        filesDAO.delete(dbFile);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Files> getFilesLineMoreThen(Long numMoreThen) {
        return filesDAO.getFilesLineMoreThen(numMoreThen);
    }
    
    @Override
    public void clearCache(){
        cacheManager.getCacheManager().getCache(Files_LineMore_Then_B_Cache).removeAll();
        cacheManager.getCacheManager().getCache(Files_Service_Get_All_B_Cache).removeAll();
    }

    @Cacheable(value = Files_Service_Get_All_B_Cache)
    @Transactional(readOnly = true)
    @Override
    public List<Files> getAll(int from, int to) {
        return filesDAO.getAll(from, to);
    }

    @Cacheable(value = Files_LineMore_Then_B_Cache)
    @Transactional(readOnly = true)
    @Override
    public List<Files> getFilesLineMoreThen(Long numMoreThen, int from, int to) {
        return filesDAO.getFilesLineMoreThen(numMoreThen, from, to);
    }
}
