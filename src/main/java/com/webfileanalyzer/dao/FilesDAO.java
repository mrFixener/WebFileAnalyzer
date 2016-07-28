/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.dao;

import com.webfileanalyzer.domain.Files;
import java.util.List;

/**
 *
 * @author Dimon
 */
public interface FilesDAO {
    public Files getFileById(Long id);
    
    public List<Files> getAll();
    
    public void add(Files dbFile);

    public void update(Files dbFile);

    public void delete(Files dbFile);
    
    public List<Files> getFilesLineMoreThen(Long numMoreThen);
}
