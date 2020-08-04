package ru.epam.miniparking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Getter
public final class SpotDTO extends BaseDto {
    @NotNull
    private String spotTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private Long locationId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private Long driverId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private boolean available;


    public SpotDTO(Long id, @NotNull String spotTitle, @Nullable Long locationId,
                   @Nullable Long driverId) {
        super(id);
        this.spotTitle = spotTitle;
        this.locationId = locationId;
        this.driverId = driverId;
        this.available = driverId == null;
    }
}
