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
import ru.epam.miniparking.dto.OfficeDTO;
import ru.epam.miniparking.exception.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OfficeControllerTest extends IntegrationTest {
    private static final TypeReference<OfficeDTO> OFFICE_DTO = new TypeReference<OfficeDTO>() {
    };
    private static final TypeReference<List<OfficeDTO>> LIST_OF_OFFICES_DTO = new TypeReference<List<OfficeDTO>>() {
    };

    @Autowired
    MockMvc mockMvc;

    @Test
    void createNewOffice() throws Exception {

        OfficeDTO expectedResult = new OfficeDTO(3L, "thirdOffice", new ArrayList<>(), new ArrayList<>());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/offices")
                .content("{\"officeTitle\": \"thirdOffice\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        OfficeDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), OFFICE_DTO);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void findAll() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/offices")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<OfficeDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_OFFICES_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findOfficeById() throws Exception {
        OfficeDTO expectedResult = getExpectedResult();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/offices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        OfficeDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), OFFICE_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void findOfficeByTitle() throws Exception {
        OfficeDTO expectedResult = getExpectedResult();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/offices")
                .param("title", "firstOffice")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<OfficeDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_OFFICES_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(expectedResult);
    }


    @Test
    void update() throws Exception {
        OfficeDTO expectedResult = new OfficeDTO(1L, "updated", new ArrayList<>(), new ArrayList<>());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/offices/1")
                .content("{\"officeTitle\": \"updated\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        OfficeDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), OFFICE_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/offices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ErrorInfo expectedResult = new ErrorInfo(404, null, "Cannot find Office with id 1", null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/offices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorInfo result = jsonStringToEntity(response.getResponse().getContentAsString(), ERRORINFO);
        assertThat(result).isEqualTo(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private OfficeDTO getExpectedResult(){
        List<Long> locations = new ArrayList<>();
        locations.add(1L);
        locations.add(2L);
        List<Long> drivers = new ArrayList<>();
        drivers.add(1L);
        drivers.add(2L);
        return new OfficeDTO(1L, "firstOffice", locations, drivers);
    }
}