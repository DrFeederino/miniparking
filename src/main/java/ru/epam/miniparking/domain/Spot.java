package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "spots")
@EqualsAndHashCode
public class Spot extends BaseEntity {
    @Column(nullable = false)
    private String spotTitle;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @OneToOne(mappedBy = "spot")
    private Driver driver;

    public Spot() {
    }

    public Spot(String spotTitle, Location location) {
        this.spotTitle = spotTitle;
        this.location = location;
    }

    public String getSpotTitle() {
        return spotTitle;
    }

    public void setSpotTitle(String spotTitle) {
        this.spotTitle = spotTitle;
    }

    public boolean isAvailable() {
        return driver == null;
    }

    public boolean isNotAvailable() {
        return !isAvailable();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
