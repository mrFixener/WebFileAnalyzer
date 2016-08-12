/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.controller;

import com.fileanalyzer.util.ProcUserLines;
import static com.webfileanalyzer.controller.IndexController.IndexControllerKeys.*;
import com.webfileanalyzer.service.FileStatisticService;
import com.webfileanalyzer.service.FilesService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Dimon
 */
@Controller
public class IndexController {
    private static final Logger log = Logger.getLogger(IndexController.class);
    
    @Autowired
    private FilesService filesService;
    
    @Autowired
    private FileStatisticService fileStatisticService;

    public IndexController(){}
    
    public IndexController(FilesService filesService,
                           FileStatisticService fileStatisticService){//Injection for mock tests
        this.filesService         = filesService;
        this.fileStatisticService = fileStatisticService;
    }
    private Map<String, Object> errorViewMap(Exception ex){
        Map<String, Object> errMap = new HashMap();
        errMap.put(ERR_KEY, (Object) ex);
        return errMap;
    }
    
    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object>
      getFiles(@RequestParam(value = "from", required = true) String from,
               @RequestParam(value = "qty", required = true) String qty,
               @RequestParam(value = "moreThen", required = false) String moreThen) {
        try {
            Map<String, Object> resp = new HashMap();
            resp.put(FILES, moreThen != null 
                            ?    filesService.getFilesLineMoreThen(Long.valueOf(moreThen), 
                                                                   Integer.valueOf(from),
                                                                   Integer.valueOf(qty)) 
                            :    filesService.getAll(Integer.valueOf(from),Integer.valueOf(qty)));
            return new ResponseEntity<>((Object) resp, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>((Object)errorViewMap(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/procStat", method = RequestMethod.POST, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody
    String procStat(@RequestParam(value = "fileName", required = true) String fileName,
                    @RequestParam(value = "lines",    required = true) String lines) {
        try {
            log.info("/procStat : fileName="+fileName+"&lines="+lines);
            filesService.clearCache();
            return Long.toString(new ProcUserLines().proc(lines, fileName));
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/getFileStat/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody
    ResponseEntity<Object> getFileStat(@PathVariable("id")                            long value,
                                       @RequestParam(value = "from", required = true) String from,
                                       @RequestParam(value = "qty", required = true)  String qty) {
        try {
            Map<String, Object> resp = new HashMap();
            resp.put(FILE_STAT, fileStatisticService.getStatisticsByFileId(value,
                                                                           Integer.valueOf(from),
                                                                           Integer.valueOf(qty)));
            return new ResponseEntity<>((Object) resp, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>((Object)errorViewMap(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static class IndexControllerKeys{
        public static final String FILES ="files";
        public static final String FILE_STAT ="fileStat";
        public static final String ERR_KEY = "err";
    }
}
