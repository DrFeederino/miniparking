package ru.epam.miniparking.controller.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Driver;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.dto.LocationDTO;
import ru.epam.miniparking.dto.SpotDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationToLocationDtoConverter extends AbstractConverter<Location, LocationDTO> {
    @Override
    protected LocationDTO convert(Location location) {
        List<SpotDTO> spots = location.getSpots() != null ?
                location.getSpots()
                        .stream()
                        .map(spot -> new SpotDTO(
                                spot.getId(),
                                spot.getSpotTitle(),
                                spot.getLocation().getId(),
                                getIdOrNull(spot.getDriver())))
                        .sorted(Comparator.comparingLong(SpotDTO::getId))
                        .collect(Collectors.toList())
                : null;
        return new LocationDTO(
                location.getId(),
                location.getLocationTitle(),
                spots,
                location.getOffice() != null ? location.getOffice().getId() : null,
                location.getCapacity());
    }

    private Long getIdOrNull(Driver driver) {
        return driver != null ? driver.getId() : null;
    }
}
