package ru.epam.miniparking.controller.converters;

import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.dto.BaseDto;
import ru.epam.miniparking.dto.LocationDTO;
import ru.epam.miniparking.service.OfficeService;
import ru.epam.miniparking.service.SpotService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class LocationDtoToLocationConverter extends AbstractConverter<LocationDTO, Location> {
    private final OfficeService officeService;

    private final SpotService spotService;

    @Override
    protected Location convert(LocationDTO locationDTO) {
        Location location = new Location();
        location.setId(locationDTO.getId());
        location.setLocationTitle(locationDTO.getLocationTitle());
        location.setCapacity(locationDTO.getCapacity());
        if (locationDTO.getOfficeId() != null) {
            location.setOffice(officeService.findById(locationDTO.getOfficeId()));
        }
        if (locationDTO.getSpots() != null) {
            List<Long> ids = locationDTO.getSpots().stream().map(BaseDto::getId).collect(toList());
            location.setSpots(spotService.findAllById(ids));
        }

        return location;
    }
}
