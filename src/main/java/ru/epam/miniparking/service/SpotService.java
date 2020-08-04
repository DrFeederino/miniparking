package ru.epam.miniparking.service;

import org.springframework.stereotype.Service;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.domain.Spot;
import ru.epam.miniparking.exception.ConflictException;
import ru.epam.miniparking.exception.NotFoundException;
import ru.epam.miniparking.repo.SpotRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpotService {
    private final SpotRepo spotRepo;

    private final LocationService locationSevice;

    public SpotService(SpotRepo spotRepo, LocationService locationSevice) {
        this.spotRepo = spotRepo;
        this.locationSevice = locationSevice;
    }

    public static boolean capacityIsExceeded(long exist, long need) {
        return exist < need;
    }

    public Spot save(Spot newSpot) {
        if (spotTitleExists(newSpot)) {
            throw new ConflictException(String.format("Spot with name '%s' already exist", newSpot.getSpotTitle()));
        } else if (
                capacityIsExceeded(
                        newSpot.getLocation().getCapacity() - newSpot.getLocation().getSpots().size(),
                        1)
        ) {
            throw new ConflictException(String.format("No capacity left for location '%s", newSpot.getLocation().getLocationTitle()));
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
        List<Spot> locationSpots = location.getSpots();
        for (Spot spot : locationSpots) {
            if (Objects.equals(spot.getSpotTitle(), newSpot.getSpotTitle())) {
                return true;
            }
        }

        return false;
    }

    public Spot findBySpotId(Long id) {
        return spotRepo.findById(id).orElseThrow(() ->
                new NotFoundException(Spot.class.getSimpleName(), id));
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
        s.ifPresent(spot -> {
            if (spot.getDriver() != null) {
                List<Spot> availableSpots = findAvailable();
                if (availableSpots.isEmpty()) {
                    spot.getDriver().setSpot(null);
                } else {
                    availableSpots.forEach(spt -> {
                        if (spt.getLocation().getOffice().equals(spot.getLocation().getOffice())){
                            spot.getDriver().setSpot(spt);
                        }
                    });
                }
            }
        });
        s.orElseThrow(() -> new NotFoundException(Spot.class.getSimpleName(), id));

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
            throw new ConflictException("Spots with such names are already exists");
        } else if (
                capacityIsExceeded(
                        location.getCapacity() - location.getSpots().size(),
                        newSpotsNames.size()
                )
        ) {
            throw new ConflictException("Capacity is over");
        }

        List<Spot> newSpots = newSpotsNames.stream()
                .map(spotName -> new Spot(spotName, location)).collect(Collectors.toList());

        return spotRepo.saveAll(newSpots);
    }
}
