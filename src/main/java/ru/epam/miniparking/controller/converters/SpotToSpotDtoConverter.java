package ru.epam.miniparking.controller.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.Spot;
import ru.epam.miniparking.dto.SpotDTO;

@Component
public class SpotToSpotDtoConverter extends AbstractConverter<Spot, SpotDTO> {
    @Override
    protected SpotDTO convert(Spot spot) {
        return new SpotDTO(
                spot.getId(),
                spot.getSpotTitle(),
                spot.getLocation() != null ? spot.getLocation().getId() : null,
                spot.getDriver() != null ? spot.getDriver().getId() : null);
    }
}
