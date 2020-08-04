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
import ru.epam.miniparking.dto.DriverDTO;
import ru.epam.miniparking.exception.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DriverControllerTest extends IntegrationTest {

    private static final TypeReference<DriverDTO> DRIVER_DTO = new TypeReference<DriverDTO>() {
    };
    private static final TypeReference<List<DriverDTO>> LIST_OF_DRIVERS_DTO = new TypeReference<List<DriverDTO>>() {
    };

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createNewDriver() throws Exception {
        DriverDTO expectedResult =
                new DriverDTO(4L, "test_driver", "test@email.com", 1L, 4L);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/drivers")
                .content("{\"name\": \"test_driver\"," +
                        "\"email\": \"test@email.com\"," +
                        "\"officeId\": \"1\"," +
                        "\"spotId\": \"4\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        DriverDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), DRIVER_DTO);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/drivers")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<DriverDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_DRIVERS_DTO);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void findDriverById() throws Exception {
        DriverDTO expectedResult = new DriverDTO(1L, "User", "test_user@epam.com", 1L, 1L);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        DriverDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), DRIVER_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void findDriverByName() throws Exception {
        DriverDTO expectedResult = getExpectedResult();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/drivers")
                .param("name", "Me")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<DriverDTO> result = jsonStringToEntity(response.getResponse().getContentAsString(), LIST_OF_DRIVERS_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(expectedResult);
    }

    //TODO Failed. Problem with spot availability. -> improved in Andrei branch (need to check again)
    @Test
    public void update() throws Exception {
        DriverDTO expectedResult = new DriverDTO(2L, "updated", "me_user@maul.ru", 1L, 6L);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/drivers/2")
                .content("{\"id\": \"2\"," +
                        "\"name\": \"updated\"," +
                        "\"email\": \"me_user@maul.ru\"," +
                        "\"officeId\": \"1\"," +
                        "\"spotId\": \"6\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        DriverDTO result = jsonStringToEntity(response.getResponse().getContentAsString(), DRIVER_DTO);
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/drivers/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ErrorInfo expectedResult = new ErrorInfo(404, null, "Cannot find Driver with id 2", null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/drivers/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorInfo result = jsonStringToEntity(response.getResponse().getContentAsString(), ERRORINFO);
        assertThat(result).isEqualTo(expectedResult);

    }

    private DriverDTO getExpectedResult(){
        return new DriverDTO(2L, "Me", "me_user@maul.ru", 1L, 5L);
    }

}