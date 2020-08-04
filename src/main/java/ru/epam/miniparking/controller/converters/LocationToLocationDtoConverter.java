package ru.epam.miniparking.controller.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.BaseEntity;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.dto.LocationDTO;

import java.util.stream.Collectors;

@Component
public class LocationToLocationDtoConverter extends AbstractConverter<Location, LocationDTO> {
    @Override
    protected LocationDTO convert(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getLocationTitle(),
                location.getSpots() != null ?
                        location.getSpots().stream().map(BaseEntity::getId).collect(Collectors.toList()) : null,
                location.getOffice() != null ? location.getOffice().getId() : null,
                location.getCapacity());
    }
}
