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
import ru.epam.miniparking.exception.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class LocationControllerTest extends IntegrationTest{
    private static final TypeReference<LocationDTO> LOCATION_DTO = new TypeReference<LocationDTO>() {
    };
    private static final TypeReference<List<LocationDTO>> LIST_OF_LOCATION_DTO = new TypeReference<List<LocationDTO>>() {
    };

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createNewLocation() throws Exception {
        LocationDTO expectedResult =
                new LocationDTO(4L, "location4", new ArrayList<>(), 1L, 100);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/locations")
                .content("{\"locationTitle\": \"location4\"," +
                        "\"officeId\": \"1\"," +
                        "\"spotIds\": []," +
                        "\"capacity\": \"100\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        LocationDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), LOCATION_DTO);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<LocationDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_LOCATION_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void findOfficeById() throws Exception {
        LocationDTO expectedResult = getExpectedResult();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/locations/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        LocationDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), LOCATION_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void findOfficeByTitle() throws Exception {
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
    public void update() throws Exception {
        List<Long> spotIds = new ArrayList<>();
        spotIds.add(5L);
        spotIds.add(6L);
        LocationDTO expectedResult =
                new LocationDTO(2L, "updated", spotIds, 1L, 50);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/locations/2")
                .content("{\"id\": \"2\"," +
                        "\"locationTitle\": \"updated\"," +
                        "\"officeId\": \"1\"," +
                        "\"spotIds\": [5, 6]," +
                        "\"capacity\": \"50\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        LocationDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), LOCATION_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void delete() throws Exception {
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

    private LocationDTO getExpectedResult(){
        List<Long> spotIds = new ArrayList<>();
        spotIds.add(5L);
        spotIds.add(6L);
        return new LocationDTO(2L, "location2", spotIds, 1L, 50);
    }


}