package ru.epam.miniparking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.epam.miniparking.exception.ErrorInfo;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/data.sql")
})
public class IntegrationTest {

    @Autowired
    protected ObjectMapper objectMapper;

    protected static final TypeReference<ErrorInfo> ERRORINFO = new TypeReference<ErrorInfo>() {
    };

    protected <T> T jsonStringToEntity(String body, TypeReference<T> clas) throws JsonProcessingException {
        return objectMapper.readValue(body, clas);
    }

}

