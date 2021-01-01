package com.cybersoft.model.admin;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "news")
@Where(clause = "deleted = 0")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title1;
    private String frontDescription;
    private String description;
    private String image;
    private String checkin;
    private String date;
    private String month;
    private short deleted;

    public News() {
    }

    @ManyToOne
    @JoinColumn(name = "estate_id")
    public Estate estate;

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category_News category_news;

    public Category_News getCategory_news() {
        return category_news;
    }

    public void setCategory_news(Category_News category_news) {
        this.category_news = category_news;
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

    public String getFrontDescription() {
        return frontDescription;
    }

    public void setFrontDescription(String frontDescription) {
        this.frontDescription = frontDescription;
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

    public String getCheckin() {
        return checkin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }
}

