package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "drivers")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Driver extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    @Column(nullable = false, unique = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Office office;
    @OneToOne
    @JoinColumn(name = "spot_id", referencedColumnName = "id")
    private Spot spot;
}
