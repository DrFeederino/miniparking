package ru.epam.miniparking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.epam.miniparking.domain.Location;

import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long> {
    List<Location> findByLocationTitleContaining(String name);
}
