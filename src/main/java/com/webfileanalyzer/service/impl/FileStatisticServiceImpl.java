/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.service.impl;

import com.webfileanalyzer.dao.FileStatisticDAO;
import com.webfileanalyzer.domain.FileStatistic;
import com.webfileanalyzer.service.FileStatisticService;
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
public class FileStatisticServiceImpl implements FileStatisticService{
    @Autowired 
    private FileStatisticDAO fileStatisticDAO;

    @Transactional(readOnly = true)
    @Override
    public FileStatistic getFileById(Long id) {
        return fileStatisticDAO.getFileById(id);
    }

    @Autowired
    private EhCacheCacheManager cacheManager;
    
    private static final String Files_By_File_Id_Cache = "getFilesByFileIdCache";

    @Cacheable(value = Files_By_File_Id_Cache)
    @Transactional(readOnly = true)
    @Override
    public List<FileStatistic> getFilesByFileId(Long id) {
       List<FileStatistic> startPrimtableQeur = fileStatisticDAO.getFilesByFileId(id);
       if(startPrimtableQeur != null && !startPrimtableQeur.isEmpty())
           startPrimtableQeur.get(0).getId();
        return fileStatisticDAO.getFilesByFileId(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<FileStatistic> getAllBetween(Integer start, Integer end) {
        return fileStatisticDAO.getAllBetween(start, end);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void add(FileStatistic dbFile) {
        clearCache();
        fileStatisticDAO.add(dbFile);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void update(FileStatistic dbFile) {
        clearCache();
        fileStatisticDAO.delete(dbFile);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(FileStatistic dbFile) {
        clearCache();
        fileStatisticDAO.delete(dbFile);
    }  
    
    private void clearCache() {
        cacheManager.getCacheManager().getCache(Files_By_File_Id_Cache).removeAll();
    }
}
