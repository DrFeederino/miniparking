package ru.epam.miniparking.controller.converters;

import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Spot;
import ru.epam.miniparking.dto.SpotDTO;
import ru.epam.miniparking.service.DriverService;
import ru.epam.miniparking.service.LocationService;

@Component
@AllArgsConstructor
public class SpotDtoToSpotConverter extends AbstractConverter<SpotDTO, Spot> {
    private final DriverService driverService;

    private final LocationService locationSevice;

    @Override
    protected Spot convert(SpotDTO spotDTO) {
        Spot spot = new Spot();
        spot.setId(spotDTO.getId());
        spot.setSpotTitle(spotDTO.getSpotTitle());
        if (spotDTO.getDriverId() != null) {
            spot.setDriver(driverService.findById(spotDTO.getDriverId()));
        }
        if (spotDTO.getLocationId() != null) {
            spot.setLocation(locationSevice.findById(spotDTO.getLocationId()));
        }

        return spot;
    }
}
