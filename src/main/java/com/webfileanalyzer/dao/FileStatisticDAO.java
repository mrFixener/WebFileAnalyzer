/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.dao;

import com.webfileanalyzer.domain.FileStatistic;
import java.util.List;

/**
 *
 * @author Dimon
 */
public interface FileStatisticDAO {
    public static final int MAX_ROWS = 10;
    public FileStatistic getFileById(Long id);

    public List<FileStatistic> getFilesByFileId(Long id);
            
    public List<FileStatistic> getAllBetween(Integer start, Integer end);

    public void add(FileStatistic dbFile);

    public void update(FileStatistic dbFile);

    public void delete(FileStatistic dbFile);
}
