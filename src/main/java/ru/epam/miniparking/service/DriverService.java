package ru.epam.miniparking.service;

import org.springframework.stereotype.Service;
import ru.epam.miniparking.domain.Driver;
import ru.epam.miniparking.exception.NotFoundException;
import ru.epam.miniparking.repo.DriverRepo;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    private final DriverRepo driverRepo;

    public DriverService(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    public List<Driver> findAll() {
        return driverRepo.findAll();
    }

    public List<Driver> findAllById(List<Long> ids) {
        return driverRepo.findAllById(ids);
    }

    public Driver findById(long id) {
        return driverRepo.findById(id).orElseThrow(() -> new NotFoundException(Driver.class.getSimpleName(), id));
    }

    public List<Driver> findByName(String name) {
        return driverRepo.findByNameContaining(name);
    }

    public Driver save(Driver driver) {
        return driverRepo.save(driver);
    }

    public Driver updateDriver(Driver driver, Long id) {
        Driver driverFromRepo = findById(id);
        driverFromRepo.setEmail(driver.getEmail());
        driverFromRepo.setName(driver.getName());
        driverFromRepo.setOffice(driver.getOffice());
        driverFromRepo.setSpot(driver.getSpot());

        return driverRepo.save(driverFromRepo);
    }

    public void deleteById(long id) {
        Optional<Driver> d = driverRepo.findById(id);
        d.ifPresent(driver -> {
            if (driver.getSpot() != null) {
                driver.getSpot().setDriver(null);
            }
        });
        d.orElseThrow(() -> new NotFoundException(Driver.class.getSimpleName(), id));

        driverRepo.deleteById(id);
    }
}
