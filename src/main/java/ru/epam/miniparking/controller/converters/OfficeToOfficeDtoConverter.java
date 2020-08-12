package ru.epam.miniparking.controller.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.epam.miniparking.domain.BaseEntity;
import ru.epam.miniparking.domain.Office;
import ru.epam.miniparking.dto.OfficeDTO;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class OfficeToOfficeDtoConverter extends AbstractConverter<Office, OfficeDTO> {
    @Override
    protected OfficeDTO convert(Office office) {
        return new OfficeDTO(
                office.getId(),
                office.getOfficeTitle(),
                office.getLocations() != null ?
                        office.getLocations()
                                .stream()
                                .map(BaseEntity::getId)
                                .collect(Collectors.toList())
                        : Collections.emptyList(),
                office.getDrivers() != null ?
                        office.getDrivers()
                                .stream()
                                .map(BaseEntity::getId)
                                .collect(Collectors.toList())
                        : Collections.emptyList());
    }
}
