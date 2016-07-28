/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.testservice;

import com.webfileanalyzer.service.FilesService;
import java.io.File;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.webfileanalyzer.domain.Files;
import org.apache.log4j.Logger;
import org.junit.Ignore;
/**
 *
 * @author Dimon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/applicationContextTest.xml")
public class FilesServiceTest {
    private static final Logger log = Logger.getLogger(FilesServiceTest.class);
    @Autowired
    private FilesService fileService;
    public FilesServiceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void addTest(){
        Files f = new Files();
        f.setFileName("junit test");
        f.setProcDate(new Date());
        fileService.add(f);
        assertNotNull("It can't be null",f.getId());
        fileService.delete(f);
    }
    
    @Test
    public void getFilesLineMoreThentest(){
        for(Files f:fileService.getFilesLineMoreThen(new Long(1)))
            log.info(f);
    }
}
