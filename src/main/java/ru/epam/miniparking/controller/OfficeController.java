package ru.epam.miniparking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.epam.miniparking.domain.NonIdentifiable;
import ru.epam.miniparking.domain.Office;
import ru.epam.miniparking.dto.OfficeDTO;
import ru.epam.miniparking.service.OfficeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Api
@Transactional
@RestController
@RequestMapping("/offices")
@CrossOrigin("http://localhost:3000")
public class OfficeController {
    private final OfficeService officeService;

    private final ModelMapper mapper;

    public OfficeController(OfficeService officeService, ModelMapper mapper) {
        this.officeService = officeService;
        this.mapper = mapper;
    }

    @ApiOperation("Returns all offices")
    @GetMapping
    public List<OfficeDTO> getAll() {
        return officeService.findAll().stream()
                .map(m -> mapper.map(m, OfficeDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Creates office")
    @PostMapping
    public OfficeDTO newOffice(@NonIdentifiable OfficeDTO newOffice) {
        return mapper.map(officeService.save(mapper.map(newOffice, Office.class)), OfficeDTO.class);
    }

    @ApiOperation("Returns office by ID")
    @GetMapping("/{id}")
    public OfficeDTO findOfficeById(@PathVariable Long id) {
        return mapper.map(officeService.findById(id), OfficeDTO.class);
    }

    @ApiOperation("Returns office by name")
    @GetMapping(params = {"title"})
    public List<OfficeDTO> findOfficeByTitle(@RequestParam("title") String title) {
        return officeService.findByOfficeTitle(title).stream()
                .map(m -> mapper.map(m, OfficeDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Updates office")
    @PutMapping("/{id}")
    public OfficeDTO update(@RequestBody OfficeDTO newOffice, @PathVariable Long id) {
        return mapper.map(officeService.update(mapper.map(newOffice, Office.class), id), OfficeDTO.class);
    }

    @ApiOperation("Deletes office by ID")
    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable Long id) {
        officeService.deleteById(id);
    }
}
