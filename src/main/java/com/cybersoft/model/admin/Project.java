package com.cybersoft.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "project")
@Where(clause = "deleted = 0")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String checkin;
    private String status;
    private String description;
    private String image;
    private short deleted;

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    public Set<Estate> estates;

    public Set<Estate> getEstates() {
        return estates;
    }

    public void setEstates(Set<Estate> estates) {
        this.estates = estates;
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

    public Project() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }
}
