/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.domain;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author Dimon
 */
@Entity
@Table(name = "FileStatistic")
public class FileStatistic implements Serializable{
    private Long id;
    private Files fileInDB;
    private Long fileId;
    private String line;
    private Long maxWord;
    private Long minWord;
    private Long lengthLine; 
    private Double avgWord;

    public static class FileStatisticKey{
    public static final String ID="ID", FILEID="FILEID", LINE="LINE", MAXWORD="MAXWORD", 
            MINWORD="MINWORD", LENGTHLINE="LENGTHLINE", AVGWORD="AVGWORD";
    }
    public FileStatistic(){}
    public FileStatistic(String line, Long maxWord, Long minWord, Long lengthLine, Double avgWord) {
        this.line = HtmlUtils.htmlUnescape(line);
        this.maxWord = maxWord;
        this.minWord = minWord;
        this.lengthLine = lengthLine;
        this.avgWord = avgWord;
    }

    public FileStatistic(Files fileInDB, String line, Long maxWord, Long minWord, Long lengthLine, Double avgWord) {
        this.line = line;
        this.maxWord = maxWord;
        this.minWord = minWord;
        this.lengthLine = lengthLine;
        this.avgWord = avgWord;
        this.fileInDB = fileInDB;
    }
    @JsonProperty("internalId")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fileInDB")
    public Files getFileInDB() {
        return fileInDB;
    }

    public void setFileInDB(Files fileInDB) {
        this.fileInDB = fileInDB;
    }
    
    @JsonProperty("externalId")
    @Column(nullable = true, name = "fileId")
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
    @Column(name = "line", length = 2048, nullable = true)
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = HtmlUtils.htmlUnescape(line);
    }

    @Column(nullable = true, name = "maxWord")
    public Long getMaxWord() {
        return maxWord;
    }

    public void setMaxWord(Long maxWord) {
        this.maxWord = maxWord;
    }

    @Column(nullable = true, name = "minWord")
    public Long getMinWord() {
        return minWord;
    }

    public void setMinWord(Long minWord) {
        this.minWord = minWord;
    }

    @Column(nullable = false, name = "lengthLine")
    public Long getLengthLine() {
        return lengthLine;
    }

    public void setLengthLine(Long lengthLine) {
        this.lengthLine = lengthLine;
    }

    @Column(nullable = true, name = "avgWord")
    public Double getAvgWord() {
        return avgWord;
    }

    public void setAvgWord(Double avgWord) {
        this.avgWord = avgWord;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.fileInDB);
        hash = 29 * hash + Objects.hashCode(this.fileId);
        hash = 29 * hash + Objects.hashCode(this.line);
        hash = 29 * hash + Objects.hashCode(this.maxWord);
        hash = 29 * hash + Objects.hashCode(this.minWord);
        hash = 29 * hash + Objects.hashCode(this.lengthLine);
        hash = 29 * hash + Objects.hashCode(this.avgWord);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileStatistic other = (FileStatistic) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fileInDB, other.fileInDB)) {
            return false;
        }
        if (!Objects.equals(this.fileId, other.fileId)) {
            return false;
        }
        if (!Objects.equals(this.line, other.line)) {
            return false;
        }
        if (!Objects.equals(this.maxWord, other.maxWord)) {
            return false;
        }
        if (!Objects.equals(this.minWord, other.minWord)) {
            return false;
        }
        if (!Objects.equals(this.lengthLine, other.lengthLine)) {
            return false;
        }
        if (!Objects.equals(this.avgWord, other.avgWord)) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {
        return "FileStatistic{" + "id=" + id + ", fileInDB=" + fileInDB + ", fileId=" + fileId + ", line=" + line + ", maxWord=" + maxWord + ", minWord=" + minWord + ", lengthLine=" + lengthLine + ", avgWord=" + avgWord + '}';
    }
    
}
