package ru.epam.miniparking.controller.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Driver;
import ru.epam.miniparking.dto.DriverDTO;

@Component
public class DriverToDriverDtoConverter extends AbstractConverter<Driver, DriverDTO> {
    @Override
    protected DriverDTO convert(Driver driver) {
        return new DriverDTO(
                driver.getId(),
                driver.getName(),
                driver.getEmail(),
                driver.getOffice() != null ? driver.getOffice().getId() : null,
                driver.getSpot() != null ? driver.getSpot().getId() : null);
    }
}
