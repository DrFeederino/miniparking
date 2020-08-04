package ru.epam.miniparking.controller.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.dto.LocationDTO;
import ru.epam.miniparking.service.OfficeService;
import ru.epam.miniparking.service.SpotService;

@Component
public class LocationDtoToLocationConverter extends AbstractConverter<LocationDTO, Location> {
    private final OfficeService officeService;

    private final SpotService spotService;

    public LocationDtoToLocationConverter(OfficeService officeService, SpotService spotService) {
        this.officeService = officeService;
        this.spotService = spotService;
    }

    @Override
    protected Location convert(LocationDTO locationDTO) {
        Location location = new Location();
        location.setId(locationDTO.getId());
        location.setLocationTitle(locationDTO.getLocationTitle());
        location.setCapacity(locationDTO.getCapacity());
        if (locationDTO.getOfficeId() != null) {
            location.setOffice(officeService.findById(locationDTO.getOfficeId()));
        }
        if (locationDTO.getSpotIds() != null) {
            location.setSpots(spotService.findAllById(locationDTO.getSpotIds()));
        }

        return location;
    }
}
