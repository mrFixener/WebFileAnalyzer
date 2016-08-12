/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.testservice;

import com.webfileanalyzer.domain.FileStatistic;
import com.webfileanalyzer.domain.Files;
import com.webfileanalyzer.service.FileStatisticService;
import com.webfileanalyzer.service.FilesService;
import java.util.Date;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Dimon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/applicationContextTest.xml")
public class FileStatisticServiceTest {
    private static final Logger log = Logger.getLogger(FileStatisticServiceTest.class);
    @Autowired
    FileStatisticService fileStatisticService;
    @Autowired
    FilesService fileService;
    
    public FileStatisticServiceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void addDeleteTest(){
        FileStatistic fs=  new FileStatistic("Line unit test", 5l, 45l, 2l, 6.8);
        fileStatisticService.add(fs);
        fileStatisticService.delete(fs);
    }
    
    @Test
    public void getFileByIdTest() {
        Files f = new Files();
        f.setFileName("junit test");
        f.setProcDate(new Date());
        fileService.add(f);
        assertNotNull(f.getId());
        FileStatistic fs=  new FileStatistic("Line unit test1", 5l, 45l, 2l, 6.8);
        fs.setFileId(f.getId());
        fileStatisticService.add(fs);
        FileStatistic fs2=  new FileStatistic("Line unit test2", 5l, 42l, 2l, 4.8);
        fs2.setFileId(f.getId());
        fileStatisticService.add(fs2);
        FileStatistic fs3=  new FileStatistic("Line unit test2", 5l, 0l, 2l, 3.8);
        fs3.setFileId(f.getId());
        fileStatisticService.add(fs3);
        
        assertEquals("Number of inserted params must be same with retrieved.", 3, 
                fileStatisticService.getStatisticsByFileId(new Long(f.getId())).size());
        
        assertNotNull(fileStatisticService.getStatisticsByFileId(new Long(f.getId())).get(0).getFileId());
        
        fileStatisticService.delete(fs);
        fileStatisticService.delete(fs2);
        fileStatisticService.delete(fs3);
        fileService.delete(f);
        
    }
}
