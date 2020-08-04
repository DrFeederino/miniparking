package ru.epam.miniparking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.domain.Spot;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotRepo extends JpaRepository<Spot, Long> {
    List<Spot> findBySpotTitleContaining(String title);

    List<Spot> findByDriverNull();

    Optional<Spot> findBySpotTitleAndLocation(String spot, Location location);
}
