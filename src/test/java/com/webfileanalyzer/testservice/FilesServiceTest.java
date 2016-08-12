/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.testservice;

import com.webfileanalyzer.domain.FileStatistic;
import com.webfileanalyzer.service.FilesService;
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
import com.webfileanalyzer.service.FileStatisticService;
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
    @Autowired
    FileStatisticService fileStatisticService;
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
    
    @Test
    public void getAllTest() {
        Files f = new Files();
        f.setFileName("junit test");
        f.setProcDate(new Date());
        fileService.add(f);
        Files f2 = new Files();
        f2.setFileName("junit test2");
        f2.setProcDate(new Date());
        fileService.add(f2);
        assertEquals("Result Set must be 2 rows.",2, fileService.getAll(0, 2).size());
        fileService.delete(f2);
        fileService.delete(f);
    }

    @Ignore
    @Test
    public void getFilesLineMoreThenTest() {
        Files f = new Files();
        f.setFileName("junit test");
        f.setProcDate(new Date());
        fileService.add(f);
        FileStatistic fs=  new FileStatistic("Line unit test1", 5l, 45l, 2l, 6.8);
        fs.setFileId(f.getId());
        fileStatisticService.add(fs);
        FileStatistic fs2=  new FileStatistic("Line unit test2", 5l, 42l, 2l, 4.8);
        fs2.setFileId(f.getId());
        fileStatisticService.add(fs2);
        
        Files f1 = new Files();
        f1.setFileName("junit test");
        f1.setProcDate(new Date());
        fileService.add(f1);
        FileStatistic fs3=  new FileStatistic("Line unit test3", 5l, 45l, 2l, 6.8);
        fs3.setFileId(f1.getId());
        fileStatisticService.add(fs3);
        FileStatistic fs4=  new FileStatistic("Line unit test4", 5l, 42l, 2l, 4.8);
        fs4.setFileId(f1.getId());
        fileStatisticService.add(fs4);
        
        Files f2 = new Files();
        f2.setFileName("junit test");
        f2.setProcDate(new Date());
        fileService.add(f2);
        FileStatistic fs5=  new FileStatistic("Line unit test5", 5l, 45l, 2l, 6.8);
        fs5.setFileId(f2.getId());
        fileStatisticService.add(fs5);
        FileStatistic fs6=  new FileStatistic("Line unit test6", 5l, 42l, 2l, 4.8);
        fs6.setFileId(f2.getId());
        fileStatisticService.add(fs6);
        assertEquals("Result Set must be 3 rows.",3,fileService.getFilesLineMoreThen(1l, 0, 3).size());
        
        fileService.delete(f);
        fileStatisticService.delete(fs);
        fileStatisticService.delete(fs2);
        fileService.delete(f1);
        fileStatisticService.delete(fs3);
        fileStatisticService.delete(fs4);
        fileService.delete(f2);
        fileStatisticService.delete(fs5);
        fileStatisticService.delete(fs6);
    }
}
