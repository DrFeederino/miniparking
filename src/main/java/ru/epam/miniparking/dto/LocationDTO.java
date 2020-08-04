package ru.epam.miniparking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode
public final class LocationDTO extends BaseDto {
    @NotNull
    private String locationTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private List<Long> spotIds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private Long officeId;
    @NotNull
    private long capacity;

    public LocationDTO(Long id, @NotNull String locationTitle, @Nullable List<Long> spotIds,
                       @Nullable Long officeId, @NotNull long capacity) {
        super(id);
        this.locationTitle = locationTitle;
        this.spotIds = spotIds;
        this.officeId = officeId;
        this.capacity = capacity;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    @Nullable
    public List<Long> getSpotIds() {
        return spotIds;
    }

    @Nullable
    public Long getOfficeId() {
        return officeId;
    }

    public long getCapacity() {
        return capacity;
    }
}
