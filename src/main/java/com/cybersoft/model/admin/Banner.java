package com.cybersoft.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "banners")
@Where(clause = "deleted = 0")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title1;
    private String title2;
    private String description;
    private String image;
    private short deleted;

    public Banner() {
    }

    @Transient
    @JsonIgnore
    private CommonsMultipartFile[] fileImage;
    public CommonsMultipartFile[] getFileImage() {
        return fileImage;
    }
    public void setFileImage(CommonsMultipartFile[] fileImage) {
        this.fileImage = fileImage;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }
}

