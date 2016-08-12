/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.service;

import com.webfileanalyzer.domain.FileStatistic;
import java.util.List;

/**
 *
 * @author Dimon
 */
public interface FileStatisticService {
    public FileStatistic getFileById(Long id);

    public List<FileStatistic> getStatisticsByFileId(Long id);
    
    public List<FileStatistic> getStatisticsByFileId(Long id,  int from, int qty);
    
    public void add(FileStatistic dbFile);

    public void update(FileStatistic dbFile);

    public void delete(FileStatistic dbFile);
}
