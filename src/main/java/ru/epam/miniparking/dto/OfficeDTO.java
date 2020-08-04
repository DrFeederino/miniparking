package ru.epam.miniparking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode
public final class OfficeDTO extends BaseDto {
    @NotNull
    private String officeTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private List<Long> locationIds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private List<Long> driverIds;

    public OfficeDTO(Long id, @NotNull String officeTitle, @Nullable List<Long> locationIds,
                     @Nullable List<Long> driverIds) {
        super(id);
        this.officeTitle = officeTitle;
        this.locationIds = locationIds;
        this.driverIds = driverIds;
    }

    public String getOfficeTitle() {
        return officeTitle;
    }

    @Nullable
    public List<Long> getLocationIds() {
        return locationIds;
    }

    @Nullable
    public List<Long> getDriverIds() {
        return driverIds;
    }
}
