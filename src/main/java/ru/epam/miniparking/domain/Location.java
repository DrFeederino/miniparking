package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "locations")
@EqualsAndHashCode
public class Location extends BaseEntity {
    @Column(nullable = false)
    private String locationTitle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    private List<Spot> spots;
    @ManyToOne
    @JoinColumn(name="office_id", referencedColumnName = "id")
    private Office office;
    @Min(1)
    @Column(nullable = false)
    private long capacity;

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String title) {
        this.locationTitle = title;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
}
