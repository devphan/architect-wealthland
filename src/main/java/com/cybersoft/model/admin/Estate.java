package com.cybersoft.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cybersoft.model.City;
import com.cybersoft.model.Province;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "estates")
@Where(clause = "deleted = 0")
public class Estate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String checkin;
    private Long price;
    private Long bedroom;
    private Long bathroom;
    private Long area;
    private String description;
    private String frontDescription;
    private String image;
    private boolean isHot;
    private String direction;
    private Integer status;
    private short deleted;

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project project;

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;



    @ManyToOne
    @JoinColumn(name = "bank_id")
    public Bank bank;

    @ManyToOne
    @JoinColumn(name = "province_id")
    public Province province;

    @ManyToOne
    @JoinColumn(name = "city_id")
    public City city;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "estate")
    public Set<News> news;


    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    public Estate() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }


    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBedroom() {
        return bedroom;
    }

    public void setBedroom(Long bedroom) {
        this.bedroom = bedroom;
    }


    public Long getBathroom() {
        return bathroom;
    }

    public void setBathroom(Long bathroom) {
        this.bathroom = bathroom;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFrontDescription() {
        return frontDescription;
    }

    public void setFrontDescription(String frontDescription) {
        this.frontDescription = frontDescription;
    }
}
