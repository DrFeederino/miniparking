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
import org.springframework.util.LinkedMultiValueMap;
import ru.epam.miniparking.dto.SpotDTO;
import ru.epam.miniparking.exception.ErrorInfo;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SpotControllerTest extends IntegrationTest{
    private static final TypeReference<SpotDTO> SPOT_DTO = new TypeReference<SpotDTO>() {
    };
    private static final TypeReference<List<SpotDTO>> LIST_OF_SPOTS_DTO = new TypeReference<List<SpotDTO>>() {
    };

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createNewSpot() throws Exception {
        SpotDTO expectedResult =
                new SpotDTO(8L, "test_spot",2L, null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/spots")
                .content("{\"spotTitle\": \"test_spot\"," +
                        "\"locationId\": \"2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        SpotDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), SPOT_DTO);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void findAllSpots() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/spots")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<SpotDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_SPOTS_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(7);
    }

    @Test
    void createSpotsInRange() throws Exception{
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "spot");
        requestParams.add("from", "1");
        requestParams.add("to", "5");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/spots")
                .params(requestParams)
                .content("{\"id\": \"2\"," +
                        "\"locationTitle\": \"location2\"," +
                        "\"officeId\": \"1\"," +
                        "\"spotIds\": [5, 6]," +
                        "\"capacity\": \"50\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<SpotDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_SPOTS_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    void findBySpotId() throws Exception{
        SpotDTO expectedResult =
                new SpotDTO(3L, "3", 1L, null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/spots/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        SpotDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), SPOT_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void findBySpotTitle() throws Exception{
        SpotDTO expectedResult =
                new SpotDTO(7L, "9",3L, 3L);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/spots")
                .param("title", "9")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<SpotDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_SPOTS_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(expectedResult);
    }

    @Test
    void findByAvailableTrue() throws Exception{
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/spots/available-spots")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<SpotDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_SPOTS_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    public void update() throws Exception {
        SpotDTO expectedResult =
                new SpotDTO(3L, "updated",1L, 2L);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/spots/3")
                .content("{\"spotTitle\": \"updated\"," +
                        "\"locationId\": \"1\"," +
                        "\"driverId\": \"2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        SpotDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), SPOT_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/spots/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ErrorInfo expectedResult = new ErrorInfo(404, null, "Cannot find Spot with id 5", null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/spots/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorInfo result = jsonStringToEntity(response.getResponse().getContentAsString(), ERRORINFO);
        assertThat(result).isEqualTo(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/drivers/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}