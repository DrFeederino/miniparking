package ru.epam.miniparking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.epam.miniparking.domain.Driver;
import ru.epam.miniparking.domain.NonIdentifiable;
import ru.epam.miniparking.dto.DriverDTO;
import ru.epam.miniparking.service.DriverService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Api
@Transactional
@RestController
@RequestMapping("/drivers")
@CrossOrigin("http://localhost:3000")
public class DriverController {
    private final DriverService driverService;

    private final ModelMapper mapper;

    public DriverController(DriverService driverService, ModelMapper mapper) {
        this.driverService = driverService;
        this.mapper = mapper;
    }

    @ApiOperation("Returns all drivers")
    @GetMapping
    public List<DriverDTO> getAllDrivers() {
        return driverService.findAll().stream()
                .map(m -> mapper.map(m, DriverDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Returns driver by ID")
    @GetMapping("/{id}")
    public DriverDTO getById(@PathVariable long id) {
        return mapper.map(driverService.findById(id), DriverDTO.class);
    }

    @ApiOperation("Returns drivers by name")
    @GetMapping(params = {"name"})
    public List<DriverDTO> getByName(@RequestParam String name) {
        return driverService.findByName(name).stream()
                .map(m -> mapper.map(m, DriverDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Creates new driver")
    @PostMapping
    public DriverDTO postCreateNewDriver(@NonIdentifiable DriverDTO driver) {
        return mapper.map(driverService.save(mapper.map(driver, Driver.class)), DriverDTO.class);
    }

    @ApiOperation("Updates driver")
    @PutMapping("/{id}")
    public DriverDTO update(@RequestBody DriverDTO driver, @PathVariable Long id) {
        return mapper.map(driverService.updateDriver(mapper.map(driver, Driver.class), id), DriverDTO.class);
    }

    @ApiOperation("Deletes driver by ID")
    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable long id) {
        driverService.deleteById(id);
    }
}
