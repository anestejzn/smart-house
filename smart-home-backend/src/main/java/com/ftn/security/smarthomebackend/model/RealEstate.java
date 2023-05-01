package com.ftn.security.smarthomebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="real_estate")
@Getter
@Setter
@NoArgsConstructor
public class RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="name", nullable = false)
    protected String name;

    @Column(name="sq_meters", nullable = false)
    protected Integer sqMeters;

    @Column(name="city", nullable = false)
    protected String city;

    @Column(name="street", nullable = false)
    protected String street;

    @Column(name="street_num", nullable = false)
    protected String streetNum;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    protected RegularUser owner;

    @ManyToMany
    @JoinTable(
            name = "real_estate_tenant",
            joinColumns = @JoinColumn(name = "real_estate_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    protected List<RegularUser> tenants = new LinkedList<>();

    public RealEstate(String name,
                      Integer sqMeters,
                      String city,
                      String street,
                      String streetNum,
                      RegularUser owner,
                      List<RegularUser> tenants
    ) {
        this.name = name;
        this.sqMeters = sqMeters;
        this.city = city;
        this.street = street;
        this.streetNum = streetNum;
        this.owner = owner;
        this.tenants = tenants;
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

    public Integer getSqMeters() {
        return sqMeters;
    }

    public void setSqMeters(Integer sqMeters) {
        this.sqMeters = sqMeters;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public RegularUser getOwner() {
        return owner;
    }

    public void setOwner(RegularUser owner) {
        this.owner = owner;
    }

    public List<RegularUser> getTenants() {
        return tenants;
    }

    public void setTenants(List<RegularUser> tenants) {
        this.tenants = tenants;
    }
}
