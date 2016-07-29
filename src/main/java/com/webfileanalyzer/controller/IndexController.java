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
 //import javax.annotation.Resource;

/**
 *
 * @author Dimon
 */
@Controller
public class IndexController {
    private static final Logger log = Logger.getLogger(IndexController.class);
    public static final String ERR_KEY = "err";
    
    @Autowired
    private FilesService filesService;
    
    @Autowired
    private FileStatisticService fileStatisticService;

    private Map<String, Object> resp = new HashMap();
   
    private Map<String, Object> errorViewMap(Exception ex){
        Map<String, Object> errMap = new HashMap();
        errMap.put(ERR_KEY, (Object) ex);
        return errMap;
    }
    
    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object>
      getFiles(@RequestParam(value = "moreThen", required = false) String moreThen) {
        try {
            resp.put(FILES, moreThen != null 
                            ?    filesService.getFilesLineMoreThen(Long.valueOf(moreThen)) 
                            :    filesService.getAll());
            return new ResponseEntity<>((Object) resp, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>((Object)errorViewMap(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/procStat", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String procStat(@RequestParam(value = "fileName", required = true) String fileName,
                                    @RequestParam(value = "lines",    required = true) String lines) {
        try {
            log.info("/procStat : fileName="+fileName+"&lines="+lines);
            return Long.toString(new ProcUserLines().proc(lines, fileName));
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/getFileStat/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> getFileStat(@PathVariable("id") long value) {
        try {
            resp.put(FILE_STAT, fileStatisticService.getFilesByFileId(value));
            return new ResponseEntity<>((Object) resp, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>((Object)errorViewMap(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getFileStatBetween", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> getFileStatBetween(@RequestParam(value = "numRows", required = true) Integer numRows,
            @RequestParam(value = "shift", required = true) Integer shift) {
        try {
            return new ResponseEntity<>((Object) fileStatisticService.getAllBetween(shift, shift), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>((Object)errorViewMap(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static class IndexControllerKeys{
        public static final String FILES ="files";
        public static final String FILE_STAT ="fileStat";
    }
}
