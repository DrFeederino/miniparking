package ru.epam.miniparking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.epam.miniparking.domain.Location;
import ru.epam.miniparking.domain.NonIdentifiable;
import ru.epam.miniparking.dto.LocationDTO;
import ru.epam.miniparking.service.LocationService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Api
@Transactional
@RestController
@RequestMapping("/locations")
@CrossOrigin("http://localhost:3000")
public class LocationController {
    private final LocationService locationSevice;

    private final ModelMapper mapper;

    public LocationController(LocationService locationSevice, ModelMapper mapper) {
        this.locationSevice = locationSevice;
        this.mapper = mapper;
    }

    @ApiOperation("Returns all locations")
    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationSevice.findAll().stream()
                .map(m -> mapper.map(m, LocationDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Returns location by ID")
    @GetMapping("/{id}")
    public LocationDTO getById(@PathVariable long id) {
        return mapper.map(locationSevice.findById(id), LocationDTO.class);
    }

    @ApiOperation("Returns location by name")
    @GetMapping(params = {"name"})
    public List<LocationDTO> getByName(@RequestParam String name) {
        return locationSevice.findByName(name).stream()
                .map(m -> mapper.map(m, LocationDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Creates new location")
    @PostMapping
    public LocationDTO postCreateNewLocation(@NonIdentifiable LocationDTO location) {
        return mapper.map(locationSevice.save(mapper.map(location, Location.class)), LocationDTO.class);
    }

    @ApiOperation("Updates location")
    @PutMapping("/{id}")
    public LocationDTO update(@RequestBody LocationDTO location, @PathVariable Long id) {
        return mapper.map(locationSevice.update(mapper.map(location, Location.class), id), LocationDTO.class);
    }

    @ApiOperation("Deletes location by ID")
    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable long id) {
        locationSevice.deleteById(id);
    }
}
