package ru.epam.miniparking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.epam.miniparking.dto.LocationDTO;
import ru.epam.miniparking.dto.SpotDTO;
import ru.epam.miniparking.exception.ErrorInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LocationControllerTest extends IntegrationTest{
    private static final TypeReference<LocationDTO> LOCATION_DTO = new TypeReference<LocationDTO>() {
    };
    private static final TypeReference<List<LocationDTO>> LIST_OF_LOCATION_DTO = new TypeReference<List<LocationDTO>>() {
    };

    @Autowired
    MockMvc mockMvc;

    @Test
    void createNewLocation() throws Exception {
        LocationDTO expectedResult =
                new LocationDTO(4L, "location4", null, 1L, 100);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/locations")
                .content("{\"locationTitle\": \"location4\"," +
                        "\"officeId\": \"1\"," +
                        "\"capacity\": \"100\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        LocationDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), LOCATION_DTO);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void findAll() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<LocationDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_LOCATION_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void findOfficeById() throws Exception {
        LocationDTO expectedResult = getExpectedResult();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/locations/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        LocationDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), LOCATION_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void findOfficeByTitle() throws Exception {
        LocationDTO expectedResult = getExpectedResult();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/locations")
                .param("name", "location2")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<LocationDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_LOCATION_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(expectedResult);
    }


    @Test
    void update() throws Exception {
        List<SpotDTO> spots = Collections.singletonList(new SpotDTO(1L, "1", 1L, 1L));
        LocationDTO expectedResult =
                new LocationDTO(2L, "updated", spots, 1L, 50);
        String body = objectMapper.writeValueAsString(expectedResult);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/locations/2")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        LocationDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), LOCATION_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/locations/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ErrorInfo expectedResult = new ErrorInfo(404, null, "Cannot find Location with id 2", null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/locations/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorInfo result = jsonStringToEntity(response.getResponse().getContentAsString(), ERRORINFO);
        assertThat(result).isEqualTo(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/drivers/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    private LocationDTO getExpectedResult() {
        List<SpotDTO> spots = Arrays.asList(
                new SpotDTO(5L, "5", 2L, 2L),
                new SpotDTO(6L, "6", 2L, null)
        );
        return new LocationDTO(2L, "location2", spots, 1L, 50);
    }


}