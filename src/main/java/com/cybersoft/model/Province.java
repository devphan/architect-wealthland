package com.cybersoft.model;

import com.cybersoft.model.admin.Estate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private Set<Estate> estates;

    public Set<Estate> getEstates() {
        return estates;
    }

    public void setEstates(Set<Estate> estates) {
        this.estates = estates;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private Set<City> city;

    public Set<City> getCity() {
        return city;
    }

    public void setCity(Set<City> city) {
        this.city = city;
    }

    public Province() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
