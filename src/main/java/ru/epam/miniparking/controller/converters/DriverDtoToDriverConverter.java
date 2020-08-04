package ru.epam.miniparking.controller.converters;

import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Driver;
import ru.epam.miniparking.dto.DriverDTO;
import ru.epam.miniparking.service.OfficeService;
import ru.epam.miniparking.service.SpotService;

@Component
@AllArgsConstructor
public class DriverDtoToDriverConverter extends AbstractConverter<DriverDTO, Driver> {
    private final OfficeService officeService;

    private final SpotService spotService;

    @Override
    protected Driver convert(DriverDTO driverDTO) {
        Driver driver = new Driver();
        driver.setId(driverDTO.getId());
        driver.setName(driverDTO.getName());
        driver.setEmail(driverDTO.getEmail());
        if (driverDTO.getOfficeId() != null) {
            driver.setOffice(officeService.findById(driverDTO.getOfficeId()));
        }
        if (driverDTO.getSpotId() != null) {
            driver.setSpot(spotService.findBySpotId(driverDTO.getSpotId()));
        }

        return driver;
    }
}
