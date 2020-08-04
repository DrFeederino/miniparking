package ru.epam.miniparking.controller.converters;

import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Office;
import ru.epam.miniparking.dto.OfficeDTO;
import ru.epam.miniparking.service.DriverService;
import ru.epam.miniparking.service.LocationService;

@Component
@AllArgsConstructor
public class OfficeDtoToOfficeConverter extends AbstractConverter<OfficeDTO, Office> {
    private final LocationService locationService;

    private final DriverService driverService;

    @Override
    protected Office convert(OfficeDTO officeDTO) {
        Office office = new Office();
        office.setId(officeDTO.getId());
        office.setOfficeTitle(officeDTO.getOfficeTitle());
        if (officeDTO.getLocationIds() != null) {
            office.setLocations(locationService.findAllById(officeDTO.getLocationIds()));
        }
        if (officeDTO.getDriverIds() != null) {
            office.setDrivers(driverService.findAllById(officeDTO.getDriverIds()));
        }

        return office;
    }
}

