package ru.epam.miniparking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.epam.miniparking.domain.Driver;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.domain.Spot;
import ru.epam.miniparking.exception.NotFoundException;
import ru.epam.miniparking.repo.DriverRepo;
import ru.epam.miniparking.repo.LocationRepo;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LocationService {
    private final LocationRepo locationRepo;
    private final OfficeService officeService;
    private final DriverRepo driverRepo;

    public List<Location> findAll() {
        return locationRepo.findAll();
    }

    public List<Location> findAllById(List<Long> ids) {
        return locationRepo.findAllById(ids);
    }

    public Location findById(long id) {
        return locationRepo.findById(id).orElseThrow(() -> new NotFoundException(Location.class.getSimpleName(), id));
    }

    public List<Location> findByName(String name) {
        return locationRepo.findByLocationTitleContaining(name);
    }

    public Location save(Location location) {
        return locationRepo.save(location);
    }

    public Location update(Location location, Long id) {
        Location location1 = findById(id);
        location1.setCapacity(location.getCapacity());
        location1.setLocationTitle(location.getLocationTitle());
        location1.setOffice(location.getOffice());
        location1.setSpots(location.getSpots());

        return save(location);
    }

    public void deleteById(long id) {
        Location location = findById(id);
        delete(location);
        reassignDrivers(location);
    }

    @Transactional(TxType.REQUIRES_NEW)
    private void delete(Location location) {
        location.getSpots().forEach(s -> {
            if (s.getDriver() != null) {
                s.getDriver().setSpot(null);
            }
        });
        locationRepo.delete(location);
        locationRepo.flush();
    }

    private void reassignDrivers(Location deletedLocation) {
        List<Location> locations = officeService.findById(deletedLocation.getOffice().getId()).getLocations();

        List<Spot> availableSpots = locations.stream()
                .filter(l -> !l.getId().equals(deletedLocation.getId()))
                .map(l -> l.getSpots().stream()
                        .filter(Spot::isAvailable)
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<Driver> driversToTransfer = deletedLocation.getSpots().stream()
                .filter(s -> s.getDriver() != null)
                .map(Spot::getDriver)
                .collect(Collectors.toList());

        driversToTransfer.forEach(d -> {
            if (availableSpots.isEmpty()) {
                return;
            }
            Spot newSpot = availableSpots.remove(availableSpots.size() - 1);
            d.setSpot(newSpot);
            newSpot.setDriver(d);
            driverRepo.save(d);
        });
    }

}
