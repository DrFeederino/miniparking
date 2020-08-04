package ru.epam.miniparking.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
public final class DriverDTO extends BaseDto {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private Long officeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private Long spotId;

    public DriverDTO(Long id, @NotNull String name, @NotNull String email, @Nullable Long officeId, @Nullable Long spotId) {
        super(id);
        this.name = name;
        this.email = email;
        this.officeId = officeId;
        this.spotId = spotId;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public Long getOfficeId() {
        return officeId;
    }

    @Nullable
    public Long getSpotId() {
        return spotId;
    }
}
