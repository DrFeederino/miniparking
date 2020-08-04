package ru.epam.miniparking.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class BaseDto {
    private Long id;

    public BaseDto() {
    }

    public BaseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
