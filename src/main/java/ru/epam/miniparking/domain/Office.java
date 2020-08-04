package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offices")
@EqualsAndHashCode
public class Office extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String officeTitle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "office")
    private List<Location> locations;
    @OneToMany(mappedBy = "office")
    private List<Driver> drivers;

    public List<Location> getLocations() {
        return locations == null ? new ArrayList<>() : locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Driver> getDrivers() {
        return drivers == null ? new ArrayList<>() : drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public String getOfficeTitle() {
        return officeTitle;
    }

    public void setOfficeTitle(String title) {
        this.officeTitle = title;
    }
}
