package ru.epam.miniparking.service;

import org.springframework.stereotype.Service;
import ru.epam.miniparking.domain.Office;
import ru.epam.miniparking.exception.NotFoundException;
import ru.epam.miniparking.repo.OfficeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {
    private final OfficeRepo officeRepo;

    public OfficeService(OfficeRepo officeRepo) {
        this.officeRepo = officeRepo;
    }

    public Office save(Office newOffice) {
        return officeRepo.save(newOffice);
    }

    public Office findById(long id) {
        return officeRepo.findById(id).orElseThrow(() ->
                new NotFoundException(Office.class.getSimpleName(), id));
    }

    public List<Office> findByOfficeTitle(String title) {
        return officeRepo.findByOfficeTitleContaining(title);
    }

    public Office update(Office newOffice, Long id) {
        Office oldOffice = findById(id);
        oldOffice.setOfficeTitle(newOffice.getOfficeTitle());
        oldOffice.setLocations(newOffice.getLocations());
        oldOffice.setDrivers(newOffice.getDrivers());

        return save(oldOffice);
    }

    public void deleteById(long id) {
        Optional<Office> o = officeRepo.findById(id);
        o.ifPresent(office -> office.getDrivers().forEach(d -> {
            d.setSpot(null);
            d.setOffice(null);
        }));
        o.orElseThrow(() -> new NotFoundException(Office.class.getSimpleName(), id));

        officeRepo.deleteById(id);
    }

    public List<Office> findAll() {
        return officeRepo.findAll();
    }
}
