package ru.epam.miniparking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.epam.miniparking.domain.Driver;

import java.util.List;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
    List<Driver> findByNameContaining(String name);
}
