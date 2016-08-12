/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.testcontroller;

import com.webfileanalyzer.controller.IndexController;
import com.webfileanalyzer.service.FileStatisticService;
import com.webfileanalyzer.service.FilesService;
import static org.hamcrest.Matchers.hasSize;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 *
 * @author Dimon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/applicationContextTest.xml")
public class IndexControllerTest {
    @Autowired
    private FilesService filesService;
    @Autowired
    private FileStatisticService fileStatisticService;
    
    private MockMvc mockMvc;
    public IndexControllerTest() {
    }
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new IndexController(filesService, fileStatisticService)).build();
    }
    
    @After
    public void tearDown() {
    }
    
    @Ignore
    @Test
    public void getFileStatTestResp200() throws Exception{
        this.mockMvc.perform(get("/getFileStat/149").param("from", "0").param("qty", "100")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @Ignore
    @Test
    public void getFileStatTestAnswer() throws Exception{
        this.mockMvc.perform(get("/getFileStat/149").param("from", "25").param("qty", "100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileStat[*]", hasSize(25)));
    }
}
