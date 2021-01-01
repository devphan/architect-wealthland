package com.cybersoft.model;


import com.cybersoft.model.admin.Estate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "citys")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public City() {
    }


    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private Set<Estate> estates;

    public Set<Estate> getEstates() {
        return estates;
    }

    public void setEstates(Set<Estate> estates) {
        this.estates = estates;
    }

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
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
}
