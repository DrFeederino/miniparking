package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offices")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Office extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String officeTitle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "office")
    private List<Location> locations;
    @OneToMany(mappedBy = "office")
    private List<Driver> drivers;
}
