package ru.epam.miniparking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
public final class LocationDTO extends BaseDto {
    @NotNull
    private String locationTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private List<SpotDTO> spots;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private Long officeId;
    @NotNull
    private long capacity;

    public LocationDTO(Long id, @NotNull String locationTitle, @Nullable List<SpotDTO> spots,
                       @Nullable Long officeId, @NotNull long capacity) {
        super(id);
        this.locationTitle = locationTitle;
        this.spots = spots;
        this.officeId = officeId;
        this.capacity = capacity;
    }
}
