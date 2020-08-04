package ru.epam.miniparking.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.epam.miniparking.dto.*;
import ru.epam.miniparking.exception.MiniparkingException;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper mapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(NonIdentifiable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String url = request.getRequestURI();
        BaseDto dto;

        switch (url) {
            case "/drivers":
                dto = mapper.readValue(request.getInputStream(), DriverDTO.class);
                break;
            case "/offices":
                dto = mapper.readValue(request.getInputStream(), OfficeDTO.class);
                break;
            case "/locations":
                dto = mapper.readValue(request.getInputStream(), LocationDTO.class);
                break;
            case "/spots":
                dto = mapper.readValue(request.getInputStream(), SpotDTO.class);
                break;
            default:
                throw new MiniparkingException("Unknown entity", HttpStatus.BAD_REQUEST);
        }

        if (dto.getId() != null) {
            throw new MiniparkingException("Id should not be supplied", HttpStatus.BAD_REQUEST);
        }

        return dto;
    }
}