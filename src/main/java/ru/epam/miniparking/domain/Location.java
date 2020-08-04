package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "locations")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
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
}
