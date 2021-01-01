package com.cybersoft.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categorys")
@Where(clause = "deleted = 0")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private short deleted;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    public Set<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    public Set<Estate> estates;

    public Set<Estate> getEstates() {
        return estates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEstates(Set<Estate> estates) {
        this.estates = estates;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
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

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public Category() {
    }

}

