package ru.epam.miniparking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.domain.Spot;
import ru.epam.miniparking.exception.ConflictException;
import ru.epam.miniparking.exception.NotFoundException;
import ru.epam.miniparking.repo.SpotRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SpotService {
    private final SpotRepo spotRepo;

    private final LocationService locationSevice;

    private static final String NO_CAPACITY_LEFT = "No capacity left for location '%s";
    private static final String SPOT_EXISTS = "Spot with name '%s' already exist";
    private static final String SPOTS_EXIST = "Spots with such names are already exists";
    private static final String CAPACITY_EXCEEDED = "Capacity is over";

    public static boolean capacityIsExceeded(long exist, long need) {
        return exist < need;
    }

    public Spot save(Spot newSpot) {
        if (spotTitleExists(newSpot)) {
            throw new ConflictException(String.format(SPOT_EXISTS, newSpot.getSpotTitle()));
        } else if (
                capacityIsExceeded(
                        newSpot.getLocation().getCapacity() - newSpot.getLocation().getSpots().size(),
                        1)
        ) {
            throw new ConflictException(String.format(NO_CAPACITY_LEFT, newSpot.getLocation().getLocationTitle()));
        }
        return spotRepo.save(newSpot);
    }

    public Spot update(Spot newSpot, long id) {
        Spot oldSpot = findBySpotId(id);
        oldSpot.setSpotTitle(newSpot.getSpotTitle());
        oldSpot.setLocation(newSpot.getLocation());
        oldSpot.setDriver(newSpot.getDriver());

        return spotRepo.save(oldSpot);
    }

    private boolean spotTitleExists(Spot newSpot) {
        Location location = locationSevice.findById(newSpot.getLocation().getId());
        Set<String> locationSpots = location.getSpots()
                .stream()
                .map(Spot::getSpotTitle)
                .collect(Collectors.toSet());

        return locationSpots.contains(newSpot.getSpotTitle());
    }

    public Spot findBySpotId(Long id) {
        return spotRepo.findById(id).orElseThrow(() -> new NotFoundException(Spot.class.getSimpleName(), id));
    }

    public List<Spot> findBySpotTitle(String title) {
        return spotRepo.findBySpotTitleContaining(title);
    }

    public List<Spot> findAll() {
        return spotRepo.findAll();
    }

    public List<Spot> findAllById(List<Long> ids) {
        return spotRepo.findAllById(ids);
    }

    public List<Spot> findAvailable() {
        return spotRepo.findByDriverNull();
    }


    public void deleteById(Long id) {
        Optional<Spot> s = spotRepo.findById(id);
        if (!s.isPresent()) {
            throw new NotFoundException(Spot.class.getSimpleName(), id);
        }
        s.ifPresent(spot -> {
            if (spot.getDriver() != null) {
                List<Spot> availableSpots = findAvailable();
                if (availableSpots.isEmpty()) {
                    spot.getDriver().setSpot(null);
                } else {
                    availableSpots.forEach(spt -> {
                        if (spt.getLocation().getOffice().equals(spot.getLocation().getOffice())) {
                            spot.getDriver().setSpot(spt);
                        }
                    });
                }
            }
        });
        spotRepo.deleteById(id);
    }

    public List<Spot> findBySpotTitleAndLocation(List<String> newSpots, Location location) {
        List<Spot> spots = new ArrayList<>();
        for (String spot : newSpots) {
            spotRepo.findBySpotTitleAndLocation(spot, location).ifPresent(spots::add);
        }

        return spots;
    }

    public static String createName(String name, long i) {
        return name + " " + i;
    }

    public List<Spot> createSpotsInRange(Long locationId, String name, int from, int to) {
        List<String> newSpotsNames = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            newSpotsNames.add(createName(name, i));
        }

        Location location = locationSevice.findById(locationId);

        if (!findBySpotTitleAndLocation(newSpotsNames, location).isEmpty()) {
            throw new ConflictException(SPOTS_EXIST);
        } else if (
                capacityIsExceeded(
                        location.getCapacity() - location.getSpots().size(),
                        newSpotsNames.size()
                )
        ) {
            throw new ConflictException(CAPACITY_EXCEEDED);
        }

        List<Spot> newSpots = newSpotsNames.stream()
                .map(spotName -> new Spot(spotName, location)).collect(Collectors.toList());

        return spotRepo.saveAll(newSpots);
    }
}
