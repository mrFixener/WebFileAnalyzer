/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfileanalyzer.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Dimon
 */
@Entity
@Table(name = "Files")
public class Files implements Serializable{
    private Long id;
    private String fileName;
    private Date procDate = new Date();

    public static class FilesFieldsKey{
    public static final String ID = "ID", FILENAME = "FILENAME", PROCDATE = "PROCDATE";
    }
    public Files(){}
    public Files(Long id, String fileName, Date procDate) {
        this.id = id;
        this.fileName = fileName;
        this.procDate = procDate;
    }

    @Id 
    @Column(nullable = false, name = FilesFieldsKey.ID )
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = FilesFieldsKey.FILENAME, length = 300, nullable = true)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(nullable = false, name = FilesFieldsKey.PROCDATE)
    public Date getProcDate() {
        return procDate;
    }

    public void setProcDate(Date procDate) {
        this.procDate = procDate;
    }

    @Override
    public String toString() {
        return "Files{" + "id=" + id + ", fileName=" + fileName + ", procDate=" + procDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.fileName);
        hash = 67 * hash + Objects.hashCode(this.procDate);
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
        final Files other = (Files) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fileName, other.fileName)) {
            return false;
        }
        if (!Objects.equals(this.procDate, other.procDate)) {
            return false;
        }
        return true;
    }

}
