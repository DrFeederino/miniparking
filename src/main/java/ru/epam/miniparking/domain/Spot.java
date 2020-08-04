package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "spots")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
public class Spot extends BaseEntity {
    @Column(nullable = false)
    private String spotTitle;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @OneToOne(mappedBy = "spot")
    private Driver driver;

    public Spot(String spotTitle, Location location) {
        this.spotTitle = spotTitle;
        this.location = location;
    }

    public boolean isAvailable() {
        return driver == null;
    }
}
