package ru.epam.miniparking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.epam.miniparking.domain.NonIdentifiable;
import ru.epam.miniparking.domain.Spot;
import ru.epam.miniparking.dto.LocationDTO;
import ru.epam.miniparking.dto.SpotDTO;
import ru.epam.miniparking.service.SpotService;

import java.util.List;
import java.util.stream.Collectors;

@Api
@Transactional
@RestController
@RequestMapping("/spots")
@CrossOrigin("http://localhost:3000")
public class SpotController {
    private final SpotService spotService;

    private final ModelMapper mapper;

    public SpotController(SpotService spotService, ModelMapper mapper) {
        this.spotService = spotService;
        this.mapper = mapper;
    }

    @ApiOperation("Returns all spots")
    @GetMapping
    public List<SpotDTO> getAll() {
        return spotService.findAll().stream()
                .map(m -> mapper.map(m, SpotDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Creates spot")
    @PostMapping
    public SpotDTO createSpot(@NonIdentifiable SpotDTO newSpot) {
        return mapper.map(spotService.save(mapper.map(newSpot, Spot.class)), SpotDTO.class);
    }

    @ApiOperation("Creates spots in range [from, to] in indicated location")
    @Transactional
    @PostMapping(params = {"name", "from", "to"})
    public List<SpotDTO> createSpotsInRange(@RequestBody LocationDTO location,
                                            @RequestParam("name") String name,
                                            @RequestParam("from") int from,
                                            @RequestParam("to") int to) {
        return spotService.createSpotsInRange(location.getId(), name, from, to).stream()
                .map(m -> mapper.map(m, SpotDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Returns spot by ID")
    @GetMapping("/{id}")
    public SpotDTO findBySpotId(@PathVariable Long id) {
        return mapper.map(spotService.findBySpotId(id), SpotDTO.class);
    }

    @ApiOperation("Returns spot by name")
    @GetMapping(params = {"title"})
    public List<SpotDTO> findBySpotTitle(@RequestParam("title") String title) {
        return spotService.findBySpotTitle(title).stream()
                .map(m -> mapper.map(m, SpotDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Returns all available spots")
    @GetMapping("/available-spots")
    public List<SpotDTO> findByAvailableTrue() {
        return spotService.findAvailable().stream()
                .map(m -> mapper.map(m, SpotDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Updates spot")
    @PutMapping("/{id}")
    public SpotDTO update(@RequestBody SpotDTO newSpot, @PathVariable Long id) {
        return mapper.map(spotService.update(mapper.map(newSpot, Spot.class), id), SpotDTO.class);
    }

    @ApiOperation("Deletes spot by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        spotService.deleteById(id);
    }

}
