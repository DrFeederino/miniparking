package ru.epam.miniparking.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "drivers")
@EqualsAndHashCode
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
}
